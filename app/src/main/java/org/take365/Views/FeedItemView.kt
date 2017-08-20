package org.take365.Views

import android.content.Context
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_feed_item.view.*
import org.take365.Network.Models.Response.FeedResponse.FeedItem
import org.take365.R
import org.take365.StoryActivity

/**
 * Created by divere on 20/08/2017.
 */
class FeedItemView(context: Context) : RelativeLayout(context) {

    var feedItem: FeedItem? = null
    set(value) {
        field = value
        if(value == null) return

        tvDate.text = value.date
        Picasso.with(context).load(value.story.authors[0].userpic?.url).into(ivAuthorAvatar)
        tvAuthorName.text = value.story.authors[0].username
        tvStoryName.text = value.story.title
        Picasso.with(context).load(value.thumbLarge.url).into(ivImage)

        tvStoryName.setOnClickListener {
            StoryActivity.startActivity(context, feedItem?.story!!)
        }
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_feed_item, this)
    }
}