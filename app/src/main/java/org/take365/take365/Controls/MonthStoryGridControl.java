package org.take365.take365.Controls;

import android.content.Context;
import android.widget.RelativeLayout;

import org.take365.take365.Engine.Network.Models.StoryImageImagesModel;
import org.take365.take365.R;

import java.util.List;

/**
 * Created by Ermakov-MAC on 19.02.16.
 */
public class MonthStoryGridControl extends RelativeLayout {

    public MonthStoryGridControl(Context context, List<StoryImageImagesModel> imagesModel) {
        super(context);
        inflate(context, R.layout.control_story_month_grid, this);


    }
}
