package org.take365.Adapters;

import android.content.Context;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.take365.Models.StoryDay;
import org.take365.Views.StorySectionView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by divere on 29/10/2016.
 */

public class StoryAdapter extends BaseAdapter {

    private Context context;
    private HashMap<String, List<StoryDay>> sections;
    private List<String> sortedSectionsTitles;

    public StoryAdapter(Context context, HashMap<String, List<StoryDay>> sections, List<String> sortedSectionsTitles)
    {
        this.context = context;
        this.sections = sections;
        this.sortedSectionsTitles = sortedSectionsTitles;
    }

    @Override
    public int getCount() {
        return sortedSectionsTitles.size();
    }

    @Override
    public Object getItem(int position) {
        String key = sortedSectionsTitles.get(position);
        return new Pair<>(key, sections.get(key));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Pair<String, List<StoryDay>> item = (Pair<String, List<StoryDay>>) this.getItem(position);

        if(convertView == null){
            convertView = new StorySectionView(context);
        }

        StorySectionView view = (StorySectionView)convertView;
        view.setTitle(item.first);
        view.setDays(item.second);

        return convertView;
    }
}
