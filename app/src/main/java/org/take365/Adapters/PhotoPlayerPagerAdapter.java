package org.take365.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.take365.Fragments.PhotoPlayerFragment;
import org.take365.Models.StoryDay;

import java.util.ArrayList;

/**
 * Created by divere on 02/11/2016.
 */

public class PhotoPlayerPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<StoryDay> items;

    public PhotoPlayerPagerAdapter(FragmentManager fm, ArrayList<StoryDay> items) {
        super(fm);
        this.items = items;
    }

    @Override
    public Fragment getItem(int position) {
        return PhotoPlayerFragment.newInstance(items.get(position));
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return items.get(position).image.title;
    }
}
