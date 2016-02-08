package org.take365.take365.Engine.Network;

import android.content.Context;
import android.util.Log;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import org.take365.take365.Engine.Network.Models.Request.LoginRequest;
import org.take365.take365.Engine.Network.Models.Response.BaseResponse;
import org.take365.take365.Engine.Network.Models.Response.LoginResponse.LoginResponse;
import org.take365.take365.Engine.Network.Models.StoryModel;
import org.take365.take365.Engine.Network.Models.StoryPrivateLevel;
import org.take365.take365.Take365Application;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by evgeniy on 08.02.16.
 */
public class ApiManager {

    private static final String URL = "http://new.take365.org";
    private static AsyncHttpClient client = new AsyncHttpClient();
    private static ApiManager instance = null;
    private static Context context;

    public ApiEvents Events;

    public String AccessToken;
    public ArrayList<StoryModel> Stories;

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

        post("api/auth/login", request, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String json = new String(responseBody);
                LoginResponse response = new Gson().fromJson(json, LoginResponse.class);
                if (Events != null && response.result != null) {
                    Events.loginCompleted(response.result, null);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                String json = new String(responseBody);
                BaseResponse response = new Gson().fromJson(json, BaseResponse.class);
                if(Events != null){
                    Events.loginCompleted(null, response.errors[0].value);
                }
            }
        });
    }

    public void getStory(int storyId) {

    }

    public void getStoryList() {

    }

    public void createStory(String title, StoryPrivateLevel privateLevel, String description) {

    }

    public void uploadImage(byte[] image, int storyId, String date) {

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
