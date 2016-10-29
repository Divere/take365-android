package org.take365.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.squareup.picasso.Picasso;

import org.take365.Models.StoryDay;
import org.take365.Views.StoryCalendarView;

import java.util.List;

/**
 * Created by divere on 29/10/2016.
 */

public class StorySectionAdapter extends BaseAdapter {

    private Context context;
    private List<StoryDay> days;

    public StorySectionAdapter(Context context, List<StoryDay> days)
    {
        this.context = context;
        this.days = days;
    }

    @Override
    public int getCount() {
        return days.size();
    }

    @Override
    public Object getItem(int position) {
        return days.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StoryDay item = (StoryDay) this.getItem(position);

        if(convertView == null) {
            convertView = new StoryCalendarView(context);
        }

        StoryCalendarView view = (StoryCalendarView)convertView;
        view.setDay(item);

        if(item.image != null) {
            Picasso.with(context).load(item.image.thumb.url).into(view.imageView);
        } else {
            view.imageView.setImageResource(android.R.color.transparent);
        }

        return convertView;
    }
}
