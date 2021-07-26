package com.ziddan.mygithubuser.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [FavoriteUser::class],
    version = 1
)
abstract class FavoriteDatabase : RoomDatabase() {

    companion object {
        var DATABASE: FavoriteDatabase? = null

        fun getDatabase(context: Context): FavoriteDatabase? {
            if (DATABASE == null) {
                synchronized(FavoriteDatabase::class) {
                    DATABASE = Room.databaseBuilder(
                        context.applicationContext,
                        FavoriteDatabase::class.java,
                        "favorite database"
                    ).build()
                }
            }
            return DATABASE
        }
    }

    abstract fun favoriteDao(): DataAccessObject
}