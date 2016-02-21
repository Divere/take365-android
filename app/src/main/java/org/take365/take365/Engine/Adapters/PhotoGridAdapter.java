package org.take365.take365.Engine.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.take365.take365.Engine.Network.Models.StoryImageImagesModel;
import org.take365.take365.R;

import java.io.InputStream;
import java.util.List;

/**
 * Created by Ermakov-MAC on 19.02.16.
 */
public class PhotoGridAdapter extends BaseAdapter {

    List<StoryImageImagesModel> storyImageModels;
    Context context;

    public PhotoGridAdapter(Context context, List<StoryImageImagesModel> storyImageModels) {
        this.storyImageModels = storyImageModels;
        this.context = context;
    }

    @Override
    public int getCount() {
        return storyImageModels.size();
    }

    @Override
    public Object getItem(int position) {

        if (storyImageModels.contains(position))
            return storyImageModels.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = inflater.inflate(R.layout.control_photo_month_item, parent, false);
        }
        TextView txtDay = (TextView)view.findViewById(R.id.txtDay);
        ImageView imageView = (ImageView)view.findViewById(R.id.imgPhoto);

        imageView.getLayoutParams().height= storyImageModels.get(position).thumbLarge.height;
        imageView.getLayoutParams().width= storyImageModels.get(position).thumbLarge.width;

        //imageView.(Uri.parse(storyImageModels.get(position).thumbLarge.url));
        new DownloadImageTask(imageView).execute(storyImageModels.get(position).thumbLarge.url);
        txtDay.setText(String.valueOf(storyImageModels.get(position).date.getDay()));
        return view;
    }

    public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}


