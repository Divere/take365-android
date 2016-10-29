package org.take365.Helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.take365.Engine.Network.Models.Response.LoginResponse.LoginResponse;
import org.take365.LoginActivity;
import org.take365.MainActivity;
import org.take365.Take365Application;

import retrofit2.Response;

/**
 * Created by divere on 28/10/2016.
 */

public class AuthenticationHelper {

    public static void handleSuccessAuthResponse(Activity activity, Response<LoginResponse> response) {
        if(response.isSuccessful() && response.body().result != null) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
            preferences.edit().putString("access_token", response.body().result.token).apply();
            Take365Application.setCurrentUser(response.body().result);
            activity.startActivity(new Intent(activity, MainActivity.class));
        }
    }

}
