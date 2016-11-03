package org.take365.Helpers;

import android.content.Context;

import com.google.gson.Gson;

import org.take365.Engine.Network.Models.Response.BaseResponse;

import java.io.IOException;

import retrofit2.Response;

/**
 * Created by divere on 03/11/2016.
 */

public class ApiErrorHelper {
    public static void handleApiError(Context context, Response response)
    {
        try {
            BaseResponse errorResponse = new Gson().fromJson(response.errorBody().string(), BaseResponse.class);
            DialogHelpers.AlertDialog(context, errorResponse.errors[0].value, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
