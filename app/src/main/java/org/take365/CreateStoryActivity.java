package org.take365;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import org.take365.Engine.Network.Models.Response.BaseResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateStoryActivity extends Take365Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_story);
        setTitle("Создание истории");

        final EditText etStoryName = (EditText) findViewById(R.id.etStoryName);
        final EditText etDescription = (EditText) findViewById(R.id.etDescription);
        final Switch swPrivacy = (Switch) findViewById(R.id.swPrivacy);
        final Button btnContinue = (Button) findViewById(R.id.btnContinue);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int status = swPrivacy.isChecked() ? 0 : 1;
                Take365App.getApi().createStory(status, etStoryName.getText().toString(), etDescription.getText().toString()).enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if(!response.isSuccessful()) {
                            showApiError(response);
                            return;
                        }

                        startActivity(new Intent(CreateStoryActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        showConnectionError();
                    }
                });
            }
        });
    }
}
