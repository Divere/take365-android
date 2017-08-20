package org.take365.views

import android.content.Context
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_feed_item.view.*
import org.take365.network.models.FeedItem
import org.take365.R
import org.take365.StoryActivity
import org.take365.Take365App

/**
 * Created by divere on 20/08/2017.
 */
class FeedItemView(context: Context) : RelativeLayout(context) {

    var feedItem: FeedItem? = null
        set(value) {
            field = value
            if (value == null) return

            tvDate.text = value.date
            Picasso.with(context).load(value.story.authors[0].userpic?.url).into(ivAuthorAvatar)
            tvAuthorName.text = value.story.authors[0].username
            tvStoryName.text = value.story.title
            Picasso.with(context).load(value.thumbLarge.url).into(ivImage)
            tvLikesCount.text = value.likesCount.toString()

            if (value.isLiked) ivLike.alpha = 1f

            ivLike.setOnClickListener {
                if (!value.isLiked) {
                    Thread { Take365App.getApi().like(feedItem!!.id).execute() }.start()
                    ivLike.alpha = 1f
                    value.likesCount += 1
                    value.isLiked = true
                } else {
                    Thread { Take365App.getApi().unlike(feedItem!!.id).execute() }.start()
                    ivLike.alpha = 0.5f
                    value.likesCount -= 1
                    value.isLiked = false
                }
                tvLikesCount.text = value.likesCount.toString()
            }

            tvStoryName.setOnClickListener {
                StoryActivity.startActivity(context, feedItem?.story!!)
            }
        }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_feed_item, this)
    }
}