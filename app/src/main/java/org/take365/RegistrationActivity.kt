package org.take365

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_registration.*

import org.take365.Network.models.responses.BaseResponse

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationActivity : Take365AuthActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        title = "Регистрация"

        btnContinue.setOnClickListener {
            showProgressDialog("регистрируем...")
            Take365App.getApi().register(etUsername.text.toString(), etEmail.text.toString(), etPassword.text.toString()).enqueue(object : Callback<BaseResponse> {
                override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                    hideProgressDialog()
                    if (!response.isSuccessful) {
                        showApiError(response)
                        return
                    }

                    showAlertDialog("Регистрация прошла успешно") { loginWithCredentials(etUsername.text.toString(), etPassword.text.toString()) }
                }

                override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                    hideProgressDialog()
                    showConnectionError()
                }
            })
        }
    }
}
