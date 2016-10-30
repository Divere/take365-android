package org.take365.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.take365.Engine.Network.Models.StoryListItemModel;
import org.take365.R;

import java.util.List;

/**
 * Created by divere on 26/10/2016.
 */

public class StoryListAdapter extends ArrayAdapter<StoryListItemModel> {
    public StoryListAdapter(Context context, List<StoryListItemModel> items) {
        super(context, 0, items);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        StoryListItemModel item = this.getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.view_storylist_item, parent, false);
        }

        TextView tvStoryName = (TextView) convertView.findViewById(R.id.tvStoryName);
        TextView tvCompletedFrom = (TextView) convertView.findViewById(R.id.tvCompletedFrom);
        TextView tvCompletedPercentage = (TextView) convertView.findViewById(R.id.tvCompletedPercentage);

        tvStoryName.setText(item.title);
        tvCompletedFrom.setText(String.valueOf(item.progress.totalImages));
        tvCompletedPercentage.setText("(" + String.valueOf(item.progress.percentsComplete) + "%)");

        return convertView;
    }
}
