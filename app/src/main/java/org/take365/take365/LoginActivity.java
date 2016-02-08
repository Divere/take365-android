package org.take365.take365;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    EditText tfLogin, tfPassword;
    Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tfLogin = (EditText) findViewById(R.id.tfLogin);
        tfPassword = (EditText) findViewById(R.id.tfPassword);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);

        
    }
}
