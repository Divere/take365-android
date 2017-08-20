package org.take365.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_photo_player.*

import org.take365.models.StoryDay
import org.take365.R

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Created by divere on 02/11/2016.
 */

class PhotoPlayerFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_photo_player, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        val day = arguments.getSerializable("storyDay") as StoryDay

        var date = Date()
        val df = SimpleDateFormat("yyyy-MM-dd")
        try {
            date = df.parse(day.day)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        val calendar = Calendar.getInstance()
        calendar.time = date

        tvDay.text = calendar.get(Calendar.DAY_OF_MONTH).toString()
        tvYear.text = calendar.get(Calendar.YEAR).toString()
        tvSeparator.text = ","
        tvMonth.text = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale("ru"))

        Picasso.with(context).load(day.image!!.image.url).into(ivPhoto)
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        fun newInstance(storyDay: StoryDay): PhotoPlayerFragment {
            val fragment = PhotoPlayerFragment()
            val args = Bundle()
            args.putSerializable("storyDay", storyDay)
            fragment.arguments = args
            return fragment
        }
    }
}