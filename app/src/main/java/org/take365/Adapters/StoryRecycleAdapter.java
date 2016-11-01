package org.take365.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import org.take365.Models.StoryDay;
import org.take365.Views.StoryDayView;
import org.take365.Views.StorySectionView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by divere on 30/10/2016.
 */

public class StoryRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public class ElementsType {
        public static final int VIEW_HEADER = 0;
        public static final int VIEW_NORMAL = 1;
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        StorySectionView headerView;

        HeaderViewHolder(StorySectionView v) {
            super(v);
            headerView = v;
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        StoryDayView dayView;

        ViewHolder(StoryDayView v) {
            super(v);
            dayView = v;
        }
    }

    private Context context;
    private ArrayList items;
    private View.OnClickListener onClickListener;
    private HashMap<String, StoryDayView> visibleViews;

    public StoryRecycleAdapter(Context context, TreeMap<String, List<StoryDay>> sections, View.OnClickListener onClickListener) {
        this.context = context;
        this.onClickListener = onClickListener;

        setSections(sections);
    }

    public void setSections(TreeMap<String, List<StoryDay>> sections) {
        this.items = new ArrayList();
        for (String sectionTitle : sections.keySet()) {
            items.add(sectionTitle);
            items.addAll(sections.get(sectionTitle));
        }
    }

    public void setUploadProgress(String selectedDate, int percentage) {
        StoryDayView visibleDay = visibleViews.get(selectedDate);
        if(visibleDay != null) {
            visibleDay.setUploadProgress(percentage);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Object item = items.get(position);

        if (item instanceof String) {
            return ElementsType.VIEW_HEADER;
        }

        if (item instanceof StoryDay) {
            return ElementsType.VIEW_NORMAL;
        }

        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ElementsType.VIEW_HEADER:
                return new HeaderViewHolder(new StorySectionView(parent.getContext()));
            case ElementsType.VIEW_NORMAL:
                return new ViewHolder(new StoryDayView(parent.getContext()));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            String item = (String) items.get(position);
            ((HeaderViewHolder) holder).headerView.setTitle(item);
        }

        if (holder instanceof ViewHolder) {
            StoryDay item = (StoryDay) items.get(position);
            StoryDayView view = ((ViewHolder) holder).dayView;
            view.setDay(item);
            if(item.image != null) {
                Picasso.with(context).load(item.image.thumb.url).into(view.imageView);
            } else {
                view.imageView.setImageResource(android.R.color.transparent);
            }

            if(item.uploadProgress != 0) {
                view.setUploadProgress(item.uploadProgress);
            }

            if(onClickListener != null) {
                view.setOnClickListener(onClickListener);
            }
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if(holder instanceof ViewHolder) {
            if(this.visibleViews == null) {
                this.visibleViews = new HashMap<>();
            }
            visibleViews.put(((ViewHolder) holder).dayView.day.day, ((ViewHolder) holder).dayView);
        }
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if(holder instanceof ViewHolder) {
            visibleViews.remove(((ViewHolder) holder).dayView.day.day);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
