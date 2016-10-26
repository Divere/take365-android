package org.take365.take365.Views;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;

import org.take365.take365.R;

/**
 * Created by divere on 26/10/2016.
 */

public class StoryListView extends FrameLayout {

    ListView lvStories;

    public StoryListView(Context context) {
        super(context);
        addView(View.inflate(context, R.layout.view_storylist, null));

        lvStories = (ListView)findViewById(R.id.lvStories);
    }
}
