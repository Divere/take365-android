package org.take365.components

import org.take365.Network.models.responses.BaseResponse
import org.take365.Take365App

import java.io.File
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Date

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Callback

/**
 * Created by divere on 31/10/2016.
 */

object ImageUploader {

    private fun uploadImage(storyId: Int, date: String?, result: Callback<BaseResponse>, file: ProgressRequestBody) {
        var date = date
        if (date == null) {
            val df = SimpleDateFormat("yyyy-MM-dd")
            val today = Date()
            date = df.format(today)
        }

        val filePart = MultipartBody.Part.createFormData("file", "image.jpg", file)

        val imageName = RequestBody.create(MediaType.parse("multipart/form-data"), "image.jpg")
        val storyIdFormatted = RequestBody.create(MediaType.parse("multipart/form-data"), storyId.toString())
        val targetType = RequestBody.create(MediaType.parse("multipart/form-data"), "2")
        val mediaType = RequestBody.create(MediaType.parse("multipart/form-data"), "storyImage")
        val dateFormatted = RequestBody.create(MediaType.parse("multipart/form-data"), date!!)

        Take365App.getApi().uploadPhoto(imageName, storyIdFormatted, targetType, mediaType, dateFormatted, filePart).enqueue(result)
    }

    fun uploadImage(storyId: Int, image: InputStream, date: String?, progress: ProgressRequestBody.UploadCallbacks, result: Callback<BaseResponse>) {
        val file = ProgressRequestBody(image, progress)
        uploadImage(storyId, date, result, file)
    }

    fun uploadImage(storyId: Int, image: File, date: String?, progress: ProgressRequestBody.UploadCallbacks, result: Callback<BaseResponse>) {
        val file = ProgressRequestBody(image, progress)
        uploadImage(storyId, date, result, file)
    }

}
