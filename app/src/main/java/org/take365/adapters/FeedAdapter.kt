package org.take365.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import org.take365.Take365Activity
import org.take365.Take365App
import org.take365.network.models.FeedItem
import org.take365.network.models.responses.feed.GetFeedResponse
import org.take365.views.FeedItemView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by divere on 26/10/2016.
 */

class FeedAdapter(val context: Context, val emptyFeedEvent: (() -> Unit)?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: MutableList<FeedItem> = mutableListOf()
    private var currentPage = 1
    private var isRequestInProcess = false
    private var isLatestPageLoaded = false

    init {
        getFeed { response ->
            if (response.body()!!.result!!.isEmpty) {
                emptyFeedEvent?.invoke()
                return@getFeed
            }

            items.addAll(response.body()!!.result!!.list)
            notifyDataSetChanged()
        }
    }

    private fun getFeed(success: (response: Response<GetFeedResponse>) -> Unit) {
        Take365App.getApi().getFeed(currentPage, 20).enqueue(object : Callback<GetFeedResponse> {
            override fun onResponse(call: Call<GetFeedResponse>?, response: Response<GetFeedResponse>?) {
                if (!response!!.isSuccessful) {
                    (context as Take365Activity).showApiError(response)
                    return
                }

                success.invoke(response)
            }

            override fun onFailure(call: Call<GetFeedResponse>?, t: Throwable?) {
                (context as Take365Activity).showConnectionError()
            }
        })
    }

    inner class FeedItemViewHolder(val feedItemView: FeedItemView) : RecyclerView.ViewHolder(feedItemView)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as FeedItemViewHolder).feedItemView.feedItem = items[position]
        if (itemCount - position < 5 && !isRequestInProcess && !isLatestPageLoaded) {
            isRequestInProcess = true
            currentPage++
            getFeed { response ->
                isRequestInProcess = false
                if (response.body()!!.result!!.isEmpty) {
                    isLatestPageLoaded = true
                    return@getFeed
                } else {
                    items.addAll(response.body()!!.result!!.list)
                    notifyDataSetChanged()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return FeedItemViewHolder(FeedItemView(parent!!.context))
    }
}