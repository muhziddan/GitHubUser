package com.ziddan.mygithubuser.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.ziddan.mygithubuser.data.local.DataAccessObject
import com.ziddan.mygithubuser.data.local.FavoriteDatabase
import com.ziddan.mygithubuser.data.local.FavoriteUser

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private var favoriteDao: DataAccessObject?
    private var favoriteDatabase: FavoriteDatabase?

    init {
        favoriteDatabase = FavoriteDatabase.getDatabase(application)
        favoriteDao = favoriteDatabase?.favoriteDao()
    }

    fun getFavorite(): LiveData<List<FavoriteUser>>? {
        return favoriteDao?.getFavoriteUser()
    }
}