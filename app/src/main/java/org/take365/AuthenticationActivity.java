package org.take365;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import org.take365.Network.Models.Request.LoginWithTokenRequest;
import org.take365.Network.Models.Response.LoginResponse.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthenticationActivity extends Take365AuthActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authitication);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        String token = preferences.getString("access_token", null);

        if(token == null){
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }

        Take365App.setAccessToken(token);
        Take365App.getApi().loginWithToken(new LoginWithTokenRequest(token)).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(!response.isSuccessful()) {
                    startActivity(new Intent(AuthenticationActivity.this, LoginActivity.class));
                    return;
                }

                handleSuccessAuthResponse(response);
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                startActivity(new Intent(AuthenticationActivity.this, LoginActivity.class));
            }
        });

    }
}
