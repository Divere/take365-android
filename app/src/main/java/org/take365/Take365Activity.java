package org.take365;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;

import org.take365.Network.Models.Response.BaseResponse;
import org.take365.Network.Models.Response.ErrorResponse;
import org.take365.Network.Models.Response.LoginResponse.LoginResult;

import java.io.IOException;

import retrofit2.Response;

/**
 * Created by divere on 04/11/2016.
 */

public class Take365Activity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        LoginResult currentUser = Take365App.getCurrentUser();
        if (currentUser != null) {
            outState.putSerializable("currentUser", Take365App.getCurrentUser());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        LoginResult currentUser = (LoginResult) savedInstanceState.getSerializable("currentUser");
        if (currentUser != null) {
            Take365App.setCurrentUser(currentUser);
        }
    }

    public void showAskDialog(String question, DialogInterface.OnClickListener positiveClickListener) {
        showAskDialog(question, positiveClickListener, null);
    }

    public void showAskDialog(String question, DialogInterface.OnClickListener positiveClickListener, DialogInterface.OnClickListener negativeClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(question).setPositiveButton("Продолжить", positiveClickListener)
                .setNegativeButton("Отмена", negativeClickListener).show();
    }

    public void showAlertDialog(String message, DialogInterface.OnClickListener continueCallback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message).setPositiveButton("Продолжить", continueCallback).show();
    }

    public void showApiError(Response response) {
        String responseString = null;

        try {
            responseString = response.errorBody().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (responseString == null) {
            Log.e("take365", "Failed get response string from response.");
            return;
        }

        String errorMessage = null;
        try {
            BaseResponse errorResponse = new Gson().fromJson(responseString, BaseResponse.class);
            errorMessage = errorResponse.errors[0].value;
            if (errorMessage != null) {
                showAlertDialog(errorMessage, null);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            ErrorResponse errorResponse = new Gson().fromJson(responseString, ErrorResponse.class);
            errorMessage = errorResponse.errors[0];
            if (errorMessage != null) {
                showAlertDialog(errorMessage, null);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



        Log.e("take365", "Failed parse error from response");
    }

    public void showConnectionError() {
        showAlertDialog("Не удалось установить соединение с сервером. Попробуйте позднее.", null);
    }

    public void showProgressDialog(String message) {
        progressDialog = ProgressDialog.show(this, null, message, true, false);
    }

    public void hideProgressDialog() {
        if (progressDialog != null) {
            progressDialog.cancel();
        }
    }
}
