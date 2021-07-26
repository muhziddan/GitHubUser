package com.ziddan.mygithubuser.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ziddan.mygithubuser.api.Client
import com.ziddan.mygithubuser.data.general.UserDetailItem
import com.ziddan.mygithubuser.data.local.DataAccessObject
import com.ziddan.mygithubuser.data.local.FavoriteDatabase
import com.ziddan.mygithubuser.data.local.FavoriteUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val detailUser = MutableLiveData<UserDetailItem>()
    private var favoriteDao: DataAccessObject?
    private var favoriteDatabase: FavoriteDatabase?

    init {
        favoriteDatabase = FavoriteDatabase.getDatabase(application)
        favoriteDao = favoriteDatabase?.favoriteDao()
    }

    fun setUserDetail(username: String) {
        Client.apiInstance.getUserDetail(username).enqueue(object : Callback<UserDetailItem> {
            override fun onResponse(
                call: Call<UserDetailItem>,
                response: Response<UserDetailItem>
            ) {
                if (response.isSuccessful) {
                    detailUser.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<UserDetailItem>, t: Throwable) {
                Log.d("onFailure", t.message.toString())
            }
        })
    }

    fun getUserDetail(): LiveData<UserDetailItem> {
        return detailUser
    }

    fun addFavorite(username: String, id: Int, avatar: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val favoriteUser = FavoriteUser(
                username,
                id,
                avatar
            )
            favoriteDao?.addFavoriteUser(favoriteUser)
        }
    }

    suspend fun checkFavorite(id: Int) = favoriteDao?.checkFavoriteUser(id)

    fun removeFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            favoriteDao?.removeFavoriteUser(id)
        }
    }
}