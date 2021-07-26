package com.ziddan.consumerapp

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {

    const val AUTHORITY = "com.ziddan.mygithubuser"
    const val SCHEME = "content"

    class FavoriteColumns : BaseColumns {

        companion object {
            const val TABLE_NAME = "favorite_user"
            const val ID = "id"
            const val USERNAME = "login"
            const val AVATAR = "avatar_url"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}