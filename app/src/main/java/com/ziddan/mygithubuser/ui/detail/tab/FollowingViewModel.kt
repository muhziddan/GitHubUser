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

class FollowingViewModel : ViewModel() {

    private val following = MutableLiveData<ArrayList<UserItem>>()

    fun setUserFollowing(list: String) {
        Client.apiInstance.getUserFollowing(list).enqueue(object : Callback<ArrayList<UserItem>> {
            override fun onResponse(
                call: Call<ArrayList<UserItem>>,
                response: Response<ArrayList<UserItem>>
            ) {
                if (response.isSuccessful) {
                    following.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<ArrayList<UserItem>>, t: Throwable) {
                Log.d("onFailure", t.message.toString())
            }

        })
    }

    fun getUserFollowing(): LiveData<ArrayList<UserItem>> {
        return following
    }
}