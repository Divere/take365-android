package org.take365.take365.Controls;

import android.content.Context;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.take365.take365.Engine.Adapters.PhotoGridAdapter;
import org.take365.take365.Engine.Network.Models.StoryImageImagesModel;
import org.take365.take365.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Ermakov-MAC on 19.02.16.
 */
public class MonthStoryGridControl extends RelativeLayout {

    GridView gridPhotos;

    public MonthStoryGridControl(Context context, List<StoryImageImagesModel> imagesModel) {
        super(context);
        inflate(context, R.layout.control_story_month_grid, this);
        gridPhotos = (GridView)findViewById(R.id.gvPhotos);
        TextView txtYear = (TextView)findViewById(R.id.txtYear);
        TextView txtMonth = (TextView)findViewById(R.id.txtMonth);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(imagesModel.get(0).date);


        txtYear.setText(String.valueOf(calendar.get(Calendar.YEAR)));
        txtMonth.setText(String.valueOf(new SimpleDateFormat("MMMM").format(imagesModel.get(0).date)));

        PhotoGridAdapter adapter = new PhotoGridAdapter(context, imagesModel);
        gridPhotos.setAdapter(adapter);
    }
}
