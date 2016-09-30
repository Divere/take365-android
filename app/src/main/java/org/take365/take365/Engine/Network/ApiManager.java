package org.take365.take365.Engine.Network;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.entity.mime.HttpMultipartMode;
import cz.msebera.android.httpclient.entity.mime.MultipartEntityBuilder;
import cz.msebera.android.httpclient.entity.mime.content.ByteArrayBody;
import cz.msebera.android.httpclient.entity.mime.content.FileBody;
import cz.msebera.android.httpclient.entity.mime.content.StringBody;

import org.take365.take365.Engine.Network.Models.Request.LoginRequest;
import org.take365.take365.Engine.Network.Models.Response.BaseResponse;
import org.take365.take365.Engine.Network.Models.Response.LoginResponse.LoginResponse;
import org.take365.take365.Engine.Network.Models.Response.StoryResponse.StoryDetailResponse;
import org.take365.take365.Engine.Network.Models.Response.StoryResponse.StoryListResponse;
import org.take365.take365.Engine.Network.Models.StoryListItemModel;
import org.take365.take365.Engine.Network.Models.StoryPrivateLevel;
import org.take365.take365.Take365Application;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by evgeniy on 08.02.16.
 */
public class ApiManager {

    private static final String URL = "http://take365.org";
    private static AsyncHttpClient client = new AsyncHttpClient();
    private static ApiManager instance = null;
    private static Context context;
    private static String userName;

    public ApiEvents Events;

    public String AccessToken;
    public ArrayList<StoryListItemModel> Stories;

    public static ApiManager getInstance() {
        if (instance == null) {
            context = Take365Application.getAppContext();
            instance = new ApiManager();
        }

        return instance;
    }

    public void login(String username, String password) {

        LoginRequest request = new LoginRequest();
        request.username = username;
        request.password = password;
        this.userName = username;

        post("api/auth/login", request, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String json = new String(responseBody);
                LoginResponse response = new Gson().fromJson(json, LoginResponse.class);
                AccessToken = response.result.token;
                if (Events != null && response.result != null) {
                    Events.loginCompleted(response.result, null);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                String json = new String(responseBody);
                BaseResponse response = new Gson().fromJson(json, BaseResponse.class);
                if (Events != null) {
                    Events.loginCompleted(null, response.errors[0].value);
                }
            }
        });
    }

    public void getStory(int storyId) {
        get("/api/story/" + storyId  + "?accessToken=" + AccessToken, new AsyncHttpObjectResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String json = new String(responseBody);
                StoryDetailResponse response = new Gson().fromJson(json, StoryDetailResponse.class);
                if (Events != null) {
                    Events.getStoryResult(response.result, null);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                super.onFailure(statusCode, headers, responseBody, error);
            }
        });
    }

    public void getStoryList(String userName, int page, int maxItems) {
        get("api/story/list?page=" + page + "&maxItems=" + maxItems + "&username=" + userName + "&accessToken=" + AccessToken, new AsyncHttpObjectResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String json = new String(responseBody);
                StoryListResponse response = new Gson().fromJson(json, StoryListResponse.class);
                if (Events != null) {
                    Events.getStoryListResult(response, null);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                super.onFailure(statusCode, headers, responseBody, error);
            }
        });
    }


    public void createStory(String title, StoryPrivateLevel privateLevel, String description) {

    }

    public void uploadImage(Bitmap image, int storyId, String date) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        try {
//            [formData appendPartWithFormData:[@"image.jpg" dataUsingEncoding:NSUTF8StringEncoding] name:@"name"];
//            [formData appendPartWithFormData:[[NSString stringWithFormat:@"%d", storyId] dataUsingEncoding:NSUTF8StringEncoding] name:@"targetId"];
//            [formData appendPartWithFormData:[@"2" dataUsingEncoding:NSUTF8StringEncoding] name:@"targetType"];
//            [formData appendPartWithFormData:[@"storyImage" dataUsingEncoding:NSUTF8StringEncoding] name:@"mediaType"];
//            [formData appendPartWithFormData:[date dataUsingEncoding:NSUTF8StringEncoding] name:@"date"];
//            [formData appendPartWithFileData:image name:@"file" fileName:@"image.jpg" mimeType:@"image/jpeg"];
//            [formData appendPartWithFormData:[_AccessToken dataUsingEncoding:NSUTF8StringEncoding] name:@"accessToken"];
            HttpPost httppost = new HttpPost(URL + "/" +"/api/media/upload");
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addPart("name", new ByteArrayBody("image.jpg".getBytes(), ""));
            builder.addPart("targetType", new ByteArrayBody("2".getBytes(), ""));
            builder.addPart("mediaType", new ByteArrayBody("storyImage".getBytes(), ""));
            builder.addPart("file", new ByteArrayBody(byteArray, ""));
            builder.addPart("date", new ByteArrayBody(date.getBytes(), ""));
            builder.addPart("targetId", new ByteArrayBody(String.valueOf(storyId).getBytes(), ""));
            builder.addPart("accessToken", new ByteArrayBody(AccessToken.getBytes(), ""));
            HttpEntity entity = builder.build();
            client.post(context, URL + "/" + "api/media/upload", entity, ""  ,new AsyncHttpObjectResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    super.onSuccess(statusCode, headers, responseBody);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    super.onFailure(statusCode, headers, responseBody, error);
                    String json = new String(responseBody);
                }
            });

        } catch (Exception e) {
            Log.e("Error uploading: ", e.getLocalizedMessage(), e);
        }
    }

    public static void get(String method, AsyncHttpObjectResponseHandler handler) {
        String reqest = URL + "/" + method;
        client.get(reqest, handler);
    }

    public static void post(String method, Object request, AsyncHttpResponseHandler handler) {
        StringEntity entity = null;
        try {
            entity = new StringEntity(new Gson().toJson(request));
        } catch (UnsupportedEncodingException e) {
            Log.w("tak365", "Json serialize error!");
            e.printStackTrace();
        }
        client.post(context, URL + "/" + method, entity, "application/json", handler);
    }
}
