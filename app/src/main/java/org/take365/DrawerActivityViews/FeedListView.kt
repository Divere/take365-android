package org.take365.DrawerActivityViews

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.view_feed.view.*
import org.take365.Adapters.FeedAdapter
import org.take365.Network.Models.Response.FeedResponse.FeedItem
import org.take365.Network.Models.Response.FeedResponse.GetFeedResponse
import org.take365.R
import org.take365.Take365Activity
import org.take365.Take365App
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by divere on 26/10/2016.
 */

class FeedListView(context: Context) : FrameLayout(context) {

    internal var feedItems: List<FeedItem>? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.view_feed, this)

//        lvFeed.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
//            val selectedStory = stories!![position]
//            val storyIntent = Intent(context, StoryActivity::class.java)
//            storyIntent.putExtra("story", selectedStory)
//            context.startActivity(storyIntent)
//        }
    }

    fun updateFeed() {
        Take365App.getApi().getFeed(1, 10).enqueue(object : Callback<GetFeedResponse> {
            override fun onResponse(call: Call<GetFeedResponse>?, response: Response<GetFeedResponse>?) {
                if (!response!!.isSuccessful) {
                    (context as Take365Activity).showApiError(response)
                    return
                }

                feedItems = response.body().result!!.list
                if (feedItems!!.isEmpty()) {
                    tvEmptyFeed.visibility = View.VISIBLE
                }
                lvFeed.adapter = FeedAdapter(context, feedItems!!)
            }

            override fun onFailure(call: Call<GetFeedResponse>?, t: Throwable?) {
                (context as Take365Activity).showConnectionError()
            }
        })
    }
}