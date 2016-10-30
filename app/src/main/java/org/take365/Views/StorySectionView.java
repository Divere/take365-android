package org.take365.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.take365.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by divere on 29/10/2016.
 */

public class StorySectionView extends FrameLayout {

    private String title;

    private TextView tvMonth;
    private TextView tvSeparator;
    private TextView tvYear;

    public StorySectionView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.view_story_section, this);

        tvMonth = (TextView) findViewById(R.id.tvMonth);
        tvSeparator = (TextView) findViewById(R.id.tvSeparator);
        tvYear = (TextView) findViewById(R.id.tvYear);
    }

    public void setTitle(String title) {
        this.title = title;

        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        try {
            date = df.parse(title);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        tvYear.setText(String.valueOf(calendar.get(Calendar.YEAR)));
        tvSeparator.setText(",");
        tvMonth.setText(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ROOT));
    }
}
