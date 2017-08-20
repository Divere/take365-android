/**
 * Created by divere on 30.09.16.
 */

package org.take365.Network

import org.take365.Network.Models.Response.BaseResponse
import org.take365.Network.Models.Response.LoginResponse.LoginResponse
import org.take365.Network.Models.Response.StoryResponse.GetStoryDetailsResponse
import org.take365.Network.Models.Response.StoryResponse.GetStoryListResponse

import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.take365.Network.Models.Response.FeedResponse.GetFeedResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface Take365Service {

    @FormUrlEncoded
    @POST("user/register")
    fun register(@Field("username") username: String, @Field("email") email: String, @Field("password") password: String): Call<BaseResponse>

    @FormUrlEncoded
    @POST("auth/login")
    fun login(@Field("username") username: String, @Field("password") password: String): Call<LoginResponse>

    @POST("auth/reuse-token")
    @FormUrlEncoded
    fun loginWithToken(@Field("accessToken") accessToken: String): Call<LoginResponse>

    @GET("story/list")
    fun getStoriesList(@Query("username") username: String, @Query("maxItems") maxItems: Int): Call<GetStoryListResponse>

    @GET("story/{id}")
    fun getStoryDetails(@Path("id") storyId: Int): Call<GetStoryDetailsResponse>

    @FormUrlEncoded
    @POST("story/write")
    fun createStory(@Field("startDate") startDate: String, @Field("status") status: Int, @Field("title") title: String, @Field("description") description: String): Call<BaseResponse>

    @Multipart
    @POST("media/upload")
    fun uploadPhoto(
            @Part("name") imageName: RequestBody,
            @Part("targetId") storyId: RequestBody,
            @Part("targetType") targetType: RequestBody,
            @Part("mediaType") mediaType: RequestBody,
            @Part("date") date: RequestBody,
            @Part file: MultipartBody.Part
    ): Call<BaseResponse>

    @GET("feed/feed")
    fun getFeed(@Query("page") page: Int, @Query("maxItems") maxItems: Int) : Call<GetFeedResponse>
}
