package org.take365.take365.Controls;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.take365.take365.Engine.Adapters.StoryListAdapter;
import org.take365.take365.Engine.Network.Models.Response.StoryResponse.StoryListResponse;
import org.take365.take365.Engine.Network.Models.StoryModel;
import org.take365.take365.R;

import java.util.List;

/**
 * Created by Ermakov-MAC on 15.02.16.
 */
public class StoryListControl extends LinearLayout {
    Context context;
    ListView lvList;
    TextView tvNoStories;

    public StoryListControl(Context context) {
        super(context);
        inflate(context, R.layout.control_story_list, this);
        this.context = context;
        lvList = (ListView)findViewById(R.id.lvList);
        tvNoStories = (TextView)findViewById(R.id.tvNoStories);
    }

    public void updateList(List<StoryModel> storyList) {
        if (storyList.size()>0) {
            StoryListAdapter adapter = new StoryListAdapter(context, storyList);
            lvList.setAdapter(adapter);
            tvNoStories.setVisibility(INVISIBLE);
        }
    }
}
