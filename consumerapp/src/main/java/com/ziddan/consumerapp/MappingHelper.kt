package com.ziddan.consumerapp

import android.database.Cursor

object MappingHelper {

    fun mapCursorToArrayList(notesCursor: Cursor?): ArrayList<UserItem> {
        val favoriteList = ArrayList<UserItem>()

        notesCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.ID))
                val username =
                    getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.USERNAME))
                val avatar =
                    getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.AVATAR))
                favoriteList.add(
                    UserItem(
                        username,
                        id,
                        avatar
                    )
                )
            }
        }
        return favoriteList
    }
}