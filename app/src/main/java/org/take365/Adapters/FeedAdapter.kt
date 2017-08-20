package org.take365.Adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_feed_item.view.*
import org.take365.MainActivity
import org.take365.Network.Models.Response.FeedResponse.FeedItem
import org.take365.R

/**
 * Created by divere on 26/10/2016.
 */

class FeedAdapter(context: Context, items: List<FeedItem>) : ArrayAdapter<FeedItem>(context, 0, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView

        val item = this.getItem(position)

        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.view_feed_item, parent, false)
        }

        convertView!!.layoutParams.height = (context as MainActivity).viewsContainer.width
        convertView.layoutParams.width = (context as MainActivity).viewsContainer.width
        convertView.requestLayout()

        convertView.tvDate.text = item.date
        Picasso.with(context).load(item.story.authors[0].userpic?.url).into(convertView.ivAuthorAvatar)
        convertView.tvAuthorName.text = item.story.authors[0].username
        convertView.tvStoryName.text = item.story.title
        Picasso.with(context).load(item.thumbLarge.url).into(convertView.ivImage)

        return convertView
    }
}