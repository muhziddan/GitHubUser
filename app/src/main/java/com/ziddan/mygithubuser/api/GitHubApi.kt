package com.ziddan.mygithubuser.api

import com.ziddan.mygithubuser.BuildConfig
import com.ziddan.mygithubuser.data.general.UserDetailItem
import com.ziddan.mygithubuser.data.general.UserItem
import com.ziddan.mygithubuser.data.general.UserList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApi {

    @GET("search/users")
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    fun getSearhedUser(@Query("q") query: String): Call<UserList>

    @GET("users/{username}")
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    fun getUserDetail(@Path("username") username: String): Call<UserDetailItem>

    @GET("users/{username}/followers")
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    fun getUserFollowers(@Path("username") username: String): Call<ArrayList<UserItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    fun getUserFollowing(@Path("username") username: String): Call<ArrayList<UserItem>>
}