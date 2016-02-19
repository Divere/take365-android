package org.take365.take365.Engine.Adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.take365.take365.Engine.Network.Models.StoryImageImagesModel;

import java.util.List;

/**
 * Created by Ermakov-MAC on 19.02.16.
 */
public class PhotoGridAdapter extends BaseAdapter {

    List<StoryImageImagesModel> storyImageModels;

    public PhotoGridAdapter(List<StoryImageImagesModel> storyImageModels) {
        this.storyImageModels = storyImageModels;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
