package org.take365.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import org.take365.Network.models.FeedItem
import org.take365.views.FeedItemView

/**
 * Created by divere on 26/10/2016.
 */

class FeedAdapter(val context: Context, val items: List<FeedItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class FeedItemViewHolder(val feedItemView: FeedItemView) : RecyclerView.ViewHolder(feedItemView)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as FeedItemViewHolder).feedItemView.feedItem = items[position]
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val view = FeedItemView(parent!!.context)
        return FeedItemViewHolder(view)
    }
}