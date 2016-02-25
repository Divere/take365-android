package org.take365.take365;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.take365.take365.Engine.Network.ApiManager;
import org.take365.take365.Engine.Network.GlobalContext;
import org.take365.take365.Engine.Network.Models.StoryDetailsModel;
import org.take365.take365.Engine.Network.Models.StoryListItemModel;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Ermakov-MAC on 12.02.16.
 */
public class UploadPhotoActivity extends Activity {
    ImageView imgPhoto;
    Button btnPublish;
    Spinner spnStories;
    TextView txtDay;
    TextView txtMonth;
    TextView txtYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_photo);

        imgPhoto = (ImageView)findViewById(R.id.imgPhoto);
        txtDay = (TextView)findViewById(R.id.txtDay);
        txtMonth = (TextView)findViewById(R.id.txtMonth);
        txtYear = (TextView)findViewById(R.id.txtYear);

        Button btnPublish = (Button)findViewById(R.id.btnPublish);
        final Spinner spnStories = (Spinner)findViewById(R.id.spnStories);


        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        txtDay.setText(String.valueOf(c.DAY_OF_MONTH) + " ");
        txtMonth.setText(String.valueOf(new SimpleDateFormat("MMMM").format(new Date())) + " ");
        txtYear.setText(String.valueOf(c.get(Calendar.YEAR)));

        final Bitmap image = (Bitmap)getIntent().getExtras().get("photo");
        final List<StoryListItemModel> storyList = GlobalContext.getInstance().myStoriesList;
        String[] storiesArray = new String[storyList.size()];
        for (int i = 0; i < storiesArray.length; i++) {
            storiesArray[i] = storyList.get(i).titile;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, storiesArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnStories.setAdapter(adapter);
        imgPhoto.setImageBitmap(image);

        btnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd:mm:yyyy HH:MM");
                ApiManager.getInstance().uploadImage(image, storyList.get(spnStories.getSelectedItemPosition()).id, simpleDateFormat.format(Calendar.getInstance().getTime()));
            }
        });


    }


}
