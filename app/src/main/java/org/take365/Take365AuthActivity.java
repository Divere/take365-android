package org.take365;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.take365.Engine.Network.Models.Response.LoginResponse.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by divere on 04/11/2016.
 */

public class Take365AuthActivity extends Take365Activity {

    protected void loginWithCredentials(String userName, String password) {
        showProgressDialog("входим...");
        Take365App.getApi().login(userName, password).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                hideProgressDialog();
                if (!response.isSuccessful()) {
                    showApiError(response);
                    return;
                }

                handleSuccessAuthResponse(response);
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                hideProgressDialog();
                showConnectionError();
            }
        });
    }

    protected void handleSuccessAuthResponse(Response<LoginResponse> response) {
        if(response.isSuccessful() && response.body().result != null) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            preferences.edit().putString("access_token", response.body().result.token).apply();
            Take365App.setCurrentUser(response.body().result);
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}
