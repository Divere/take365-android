package org.take365.take365.Controls;

import android.content.Context;
import android.widget.RelativeLayout;

import org.take365.take365.Engine.Network.Models.StoryDetailsModel;
import org.take365.take365.R;

/**
 * Created by Ermakov-MAC on 18.02.16.
 */
public class MonthStoryListControl extends RelativeLayout {

    StoryDetailsModel story;

    public MonthStoryListControl(Context context, StoryDetailsModel storyDetailsModel) {
        super(context);
        inflate(context, R.layout.control_story_list, this);

        story = storyDetailsModel;
    }
}
