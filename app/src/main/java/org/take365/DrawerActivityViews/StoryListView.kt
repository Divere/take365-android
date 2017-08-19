package org.take365.DrawerActivityViews

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.view_storylist.view.*
import org.take365.Adapters.StoryListAdapter
import org.take365.Network.Models.Response.StoryResponse.StoryListResponse
import org.take365.Network.Models.StoryListItemModel
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

    internal var stories: List<StoryListItemModel>? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.view_storylist, this)

        lvStories.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val selectedStory = stories!![position]
            val storyIntent = Intent(context, StoryActivity::class.java)
            storyIntent.putExtra("story", selectedStory)
            context.startActivity(storyIntent)
        }
    }

    fun updateStoryList() {
        Take365App.getApi().getStoriesList(Take365App.getCurrentUser().username, 100).enqueue(object : Callback<StoryListResponse> {
            override fun onResponse(call: Call<StoryListResponse>, response: Response<StoryListResponse>) {
                if (!response.isSuccessful) {
                    (context as Take365Activity).showApiError(response)
                    return
                }

                stories = response.body().result
                if (stories!!.size == 0) {
                    tvNoStories.visibility = View.VISIBLE
                }
                lvStories.adapter = StoryListAdapter(context, stories!!)
            }

            override fun onFailure(call: Call<StoryListResponse>, t: Throwable) {
                (context as Take365Activity).showConnectionError()
            }
        })
    }
}