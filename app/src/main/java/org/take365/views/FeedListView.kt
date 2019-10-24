package org.take365.views

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.view_feed.view.*
import org.take365.R
import org.take365.adapters.FeedAdapter

/**
 * Created by divere on 26/10/2016.
 */

class FeedListView(context: Context) : FrameLayout(context) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_feed, this)
    }

    fun updateFeed() {
        rvFeed.layoutManager = LinearLayoutManager(context)
        rvFeed.adapter = FeedAdapter(context, {
            tvEmptyFeed.visibility = View.VISIBLE
        })
    }
}