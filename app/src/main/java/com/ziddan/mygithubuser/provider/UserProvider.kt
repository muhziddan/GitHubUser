package com.ziddan.mygithubuser.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.ziddan.mygithubuser.data.local.DataAccessObject
import com.ziddan.mygithubuser.data.local.FavoriteDatabase

class UserProvider : ContentProvider() {

    private lateinit var favoriteDao: DataAccessObject

    companion object {
        const val AUTHORITY = "com.ziddan.mygithubuser"
        const val TABLE_NAME = "favorite_user"

        private const val FAVORITE_ID = 1
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            sUriMatcher.addURI(AUTHORITY, TABLE_NAME, FAVORITE_ID)
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun onCreate(): Boolean {
        favoriteDao = context?.let { FavoriteDatabase.getDatabase(it)?.favoriteDao() }!!
        return false
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        var cursor: Cursor?
        when (sUriMatcher.match(uri)) {
            FAVORITE_ID -> {
                cursor = favoriteDao.findAllFavoriteUser()
                if (context != null) {
                    cursor.setNotificationUri(context?.contentResolver, uri)
                }
            }
            else -> {
                cursor = null
            }
        }
        return cursor
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }
}