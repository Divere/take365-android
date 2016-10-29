package org.take365;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import org.take365.Adapters.StoryAdapter;
import org.take365.Engine.Network.Models.AuthorModel;
import org.take365.Engine.Network.Models.Response.StoryResponse.StoryDetailResponse;
import org.take365.Engine.Network.Models.StoryDetailsModel;
import org.take365.Engine.Network.Models.StoryImageImagesModel;
import org.take365.Engine.Network.Models.StoryListItemModel;
import org.take365.Models.StoryDay;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoryActivity extends AppCompatActivity {

    private StoryListItemModel currentStory;
    private StoryDetailsModel storyInfo;
    private boolean isContributingStory;

    private HashMap<String, StoryImageImagesModel> imagesByDays;
    private List<StoryDay> days;
    private HashMap<String, List<StoryDay>> sections;
    private List<String> sortedSectionsTitles;

    private ListView lvSections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        currentStory = (StoryListItemModel) getIntent().getSerializableExtra("story");
        setTitle(currentStory.title);

        lvSections = (ListView) findViewById(R.id.lvSections);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        imagesByDays = new HashMap<>();
        days = new ArrayList<>();
        sections = new HashMap<>();

        Take365App.getApi().getStoryDetails(currentStory.id).enqueue(new Callback<StoryDetailResponse>() {
            @Override
            public void onResponse(Call<StoryDetailResponse> call, Response<StoryDetailResponse> response) {
                storyInfo = response.body().result;
                try {
                    loadStoryInfo();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<StoryDetailResponse> call, Throwable t) {

            }
        });
    }

    private void loadStoryInfo() throws ParseException {
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

        sortedSectionsTitles = new ArrayList<>(sections.keySet());
        Collections.sort(sortedSectionsTitles, new Comparator<String>() {
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

        lvSections.setAdapter(new StoryAdapter(sections, sortedSectionsTitles));
    }
}
