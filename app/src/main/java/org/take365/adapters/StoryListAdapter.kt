package org.take365.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

import org.take365.Network.models.StoryListItem
import org.take365.R

/**
 * Created by divere on 26/10/2016.
 */

class StoryListAdapter(context: Context, items: List<StoryListItem>) : ArrayAdapter<StoryListItem>(context, 0, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView

        val item = this.getItem(position)

        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.view_storylist_item, parent, false)
        }

        val tvStoryName = convertView!!.findViewById<View>(R.id.tvStoryName) as TextView
        val tvCompletedFrom = convertView.findViewById<View>(R.id.tvCompletedFrom) as TextView
        val tvCompletedPercentage = convertView.findViewById<View>(R.id.tvCompletedPercentage) as TextView

        tvStoryName.text = item!!.title
        tvCompletedFrom.text = item.progress.totalImages.toString()
        tvCompletedPercentage.text = "(" + item.progress.percentsComplete.toString() + "%)"

        return convertView
    }
}
