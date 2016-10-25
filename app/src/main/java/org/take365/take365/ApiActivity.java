package org.take365.take365;

import android.support.v7.app.AppCompatActivity;

import org.take365.take365.Engine.Network.Models.Response.LoginResponse.LoginResult;
import org.take365.take365.Engine.Network.Take365Service;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by divere on 25/10/2016.
 */

public class ApiActivity extends AppCompatActivity {

    private static final String API_URI = "https://take365.org/api/";

    private static Take365Service service;
    private static LoginResult currentUser;

    protected Take365Service getApi()
    {
        if(service == null)
        {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(API_URI).addConverterFactory(GsonConverterFactory.create()).build();
            service = retrofit.create(Take365Service.class);
        }

        return service;
    }

    protected Take365Service setCurrentUser(final LoginResult result)
    {
        currentUser = result;
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                return chain.proceed(original.newBuilder().header("Authorization", "Bearer " + result.token).build());
            }
        });

        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(API_URI).addConverterFactory(GsonConverterFactory.create());
        service = builder.client(httpClient.build()).build().create(Take365Service.class);

        return service;
    }

    protected LoginResult getCurrentUser()
    {
        return currentUser;
    }

}
