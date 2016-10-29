/**
 * Created by divere on 30.09.16.
 */

package org.take365.Engine.Network;

import org.take365.Engine.Network.Models.Request.LoginWithTokenRequest;
import org.take365.Engine.Network.Models.Response.LoginResponse.LoginResponse;
import org.take365.Engine.Network.Models.Response.StoryResponse.StoryDetailResponse;
import org.take365.Engine.Network.Models.Response.StoryResponse.StoryListResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Take365Service {

    @FormUrlEncoded
    @POST("auth/login")
    Call<LoginResponse> login(@Field("username") String username, @Field("password") String password);

    @POST("auth/reuse-token")
    Call<LoginResponse> loginWithToken(@Body LoginWithTokenRequest request);

    @GET("story/list")
    Call<StoryListResponse> getStoriesList(@Query("username") String username);

    @GET("story/{id}")
    Call<StoryDetailResponse> getStoryDetails(@Path("id") int storyId);
}
