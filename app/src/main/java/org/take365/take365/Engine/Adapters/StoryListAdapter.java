package org.take365.take365.Engine.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.take365.take365.Engine.Network.Models.StoryListItemModel;
import org.take365.take365.R;

import java.util.List;

/**
 * Created by Ermakov-MAC on 15.02.16.
 */
public class StoryListAdapter extends BaseAdapter {

    private List<StoryListItemModel> storyList;
    private Context context;

    public StoryListAdapter(Context context, List<StoryListItemModel> storyList) {
        this.storyList = storyList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return storyList.size();
    }

    @Override
    public Object getItem(int position) {
        return storyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = inflater.inflate(R.layout.control_story_item, parent, false);
        }

        StoryListItemModel storyModel = storyList.get(position);
        TextView tvValue = ((TextView)view.findViewById(R.id.tvCurrentValue));
        TextView tvName = ((TextView)view.findViewById(R.id.tvStoryName));
        TextView tvPercent = (TextView)view.findViewById(R.id.tvPercent);
        tvPercent.setText(String.valueOf(Math.round(storyModel.progress.totalImages/365.0*100)) + "%");
        tvValue.setText(String.valueOf(storyModel.progress.totalImages));
        if (storyModel.titile == null || storyModel.titile == "")
            storyModel.titile = "Без названия";
        tvName.setText(storyModel.titile);
        return view;
    }
}
