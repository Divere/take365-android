package org.take365.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.take365.Models.StoryDay;
import org.take365.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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

        View view = inflater.inflate(R.layout.fragment_photo_player, container, false);

        TextView tvDay = (TextView) view.findViewById(R.id.tvDay);
        TextView tvMonth = (TextView) view.findViewById(R.id.tvMonth);
        TextView tvSeparator = (TextView) view.findViewById(R.id.tvSeparator);
        TextView tvYear = (TextView) view.findViewById(R.id.tvYear);

        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = df.parse(day.day);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        tvDay.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
        tvYear.setText(String.valueOf(calendar.get(Calendar.YEAR)));
        tvSeparator.setText(",");
        tvMonth.setText(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, new Locale("ru")));

        ImageView ivPhoto = (ImageView) view.findViewById(R.id.ivPhoto);
        Picasso.with(getContext()).load(day.image.thumbLarge.url).into(ivPhoto);
        return view;
    }
}