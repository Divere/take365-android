package org.take365.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.take365.Models.StoryDay;
import org.take365.R;

/**
 * Created by divere on 29/10/2016.
 */

public class StoryDayView extends FrameLayout {

    public StoryDay day;

    private TextView tvDay;
    public ImageView imageView;
    public ProgressBar uploadProgressBar;

    public StoryDayView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.view_story_day, this);
        tvDay = (TextView) findViewById(R.id.tvDay);
        imageView = (ImageView) findViewById(R.id.imageView);
        uploadProgressBar = (ProgressBar) findViewById(R.id.uploadProgressBar);
    }

    public void setDay(StoryDay day) {
        this.day = day;
        tvDay.setText(String.valueOf(day.day.split("-")[2]));
    }

    public void setUploadProgress(int percentage) {
        day.uploadProgress = percentage;
        if(uploadProgressBar.getVisibility() == INVISIBLE) {
            uploadProgressBar.setVisibility(VISIBLE);
        }
        uploadProgressBar.setProgress(percentage);
    }
}
