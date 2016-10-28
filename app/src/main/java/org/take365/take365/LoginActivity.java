package org.take365.take365;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.take365.take365.Engine.Network.Models.Request.LoginWithTokenRequest;
import org.take365.take365.Engine.Network.Models.Response.LoginResponse.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText tfLogin = (EditText) findViewById(R.id.tfLogin);
        final EditText tfPassword = (EditText) findViewById(R.id.tfPassword);
        Button btnSignIn = (Button) findViewById(R.id.btnSignIn);

        preferences = getPreferences(Context.MODE_PRIVATE);

        String token = preferences.getString("access_token", null);
        if(token != null){
            Take365Application.setAccessToken(token);
            Take365Application.getApi().loginWithToken(new LoginWithTokenRequest(token)).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    handleSuccessAuthResponse(response);
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {

                    }
            });
        }

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Take365Application.getApi().login(tfLogin.getText().toString(), tfPassword.getText().toString()).enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        handleSuccessAuthResponse(response);
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {

                    }
                });
            }
        });
    }

    private void handleSuccessAuthResponse(Response<LoginResponse> response) {
        if(response.isSuccessful() && response.body().result != null) {
            preferences.edit().putString("access_token", response.body().result.token).apply();
            Take365Application.setCurrentUser(response.body().result);
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
    }
}
