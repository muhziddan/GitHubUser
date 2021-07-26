package com.ziddan.mygithubuser.preference

import android.content.Context
import com.ziddan.mygithubuser.data.general.ReminderModel

class ReminderPrf(context: Context) {
    companion object {
        private const val PREFS_NAME = "reminder_pref"
        private const val REMINDER_MU = "isReminder"
    }

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setReminder(value: ReminderModel) {
        val editor = preferences.edit()
        editor.putBoolean(REMINDER_MU, value.isReminder)
        editor.apply()
    }

    fun getReminder(): ReminderModel {
        val model = ReminderModel()
        model.isReminder = preferences.getBoolean(REMINDER_MU, false)
        return model
    }
}