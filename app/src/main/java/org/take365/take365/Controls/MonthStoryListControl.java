package org.take365.take365.Controls;

import android.content.Context;
import android.widget.RelativeLayout;

import org.take365.take365.Engine.Network.Models.StoryDetailsModel;
import org.take365.take365.Engine.Network.Models.StoryImageImagesModel;
import org.take365.take365.R;

import java.util.ArrayList;
import java.util.List;

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

    private void refresh(StoryDetailsModel storyDetailsModel) {
        List<StoryImageImagesModel> imageModel = new ArrayList<StoryImageImagesModel>();
        List<MonthStoryGridControl> gridControl = new ArrayList<MonthStoryGridControl>();

        for (int i = 0; i<storyDetailsModel.images.size(); i++) {
            imageModel.add(storyDetailsModel.images.get(i));
            if ( i<storyDetailsModel.images.size() & storyDetailsModel.images.get(i).date.getMonth() == storyDetailsModel.images.get(i++).date.getMonth())
                imageModel.add(storyDetailsModel.images.get(i));
            else {
                gridControl.add(new MonthStoryGridControl(getContext(), imageModel));
                imageModel = new ArrayList<StoryImageImagesModel>();
            }
        }
    }
}
