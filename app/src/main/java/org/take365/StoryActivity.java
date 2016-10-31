package org.take365;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.take365.Adapters.StoryRecycleAdapter;
import org.take365.Components.GridAutofitLayoutManager;
import org.take365.Components.ImageUploader;
import org.take365.Components.SpacesItemDecoration;
import org.take365.Engine.Network.Models.AuthorModel;
import org.take365.Engine.Network.Models.Response.BaseResponse;
import org.take365.Engine.Network.Models.Response.StoryResponse.StoryDetailResponse;
import org.take365.Engine.Network.Models.StoryDetailsModel;
import org.take365.Engine.Network.Models.StoryImageImagesModel;
import org.take365.Engine.Network.Models.StoryListItemModel;
import org.take365.Helpers.BitmapToBytesConverter;
import org.take365.Helpers.DialogHelpers;
import org.take365.Helpers.DpToPixelsConverter;
import org.take365.Models.StoryDay;
import org.take365.Views.StoryDayView;

import java.io.IOException;
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
        fab.setOnClickListener(new View.OnClickListener() {
            private void captureImage()
            {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, CAMERA_REQUEST);
                }
            }

            @Override
            public void onClick(View view) {
                if(imagesByDays.get(todayString) != null) {
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case CAMERA_REQUEST: {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                byte[] imageBytes = BitmapToBytesConverter.convert(photo);

                ImageUploader.uploadImage(currentStory.id, imageBytes, null, new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        refreshStoryInfo();
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {

                    }
                });
            }
            break;
            case PICK_IMAGE: {
                Uri imageUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    byte[] imageBytes = BitmapToBytesConverter.convert(bitmap);

                    ImageUploader.uploadImage(currentStory.id, imageBytes, selectedDate, new Callback<BaseResponse>() {
                        @Override
                        public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                            refreshStoryInfo();
                        }

                        @Override
                        public void onFailure(Call<BaseResponse> call, Throwable t) {

                        }
                    });
                } catch (IOException e) {
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

        for (int i = 0; i < storyInfo.progress.passedDays + 1; i++) {
            calendar.add(Calendar.HOUR, 24);
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

        if (storyRecycleAdapter == null) {
            storyRecycleAdapter = new StoryRecycleAdapter(this, sections, new View.OnClickListener() {

                public void captureImage()
                {
                     // TODO: 31/10/2016 find a way how pass this value via Intent extra
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
                }

                @Override
                public void onClick(View v) {
                    StoryDay day = ((StoryDayView) v).day;
                    selectedDate = day.day;

                    if(day.image != null) {
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
        } else {
            storyRecycleAdapter.setSections(sections);
            storyRecycleAdapter.notifyDataSetChanged();
        }
    }
}