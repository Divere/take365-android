package org.take365;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;

import org.take365.Engine.Network.Models.Response.BaseResponse;

import java.io.IOException;

import retrofit2.Response;

/**
 * Created by divere on 04/11/2016.
 */

public class Take365Activity extends AppCompatActivity {

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
        try {
            BaseResponse errorResponse = new Gson().fromJson(response.errorBody().string(), BaseResponse.class);
            showAlertDialog(errorResponse.errors[0].value, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showConnectionError() {
        showAlertDialog("Не удалось установить соединение с сервером. Попробуйте позднее.", null);
    }
}
