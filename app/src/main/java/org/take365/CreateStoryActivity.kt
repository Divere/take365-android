package org.take365

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import kotlinx.android.synthetic.main.activity_create_story.*

import org.take365.Network.Models.Response.BaseResponse

import java.text.SimpleDateFormat
import java.util.Date

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateStoryActivity : Take365Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_story)
        title = "Создание истории"

        btnContinue.setOnClickListener {
            showProgressDialog("создаём историю...")
            val status = if (swPrivacy.isChecked) 0 else 1
            val df = SimpleDateFormat("yyyy-MM-dd")
            val todayString = df.format(Date())
            Take365App.getApi().createStory(todayString, status, etStoryName.text.toString(), etDescription.text.toString()).enqueue(object : Callback<BaseResponse> {
                override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                    hideProgressDialog()
                    if (!response.isSuccessful) {
                        showApiError(response)
                        return
                    }

                    startActivity(Intent(this@CreateStoryActivity, MainActivity::class.java))
                }

                override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                    hideProgressDialog()
                    showConnectionError()
                }
            })
        }
    }
}
