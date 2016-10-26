package org.take365.take365.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import org.take365.take365.Engine.Network.Models.StoryListItemModel;

/**
 * Created by divere on 26/10/2016.
 */

public class StoryListAdapter extends ArrayAdapter<StoryListItemModel> {
    public StoryListAdapter(Context context, StoryListItemModel[] items) {
        super(context, 0, items);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}
