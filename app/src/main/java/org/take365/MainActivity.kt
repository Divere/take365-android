package org.take365

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

import org.take365.DrawerActivityViews.StoryListView

class MainActivity : Take365Activity(), NavigationView.OnNavigationItemSelectedListener {

    private var currentView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "Мои истории"

        setSupportActionBar(toolbar)

        fab.setOnClickListener { startActivity(Intent(this@MainActivity, CreateStoryActivity::class.java)) }

        val toggle = ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.setDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener(this)

        setView(StoryListView(this))
    }

    override fun onResume() {
        super.onResume()
        if (currentView is StoryListView) {
            (currentView as StoryListView).updateStoryList()
        }

    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        //        //noinspection SimplifiableIfStatement
        //        if (id == R.id.action_settings) {
        //            return true;
        //        }

        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        when (id) {
            R.id.nav_exit -> {
                PreferenceManager.getDefaultSharedPreferences(this).edit().remove("access_token").apply()
                Take365App.clearAccessToken()
                val intent = Intent(this, AuthenticationActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
        }

        //        if (id == R.id.nav_camera) {
        //            // Handle the camera action
        //        } else if (id == R.id.nav_gallery) {
        //
        //        } else if (id == R.id.nav_slideshow) {
        //
        //        } else if (id == R.id.nav_manage) {
        //
        //        } else if (id == R.id.nav_share) {
        //
        //        } else if (id == R.id.nav_send) {
        //
        //        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun setView(view: View) {
        viewsContainer!!.removeAllViews()
        viewsContainer!!.addView(view)
        currentView = view
    }
}
