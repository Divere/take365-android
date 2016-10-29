package org.take365;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.take365.Engine.Network.Models.Response.LoginResponse.LoginResponse;
import org.take365.Helpers.AuthenticationHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText tfLogin = (EditText) findViewById(R.id.tfLogin);
        final EditText tfPassword = (EditText) findViewById(R.id.tfPassword);
        Button btnSignIn = (Button) findViewById(R.id.btnSignIn);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Take365App.getApi().login(tfLogin.getText().toString(), tfPassword.getText().toString()).enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        AuthenticationHelper.handleSuccessAuthResponse(LoginActivity.this, response);
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {

                    }
                });
            }
        });
    }
}
