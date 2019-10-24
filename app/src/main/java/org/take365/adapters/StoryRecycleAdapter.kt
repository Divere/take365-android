package org.take365.adapters

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_story_day.view.*

import org.take365.models.StoryDay
import org.take365.views.StoryDayView
import org.take365.views.StorySectionView

import java.util.HashMap
import java.util.TreeMap

/**
 * Created by divere on 30/10/2016.
 */

class StoryRecycleAdapter(private val context: Context, sections: TreeMap<String, MutableList<StoryDay>>, private val onClickListener: View.OnClickListener?, private val onLongClickListener: View.OnLongClickListener?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    object ElementsType {
        val VIEW_HEADER = 0
        val VIEW_NORMAL = 1
    }

    private inner class HeaderViewHolder internal constructor(internal var headerView: StorySectionView) : RecyclerView.ViewHolder(headerView)

    private inner class ViewHolder internal constructor(internal var dayView: StoryDayView) : RecyclerView.ViewHolder(dayView)

    private var items: MutableList<Any>? = null
    private var visibleViews: HashMap<String, StoryDayView>? = null

    init {

        setSections(sections)
    }

    fun setSections(sections: TreeMap<String, MutableList<StoryDay>>) {
        this.items = mutableListOf()
        for (sectionTitle in sections.keys) {
            items!!.add(sectionTitle)
            items!!.addAll(sections[sectionTitle]!!)
        }
    }

    fun setUploadProgress(selectedDate: String, percentage: Int) {
        val visibleDay = visibleViews!![selectedDate]
        visibleDay?.setUploadProgress(percentage)
    }

    override fun getItemViewType(position: Int): Int {
        val item = items!![position]

        if (item is String) {
            return ElementsType.VIEW_HEADER
        }

        return if (item is StoryDay) {
            ElementsType.VIEW_NORMAL
        } else -1

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ElementsType.VIEW_HEADER -> HeaderViewHolder(StorySectionView(parent.context))
            ElementsType.VIEW_NORMAL -> ViewHolder(StoryDayView(parent.context))
            else -> ViewHolder(StoryDayView(parent.context))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HeaderViewHolder) {
            val item = items!![position] as String
            holder.headerView.setTitle(item)
        }

        if (holder is ViewHolder) {
            val item = items!![position] as StoryDay
            val view = holder.dayView
            view.setDay(item)
            if (item.image != null) {
                Picasso.with(context).load(item.image!!.thumb.url).into(view.imageView)
            } else {
                view.imageView.setImageResource(android.R.color.transparent)
            }

            if (onClickListener != null) {
                view.setOnClickListener(onClickListener)
            }

            if (onLongClickListener != null) {
                view.setOnLongClickListener(onLongClickListener)
            }
        }
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        if (holder is ViewHolder) {
            if (this.visibleViews == null) {
                this.visibleViews = HashMap()
            }
            visibleViews!!.put(holder.dayView.storyDay.day, holder.dayView)
        }
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        if (holder is ViewHolder) {
            visibleViews!!.remove(holder.dayView.storyDay.day)
        }
    }

    override fun getItemCount(): Int {
        return items!!.size
    }
}
