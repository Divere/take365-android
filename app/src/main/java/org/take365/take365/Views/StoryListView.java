package org.take365.take365.Views;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;

import org.take365.take365.Adapters.StoryListAdapter;
import org.take365.take365.Engine.Network.Models.Response.StoryResponse.StoryListResponse;
import org.take365.take365.R;
import org.take365.take365.Take365Application;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by divere on 26/10/2016.
 */

public class StoryListView extends FrameLayout {

    ListView lvStories;

    public StoryListView(final Context context) {
        super(context);
        addView(View.inflate(context, R.layout.view_storylist, null));

        lvStories = (ListView)findViewById(R.id.lvStories);

        Take365Application.getApi().storyList(Take365Application.getCurrentUser().username).enqueue(new Callback<StoryListResponse>() {
            @Override
            public void onResponse(Call<StoryListResponse> call, Response<StoryListResponse> response) {
                lvStories.setAdapter(new StoryListAdapter(context, response.body().result));
            }

            @Override
            public void onFailure(Call<StoryListResponse> call, Throwable t) {

            }
        });
    }
}
