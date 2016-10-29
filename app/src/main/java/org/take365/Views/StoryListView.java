package org.take365.Views;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import org.take365.Adapters.StoryListAdapter;
import org.take365.Engine.Network.Models.Response.StoryResponse.StoryListResponse;
import org.take365.Engine.Network.Models.StoryListItemModel;
import org.take365.R;
import org.take365.StoryActivity;
import org.take365.Take365Application;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by divere on 26/10/2016.
 */

public class StoryListView extends FrameLayout {

    ListView lvStories;
    List<StoryListItemModel> stories;

    public StoryListView(final Context context) {
        super(context);
        addView(View.inflate(context, R.layout.view_storylist, null));

        lvStories = (ListView)findViewById(R.id.lvStories);

        lvStories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StoryListItemModel selectedStory = stories.get(position);
                Intent storyIntent = new Intent(context, StoryActivity.class);
                storyIntent.putExtra("story", selectedStory);
                context.startActivity(storyIntent);
            }
        });

        Take365Application.getApi().storyList(Take365Application.getCurrentUser().username).enqueue(new Callback<StoryListResponse>() {
            @Override
            public void onResponse(Call<StoryListResponse> call, Response<StoryListResponse> response) {
                lvStories.setAdapter(new StoryListAdapter(context, response.body().result));
                stories = response.body().result;
            }

            @Override
            public void onFailure(Call<StoryListResponse> call, Throwable t) {

            }
        });
    }
}
