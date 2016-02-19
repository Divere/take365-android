package org.take365.take365.Controls;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.take365.take365.Engine.Adapters.StoryListAdapter;
import org.take365.take365.Engine.Network.Models.StoryListItemModel;
import org.take365.take365.R;
import org.take365.take365.StoryActivity;

import java.util.List;

/**
 * Created by Ermakov-MAC on 15.02.16.
 */
public class StoryListControl extends LinearLayout {
    Context context;
    ListView lvList;
    TextView tvNoStories;
    List<StoryListItemModel> storyList;

    public StoryListControl(Context context) {
        super(context);
        inflate(context, R.layout.control_story_list, this);
        this.context = context;
        final Context context2 = context;
        lvList = (ListView)findViewById(R.id.lvList);
        tvNoStories = (TextView)findViewById(R.id.tvNoStories);

        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context2, StoryActivity.class);
                intent.putExtra("storyId", storyList.get(position).id);
                context2.startActivity(intent);
            }
        });
    }

    public void updateList(List<StoryListItemModel> storyList) {
        if (storyList.size()>0) {
            StoryListAdapter adapter = new StoryListAdapter(context, storyList);
            lvList.setAdapter(adapter);
            tvNoStories.setVisibility(INVISIBLE);
            this.storyList = storyList;
        }
    }
}
