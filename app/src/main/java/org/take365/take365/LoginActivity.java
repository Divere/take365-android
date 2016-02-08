package org.take365.take365;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import org.take365.take365.Engine.Network.ApiEvents;
import org.take365.take365.Engine.Network.ApiManager;
import org.take365.take365.Engine.Network.Models.Response.LoginResponse.LoginResult;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText tfLogin = (EditText) findViewById(R.id.tfLogin);
        final EditText tfPassword = (EditText) findViewById(R.id.tfPassword);
        Button btnSignIn = (Button) findViewById(R.id.btnSignIn);

        final ApiManager api = ApiManager.getInstance();
        api.Events = new ApiEvents(){
            @Override
            public void loginCompleted(LoginResult result, String error) {
                if(error == null){
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
            }
        };

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                api.login(tfLogin.getText().toString(), tfPassword.getText().toString());
            }
        });
    }
}
