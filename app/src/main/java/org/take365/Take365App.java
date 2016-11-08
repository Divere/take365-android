package org.take365;

import android.app.Application;
import android.content.Context;

import org.take365.Network.Models.Response.LoginResponse.LoginResult;
import org.take365.Network.Take365Service;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by evgeniy on 08.02.16.
 */


public class Take365App extends Application {

    private static Take365App application;

    private static final String API_URI = "https://take365.org/api/";

    private static Take365Service service;
    private static LoginResult currentUser;

    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static Context getAppContext() {
        return application.getApplicationContext();
    }

    public static Take365Service getApi()
    {
        if(service == null)
        {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(API_URI).addConverterFactory(GsonConverterFactory.create()).build();
            service = retrofit.create(Take365Service.class);
        }

        return service;
    }

    public static Take365Service setAccessToken(final String token)
    {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                return chain.proceed(original.newBuilder().header("Authorization", "Bearer " + token).build());
            }
        });

//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//        httpClient.addInterceptor(logging);

        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(API_URI).addConverterFactory(GsonConverterFactory.create());
        service = builder.client(httpClient.build()).build().create(Take365Service.class);

        return service;
    }

    public static void clearAccessToken() {
        service = null;
    }

    public static Take365Service setCurrentUser(final LoginResult result)
    {
        currentUser = result;
        setAccessToken(currentUser.token);
        return service;
    }

    public static LoginResult getCurrentUser()
    {
        return currentUser;
    }
}