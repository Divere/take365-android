package org.take365

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import org.take365.network.models.responses.login.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthenticationActivity : Take365AuthActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authitication)

        val preferences = PreferenceManager.getDefaultSharedPreferences(this)

        val token = preferences.getString("access_token", null)

        if (token == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            return
        }

        Take365App.setAccessToken(token)
        Take365App.getApi().loginWithToken(token).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (!response.isSuccessful) {
                    startActivity(Intent(this@AuthenticationActivity, LoginActivity::class.java))
                    return
                }

                handleSuccessAuthResponse(response)
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                startActivity(Intent(this@AuthenticationActivity, LoginActivity::class.java))
            }
        })

    }
}
