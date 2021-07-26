package com.ziddan.mygithubuser.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Client {

    private const val MAIN_URL = "https://api.github.com/"

    val builder = Retrofit.Builder()
        .baseUrl(MAIN_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiInstance = builder.create(GitHubApi::class.java)
}