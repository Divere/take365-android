package org.take365.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.take365.Models.StoryDay;
import org.take365.R;

/**
 * Created by divere on 29/10/2016.
 */

public class StoryCalendarView extends FrameLayout {

    public StoryDay day;

    private TextView tvDay;

    public ImageView imageView;

    public StoryCalendarView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.view_story_calendar_day, this);
        tvDay = (TextView) findViewById(R.id.tvDay);
        imageView = (ImageView) findViewById(R.id.imageView);
    }

    public void setDay(StoryDay day) {
        this.day = day;
        tvDay.setText(String.valueOf(day.day.split("-")[2]));
    }
}
