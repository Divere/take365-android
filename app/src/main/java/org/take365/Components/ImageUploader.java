package org.take365.Components;

import org.take365.Engine.Network.Models.Response.BaseResponse;
import org.take365.Take365App;

import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Callback;

/**
 * Created by divere on 31/10/2016.
 */

public class ImageUploader {

    public static void uploadImage(int storyId, byte[] imageData, String date, Callback<BaseResponse> callback) {
        if(date == null) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date today = new Date();
            date = df.format(today);
        }

        RequestBody file = RequestBody.create(MediaType.parse("multipart/form-data"), imageData);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", "image.jpg", file);

        RequestBody imageName = RequestBody.create(MediaType.parse("multipart/form-data"), "image.jpg");
        RequestBody storyIdFormatted = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(storyId));
        RequestBody targetType = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf("2"));
        RequestBody mediaType = RequestBody.create(MediaType.parse("multipart/form-data"), "storyImage");
        RequestBody dateFormatted = RequestBody.create(MediaType.parse("multipart/form-data"), date);

        Take365App.getApi().uploadPhoto(imageName, storyIdFormatted, targetType, mediaType, dateFormatted, filePart).enqueue(callback);
    }

}
