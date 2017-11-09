package org.take365

import android.content.Intent
import android.preference.PreferenceManager

import org.take365.network.models.responses.login.LoginResponse

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by divere on 04/11/2016.
 */

open class Take365AuthActivity : Take365Activity() {

    protected fun loginWithCredentials(userName: String, password: String) {
        showProgressDialog("входим...")
        Take365App.getApi().login(userName, password).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                hideProgressDialog()
                if (!response.isSuccessful) {
                    showApiError(response)
                    return
                }

                handleSuccessAuthResponse(response)
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                hideProgressDialog()
                showConnectionError()
            }
        })
    }

    protected fun handleSuccessAuthResponse(response: Response<LoginResponse>) {
        if (response.isSuccessful && response.body()!!.result != null) {
            val preferences = PreferenceManager.getDefaultSharedPreferences(this)
            preferences.edit().putString("access_token", response.body()!!.result!!.token).apply()
            Take365App.setCurrentUser(response.body()!!.result)
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
