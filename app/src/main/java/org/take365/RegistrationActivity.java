package org.take365;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.take365.Network.Models.Response.BaseResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends Take365AuthActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setTitle("Регистрация");

        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);

        Button btnContinue = (Button) findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog("регистрируем...");
                Take365App.getApi().register(etUsername.getText().toString(), etEmail.getText().toString(), etPassword.getText().toString()).enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        hideProgressDialog();
                        if (!response.isSuccessful()) {
                            showApiError(response);
                            return;
                        }

                         showAlertDialog("Регистрация прошла успешно", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                loginWithCredentials(etUsername.getText().toString(), etPassword.getText().toString());
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        hideProgressDialog();
                        showConnectionError();
                    }
                });
            }
        });
    }
}
