package org.take365.take365;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import org.take365.take365.Controls.StoryListControl;
import org.take365.take365.Engine.Network.ApiEvents;
import org.take365.take365.Engine.Network.ApiManager;
import org.take365.take365.Engine.Network.Models.Response.StoryResponse.StoryListResponse;

public class MainActivity extends AppCompatActivity {
    private int REQUEST_IMAGE_CAPTURE = 1;
    private StoryListControl storyListControl;
    private FrameLayout content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        content = (FrameLayout)findViewById(R.id.content);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        ApiManager.getInstance().Events = new ApiEvents() {
            @Override
            public void getStoryListResult(StoryListResponse result, String error) {
                if (result.result.size()>0) {
                    setControl(storyListControl);
                    storyListControl.updateList(result.result);
                }
            }
        };

        storyListControl = new StoryListControl(this);
        ApiManager.getInstance().getStoryList("me", 0, 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            Intent intent = new Intent(this, UploadPhotoActivity.class);
            intent.putExtra("photo", imageBitmap);
            startActivity(intent);
        }
    }

    private void setControl(View view) {
        content.removeAllViews();
        content.addView(view);
    }
}
