package com.ziddan.mygithubuser.ui.detail.tab

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ziddan.mygithubuser.api.Client
import com.ziddan.mygithubuser.data.general.UserItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel : ViewModel() {

    private val followers = MutableLiveData<ArrayList<UserItem>>()

    fun setUserFollowers(list: String) {
        Client.apiInstance.getUserFollowers(list).enqueue(object : Callback<ArrayList<UserItem>> {
            override fun onResponse(
                call: Call<ArrayList<UserItem>>,
                response: Response<ArrayList<UserItem>>
            ) {
                if (response.isSuccessful) {
                    followers.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<ArrayList<UserItem>>, t: Throwable) {
                Log.d("onFailure", t.message.toString())
            }

        })
    }

    fun getUserFollowers(): LiveData<ArrayList<UserItem>> {
        return followers
    }
}