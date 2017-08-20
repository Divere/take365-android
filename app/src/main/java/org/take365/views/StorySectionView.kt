package org.take365.views

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.view_story_section.view.*

import org.take365.helpers.DateHelpers
import org.take365.R

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

/**
 * Created by divere on 29/10/2016.
 */

class StorySectionView(context: Context) : FrameLayout(context) {

    private var title: String? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.view_story_section, this)
    }

    fun setTitle(title: String) {
        this.title = title

        var date = Date()
        val df = SimpleDateFormat("yyyy-MM")
        try {
            date = df.parse(title)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        val calendar = Calendar.getInstance()
        calendar.time = date

        tvYear.text = calendar.get(Calendar.YEAR).toString()
        tvSeparator.text = ","
        tvMonth.text = DateHelpers.getMonthDate(calendar.get(Calendar.MONTH))
    }
}
