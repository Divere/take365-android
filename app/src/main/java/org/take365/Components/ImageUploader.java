package org.take365.Components;

import org.take365.Engine.Network.Models.Response.BaseResponse;
import org.take365.Take365App;

import java.io.File;
import java.io.InputStream;
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

    private static void uploadImage(int storyId, String date, Callback<BaseResponse> result, ProgressRequestBody file) {
        if(date == null) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date today = new Date();
            date = df.format(today);
        }

        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", "image.jpg", file);

        RequestBody imageName = RequestBody.create(MediaType.parse("multipart/form-data"), "image.jpg");
        RequestBody storyIdFormatted = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(storyId));
        RequestBody targetType = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf("2"));
        RequestBody mediaType = RequestBody.create(MediaType.parse("multipart/form-data"), "storyImage");
        RequestBody dateFormatted = RequestBody.create(MediaType.parse("multipart/form-data"), date);

        Take365App.getApi().uploadPhoto(imageName, storyIdFormatted, targetType, mediaType, dateFormatted, filePart).enqueue(result);
    }

    public static void uploadImage(int storyId, InputStream image, String date, ProgressRequestBody.UploadCallbacks progress, Callback<BaseResponse> result) {
        ProgressRequestBody file = new ProgressRequestBody(image, progress);
        uploadImage(storyId, date, result, file);
    }

    public static void uploadImage(int storyId, File image, String date, ProgressRequestBody.UploadCallbacks progress, Callback<BaseResponse> result) {
        ProgressRequestBody file = new ProgressRequestBody(image, progress);
        uploadImage(storyId, date, result, file);
    }

}
