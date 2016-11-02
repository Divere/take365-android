package org.take365.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.take365.Models.StoryDay;
import org.take365.R;

/**
 * Created by divere on 02/11/2016.
 */

public class PhotoPlayerFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public PhotoPlayerFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PhotoPlayerFragment newInstance(StoryDay storyDay) {
        PhotoPlayerFragment fragment = new PhotoPlayerFragment();
        Bundle args = new Bundle();
        args.putSerializable("storyDay", storyDay);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        StoryDay day = (StoryDay) getArguments().getSerializable("storyDay");

        View rootView = inflater.inflate(R.layout.fragment_photo_player, container, false);
        ImageView ivPhoto = (ImageView) rootView.findViewById(R.id.ivPhoto);
        Picasso.with(getContext()).load(day.image.thumbLarge.url).into(ivPhoto);
        return rootView;
    }
}