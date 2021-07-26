package com.ziddan.mygithubuser.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ziddan.mygithubuser.api.Client
import com.ziddan.mygithubuser.data.general.UserItem
import com.ziddan.mygithubuser.data.general.UserList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val listUsers = MutableLiveData<ArrayList<UserItem>>()

    fun setUsers(query: String) {
        Client.apiInstance.getSearhedUser(query).enqueue(object : Callback<UserList> {
            override fun onResponse(call: Call<UserList>, response: Response<UserList>) {
                if (response.isSuccessful) {
                    listUsers.postValue(response.body()?.items)
                }
            }

            override fun onFailure(call: Call<UserList>, t: Throwable) {
                Log.d("onFailure", t.message.toString())
            }

        })
    }

    fun getUsers(): LiveData<ArrayList<UserItem>> {
        return listUsers
    }
}