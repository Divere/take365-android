package org.take365.views

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.view_storylist.view.*
import org.take365.adapters.StoryListAdapter
import org.take365.network.models.responses.story.GetStoryListResponse
import org.take365.network.models.StoryListItem
import org.take365.R
import org.take365.StoryActivity
import org.take365.Take365Activity
import org.take365.Take365App
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by divere on 26/10/2016.
 */

class StoryListView(context: Context) : FrameLayout(context) {

    internal var stories: List<StoryListItem>? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.view_storylist, this)

        lvStories.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            StoryActivity.startActivity(context, stories!![position])
        }
    }

    fun updateStoryList() {
        Take365App.getApi().getStoriesList(Take365App.getCurrentUser().username, 100).enqueue(object : Callback<GetStoryListResponse> {
            override fun onResponse(call: Call<GetStoryListResponse>, response: Response<GetStoryListResponse>) {
                if (!response.isSuccessful) {
                    (context as Take365Activity).showApiError(response)
                    return
                }

                stories = response.body()!!.result
                if (stories!!.isEmpty()) {
                    tvNoStories.visibility = View.VISIBLE
                }
                lvStories.adapter = StoryListAdapter(context, stories!!)
            }

            override fun onFailure(call: Call<GetStoryListResponse>, t: Throwable) {
                (context as Take365Activity).showConnectionError()
            }
        })
    }
}