package org.take365.take365;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import org.take365.take365.Controls.MonthStoryListControl;
import org.take365.take365.Engine.Network.ApiEvents;
import org.take365.take365.Engine.Network.ApiManager;
import org.take365.take365.Engine.Network.Models.StoryDetailsModel;
import org.take365.take365.Engine.Network.Models.StoryImageImagesModel;

/**
 * Created by Ermakov-MAC on 15.02.16.
 */
public class StoryActivity extends Activity {

    FrameLayout frameConent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        frameConent = (FrameLayout)findViewById(R.id.frame_content);

        Intent intent = getIntent();
        int storyId = intent.getExtras().getInt("storyId");
        ApiManager apiManager  = ApiManager.getInstance();
        apiManager.Events = new ApiEvents() {
            @Override
            public void getStoryResult(StoryDetailsModel result, String error) {
                placeMonthControl(result);
            }
        };
        apiManager.getStory(storyId);
    }

    private void placeMonthControl(StoryDetailsModel storyDetailsModel) {
        MonthStoryListControl storyListControl = new MonthStoryListControl(this, storyDetailsModel);
        frameConent.addView(storyListControl);
    }
}
