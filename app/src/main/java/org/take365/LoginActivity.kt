package org.take365

import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : Take365AuthActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnSignIn.setOnClickListener { loginWithCredentials(tfLogin.text.toString(), tfPassword.text.toString()) }

        btnRegister.setOnClickListener { startActivity(Intent(this@LoginActivity, RegistrationActivity::class.java)) }
    }
}
