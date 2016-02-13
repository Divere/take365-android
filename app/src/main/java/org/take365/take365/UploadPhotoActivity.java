package org.take365.take365;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * Created by Ermakov-MAC on 12.02.16.
 */
public class UploadPhotoActivity extends Activity {
    ImageView imgPhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_photo);

        imgPhoto = (ImageView)findViewById(R.id.imgPhoto);

        Bitmap image = (Bitmap)getIntent().getExtras().get("photo");
        imgPhoto.setImageBitmap(image);
    }
}
