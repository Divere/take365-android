package org.take365

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.take365.DrawerActivityViews.FeedListView

import org.take365.DrawerActivityViews.StoryListView

class MainActivity : Take365Activity(), NavigationView.OnNavigationItemSelectedListener {

    private var currentView: View? = null

    private lateinit var storyListView: StoryListView
    private lateinit var feedView: FeedListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        fab.setOnClickListener { startActivity(Intent(this@MainActivity, CreateStoryActivity::class.java)) }

        val toggle = ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.setDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener(this)

        storyListView = StoryListView(this)
        feedView = FeedListView(this)

        showStories()
    }

    override fun onResume() {
        super.onResume()
        when (currentView) {
            is StoryListView -> (currentView as StoryListView).updateStoryList()
            is FeedListView -> (currentView as FeedListView).updateFeed()
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
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        when (id) {
            R.id.nav_gallery -> {
                showStories()
            }
            R.id.nav_feed -> {
                showFeed()
            }
            R.id.nav_exit -> {
                logout()
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun logout() {
        PreferenceManager.getDefaultSharedPreferences(this).edit().remove("access_token").apply()
        Take365App.clearAccessToken()
        val intent = Intent(this, AuthenticationActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun showFeed() {
        setView(feedView)
        feedView.updateFeed()
        title = "Лента"
        fab.visibility = View.GONE
    }

    private fun showStories() {
        setView(storyListView)
        storyListView.updateStoryList()
        title = "Мои истории"
        fab.visibility = View.VISIBLE
    }

    private fun setView(view: View) {
        viewsContainer!!.removeAllViews()
        viewsContainer!!.addView(view)
        currentView = view
    }
}
