package com.ziddan.consumerapp

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private var favoriteList = MutableLiveData<ArrayList<UserItem>>()

    fun setFavorite(context: Context) {
        val cursor = context.contentResolver.query(
            DatabaseContract.FavoriteColumns.CONTENT_URI, null, null, null, null
        )
        val converted = MappingHelper.mapCursorToArrayList(cursor)
        favoriteList.postValue(converted)
    }

    fun getFavorite(): LiveData<ArrayList<UserItem>>? {
        return favoriteList
    }
}