package org.take365.take365.Controls;

import android.content.Context;
import android.widget.RelativeLayout;

import org.take365.take365.Engine.Network.Models.StoryDetailsModel;
import org.take365.take365.Engine.Network.Models.StoryImageImagesModel;
import org.take365.take365.R;

/**
 * Created by Ermakov-MAC on 18.02.16.
 */
public class PhotoItemMonthControl extends RelativeLayout {

    StoryImageImagesModel imageModel;

    public PhotoItemMonthControl(Context context, StoryImageImagesModel imageModel) {
        super(context);
        inflate(context, R.layout.control_photo_month_item, this);
        this.imageModel = imageModel;
    }
}