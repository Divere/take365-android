package org.take365

import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_create_story.*

import org.take365.network.models.responses.BaseResponse
import org.take365.network.models.responses.story.CreateStoryResponse

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
            Take365App.getApi().createStory(todayString, status, etStoryName.text.toString(), etDescription.text.toString()).enqueue(object : Callback<CreateStoryResponse> {
                override fun onResponse(call: Call<CreateStoryResponse>, response: Response<CreateStoryResponse>) {
                    hideProgressDialog()
                    if (!response.isSuccessful) {
                        showApiError(response)
                        return
                    }

                    StoryActivity.startActivity(this@CreateStoryActivity, response.body()!!.result)
                    finish()
                }

                override fun onFailure(call: Call<CreateStoryResponse>, t: Throwable) {
                    hideProgressDialog()
                    showConnectionError()
                }
            })
        }
    }
}
