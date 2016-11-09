/**
 * Created by divere on 30.09.16.
 */

package org.take365.Network;

import org.take365.Network.Models.Request.LoginWithTokenRequest;
import org.take365.Network.Models.Response.BaseResponse;
import org.take365.Network.Models.Response.LoginResponse.LoginResponse;
import org.take365.Network.Models.Response.StoryResponse.StoryDetailResponse;
import org.take365.Network.Models.Response.StoryResponse.StoryListResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Take365Service {

    @FormUrlEncoded
    @POST("user/register")
    Call<BaseResponse> register(@Field("username") String username, @Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("auth/login")
    Call<LoginResponse> login(@Field("username") String username, @Field("password") String password);

    @POST("auth/reuse-token")
    Call<LoginResponse> loginWithToken(@Body LoginWithTokenRequest request);

    @GET("story/list")
    Call<StoryListResponse> getStoriesList(@Query("username") String username, @Query("maxItems") int maxItems);

    @GET("story/{id}")
    Call<StoryDetailResponse> getStoryDetails(@Path("id") int storyId);

    @FormUrlEncoded
    @POST("story/write")
    Call<BaseResponse> createStory(@Field("startDate") String startDate, @Field("status") int status, @Field("title") String title, @Field("description") String description);

    @Multipart
    @POST("media/upload")
    Call<BaseResponse> uploadPhoto(
            @Part("name") RequestBody imageName,
            @Part("targetId") RequestBody storyId,
            @Part("targetType") RequestBody targetType,
            @Part("mediaType") RequestBody mediaType,
            @Part("date") RequestBody date,
            @Part MultipartBody.Part file
    );
}
