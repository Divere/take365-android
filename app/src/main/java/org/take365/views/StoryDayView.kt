package org.take365.views

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.view_story_day.view.*

import org.take365.models.StoryDay
import org.take365.R

/**
 * Created by divere on 29/10/2016.
 */

class StoryDayView(context: Context) : RelativeLayout(context) {

    lateinit var storyDay: StoryDay

    init {
        LayoutInflater.from(context).inflate(R.layout.view_story_day, this)
    }

    fun setDay(day: StoryDay) {
        this.storyDay = day
        this.uploadProgressBar.visibility = View.INVISIBLE
        this.uploadProgressBar.progress = 0
        tvDay.text = day.day.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[2].toString()
    }

    fun setUploadProgress(percentage: Int) {
        if (uploadProgressBar.visibility == View.INVISIBLE) {
            uploadProgressBar.visibility = View.VISIBLE
        }
        uploadProgressBar.progress = percentage
    }
}
