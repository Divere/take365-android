package org.take365.take365.Controls;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.ListView;
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
    LinearLayout llItems;

    public MonthStoryListControl(Context context, StoryDetailsModel storyDetailsModel) {
        super(context);
        inflate(context, R.layout.control_photo_month_list, this);
        llItems = (LinearLayout)findViewById(R.id.llItems);

        story = storyDetailsModel;
        refresh(story.images);
    }

    private void refresh(List<StoryImageImagesModel> imagesModelsOrig) {
        List<StoryImageImagesModel> imagesModels = new ArrayList<StoryImageImagesModel>();
        List<MonthStoryGridControl> gridControl = new ArrayList<MonthStoryGridControl>();
        MonthStoryGridControl control;
        imagesModels.add(imagesModelsOrig.get(0));
        for (int i = 1; i<imagesModelsOrig.size(); i++) {
            if (imagesModelsOrig.get(i).date.getMonth()==imagesModels.get(imagesModels.size()-1).date.getMonth()) {
                imagesModels.add(imagesModelsOrig.get(i));
            }
            else
            {
                llItems.addView(new MonthStoryGridControl(getContext(), imagesModels));
                imagesModels = new ArrayList<StoryImageImagesModel>();
                imagesModels.add(imagesModelsOrig.get(i));
            }
        }
        llItems.addView(new MonthStoryGridControl(getContext(), imagesModels));
    }
}
