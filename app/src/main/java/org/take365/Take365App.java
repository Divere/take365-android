package org.take365;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import org.take365.network.models.responses.login.LoginResult;
import org.take365.network.Take365Service;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by evgeniy on 08.02.16.
 */


public class Take365App extends Application {

    private static Take365App application;

    private static final String API_URI = "http://take365.vasa.tech/api/";

    private static Take365Service service;
    private static LoginResult currentUser;

    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
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

    public static void setAccessToken(final String token)
    {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                return chain.proceed(original.newBuilder().header("Authorization", "Bearer " + token).build());
            }
        });

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);

        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(API_URI).addConverterFactory(GsonConverterFactory.create());
        service = builder.client(httpClient.build()).build().create(Take365Service.class);

    }

    public static void clearAccessToken() {
        service = null;
    }

    public static void setCurrentUser(final LoginResult result)
    {
        currentUser = result;
        setAccessToken(currentUser.getToken());
    }

    public static LoginResult getCurrentUser()
    {
        return currentUser;
    }
}