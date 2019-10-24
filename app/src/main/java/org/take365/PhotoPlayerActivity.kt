package org.take365

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_photo_player.*
import org.take365.adapters.PhotoPlayerPagerAdapter
import org.take365.models.StoryDay
import java.util.*

class PhotoPlayerActivity : Take365Activity() {

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * [FragmentPagerAdapter] derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private var mSectionsPagerAdapter: PhotoPlayerPagerAdapter? = null

    /**
     * The [ViewPager] that will host the section contents.
     */

    private var currentItem: StoryDay? = null
    private var items: ArrayList<StoryDay>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_player)
        title = "Take365"

        currentItem = intent.getSerializableExtra("currentItem") as StoryDay
        val allItems = intent.getSerializableExtra("items") as ArrayList<StoryDay>

        this.items = ArrayList()
        for (item in allItems) {
            if (item.image != null) {
                this.items!!.add(item)
            }
        }

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = PhotoPlayerPagerAdapter(supportFragmentManager, items!!)

        // Set up the ViewPager with the sections adapter.
        mViewPager!!.offscreenPageLimit = 6
        mViewPager!!.adapter = mSectionsPagerAdapter

        for (i in items!!.indices) {
            if (items!![i].day == currentItem!!.day) {
                mViewPager!!.currentItem = i
                break
            }
        }

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_photo_player, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        //        if (id == R.id.action_settings) {
        //            return true;
        //        }

        return super.onOptionsItemSelected(item)
    }
}
