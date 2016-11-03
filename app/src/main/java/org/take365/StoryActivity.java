package org.take365;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.take365.Adapters.StoryRecycleAdapter;
import org.take365.Components.GridAutofitLayoutManager;
import org.take365.Components.ImageUploader;
import org.take365.Components.ProgressRequestBody;
import org.take365.Components.SpacesItemDecoration;
import org.take365.Engine.Network.Models.AuthorModel;
import org.take365.Engine.Network.Models.Response.BaseResponse;
import org.take365.Engine.Network.Models.Response.StoryResponse.StoryDetailResponse;
import org.take365.Engine.Network.Models.StoryDetailsModel;
import org.take365.Engine.Network.Models.StoryImageImagesModel;
import org.take365.Engine.Network.Models.StoryListItemModel;
import org.take365.Helpers.DialogHelpers;
import org.take365.Helpers.DpToPixelsConverter;
import org.take365.Models.StoryDay;
import org.take365.Views.StoryDayView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoryActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1888;
    private static final int PICK_IMAGE = 1889;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    private StoryListItemModel currentStory;
    private StoryDetailsModel storyInfo;
    private boolean isContributingStory;

    private HashMap<String, StoryImageImagesModel> imagesByDays;
    private List<StoryDay> days;
    private TreeMap<String, List<StoryDay>> sections;

    private RecyclerView gvDays;
    private StoryRecycleAdapter storyRecycleAdapter;

    private String todayString;
    private String selectedDate;

    private String pictureImagePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        currentStory = (StoryListItemModel) getIntent().getSerializableExtra("story");
        setTitle(currentStory.title);

        gvDays = (RecyclerView) findViewById(R.id.gvDays);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if(currentStory.progress.isOutdated) {
            fab.setVisibility(View.GONE);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            private void captureImage() {

                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = timeStamp + ".jpg";
                File storageDir = Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES);
                pictureImagePath = storageDir.getAbsolutePath() + "/" + imageFileName;
                File file = new File(pictureImagePath);
                Uri outputFileUri = FileProvider.getUriForFile(StoryActivity.this, StoryActivity.this.getApplicationContext().getPackageName() + ".provider", file);

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

                List<ResolveInfo> resInfoList = StoryActivity.this.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                for (ResolveInfo resolveInfo : resInfoList) {
                    String packageName = resolveInfo.activityInfo.packageName;
                    StoryActivity.this.grantUriPermission(packageName, outputFileUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, CAMERA_REQUEST);
                }
            }

            @Override
            public void onClick(View view) {
                if (imagesByDays.get(todayString) != null) {
                    DialogHelpers.AskDialog(StoryActivity.this, "Данное действие заменит уже существующую фотографию", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            captureImage();
                        }
                    });
                    return;
                }

                captureImage();
            }
        });

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        todayString = df.format(new Date());

        refreshStoryInfo();

        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        ProgressRequestBody.UploadCallbacks progressCallback = new ProgressRequestBody.UploadCallbacks() {
            @Override
            public void onProgressUpdate(int percentage) {
                storyRecycleAdapter.setUploadProgress(selectedDate, percentage);
            }

            @Override
            public void onError() {

            }

            @Override
            public void onFinish() {

            }
        };

        Callback<BaseResponse> resultCallback = new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                refreshStoryInfo();
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

            }
        };

        switch (requestCode) {
            case CAMERA_REQUEST: {
                File imgFile = new File(pictureImagePath);
                if (!imgFile.exists()) {
                    return;
                }
                selectedDate = todayString;
                ImageUploader.uploadImage(currentStory.id, imgFile, selectedDate, progressCallback, resultCallback);
            }
            break;
            case PICK_IMAGE: {
                try {
                    InputStream inputStream = StoryActivity.this.getContentResolver().openInputStream(data.getData());
                    ImageUploader.uploadImage(currentStory.id, inputStream, selectedDate, progressCallback, resultCallback);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            break;
        }
    }

    private void refreshStoryInfo() {
        Take365App.getApi().getStoryDetails(currentStory.id).enqueue(new Callback<StoryDetailResponse>() {
            @Override
            public void onResponse(Call<StoryDetailResponse> call, Response<StoryDetailResponse> response) {
                storyInfo = response.body().result;
                try {
                    renderStoryInfo();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<StoryDetailResponse> call, Throwable t) {

            }
        });
    }

    private void renderStoryInfo() throws ParseException {
        imagesByDays = new HashMap<>();
        days = new ArrayList<>();
        sections = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");

                Date date1 = null;
                Date date2 = null;

                try {
                    date1 = df.parse(o1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                try {
                    date2 = df.parse(o2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                return date2.compareTo(date1);
            }
        });

        for (AuthorModel author : storyInfo.authors) {
            if (author.id == Take365App.getCurrentUser().id) {
                isContributingStory = true;
                break;
            }
        }

        for (StoryImageImagesModel image : storyInfo.images) {
            imagesByDays.put(image.date, image);
        }

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Date dateStart = df.parse(storyInfo.progress.dateStart);
        Date dateEnd = df.parse(storyInfo.progress.dateEnd);
        Date today = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateStart);

        StoryDay firstDay = new StoryDay();
        firstDay.day = df.format(calendar.getTime());
        firstDay.image = imagesByDays.get(firstDay.day);

        if (isContributingStory) {
            days.add(0, firstDay);
        }

        for (int i = 0; i < storyInfo.progress.passedDays + 1; i++) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            Date currentDate = calendar.getTime();
            if (currentDate.compareTo(dateEnd) > 0 || currentDate.compareTo(today) > 0) {
                break;
            }

            StoryDay storyDay = new StoryDay();
            storyDay.day = df.format(currentDate);
            storyDay.image = imagesByDays.get(storyDay.day);

            if (storyDay.image == null && !isContributingStory) {
                continue;
            }

            days.add(0, storyDay);
        }

        for (StoryDay storyDay : days) {
            String[] sectionTitleComponents = storyDay.day.split("-");
            String sectionTitle = sectionTitleComponents[0] + "-" + sectionTitleComponents[1];

            List<StoryDay> sectionContent = sections.get(sectionTitle);

            if (sectionContent == null) {
                sectionContent = new ArrayList<>();
                sections.put(sectionTitle, sectionContent);
            }

            sectionContent.add(storyDay);
        }

        if (storyRecycleAdapter != null) {
            storyRecycleAdapter.setSections(sections);
            storyRecycleAdapter.notifyDataSetChanged();
            return;
        }


        storyRecycleAdapter = new StoryRecycleAdapter(this, sections, new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                StoryDay day = ((StoryDayView) v).day;
                selectedDate = day.day;

                if (day.image != null) {
                    Intent playerIntent = new Intent(StoryActivity.this, PhotoPlayerActivity.class);
                    playerIntent.putExtra("currentItem", day);
                    playerIntent.putExtra("items", (ArrayList<StoryDay>) days);
                    startActivity(playerIntent);
                    return;
                }

                captureImage();
            }
        }, (!currentStory.progress.isOutdated) ? new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                StoryDay day = ((StoryDayView) v).day;
                selectedDate = day.day;

                if (day.image != null) {
                    DialogHelpers.AskDialog(StoryActivity.this, "Данное действие заменит уже существующую фотографию", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            captureImage();
                        }
                    });
                }

                return true;
            }
        } : null);

        final GridAutofitLayoutManager gridLayoutManager = new GridAutofitLayoutManager(this, DpToPixelsConverter.toPixels(110));
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return storyRecycleAdapter.getItemViewType(position) == StoryRecycleAdapter.ElementsType.VIEW_HEADER ? gridLayoutManager.spanCount : 1;
            }
        });

        gvDays.setLayoutManager(gridLayoutManager);
        gvDays.addItemDecoration(new SpacesItemDecoration(DpToPixelsConverter.toPixels(10)));
        gvDays.setAdapter(storyRecycleAdapter);
    }

    private void captureImage() {
        if(currentStory.progress.isOutdated){
            return;
        }
        // TODO: 31/10/2016 find a way how pass this value via Intent extra
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }
}