/**
 * Created by divere on 30.09.16.
 */

package org.take365.take365.Engine.Network;

import org.take365.take365.Engine.Network.Models.Response.LoginResponse.LoginResponse;
import org.take365.take365.Engine.Network.Models.Response.StoryResponse.StoryListResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Take365Service {

    @FormUrlEncoded
    @POST("auth/login")
    Call<LoginResponse> login(@Field("username") String username, @Field("password") String password);

    @GET("auth/reuse-token")
    Call<LoginResponse> loginWithToken();

    @GET("story/list")
    Call<StoryListResponse> storyList(@Query("username") String username);
}
