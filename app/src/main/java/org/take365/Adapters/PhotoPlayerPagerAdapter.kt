package org.take365.Adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

import org.take365.Fragments.PhotoPlayerFragment
import org.take365.Models.StoryDay

import java.util.ArrayList

/**
 * Created by divere on 02/11/2016.
 */

class PhotoPlayerPagerAdapter(fm: FragmentManager, private val items: ArrayList<StoryDay>) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return PhotoPlayerFragment.newInstance(items[position])
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return items[position].image!!.title
    }
}
