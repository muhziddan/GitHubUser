package com.ziddan.mygithubuser.ui.settings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import android.widget.Toast
import com.ziddan.mygithubuser.data.general.ReminderModel
import com.ziddan.mygithubuser.databinding.ActivitySettingsBinding
import com.ziddan.mygithubuser.preference.ReminderPrf
import com.ziddan.mygithubuser.receiver.ReminderReceiver
import com.ziddan.mygithubuser.ui.main.MainActivity

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var reminder: ReminderModel
    private lateinit var reminderReceiver: ReminderReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvLanguangeSettings.setOnClickListener {
            val langIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(langIntent)
        }

        val reminderPrf = ReminderPrf(this)
        if (reminderPrf.getReminder().isReminder) {
            binding.switchReminderSettings.isChecked = true
        } else {
            binding.switchReminderSettings.isChecked = false
        }

        reminderReceiver = ReminderReceiver()

        binding.switchReminderSettings.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                onReminderSet(true)
                reminderReceiver.setRepeatingReminder(
                    this,
                    "GitHub User Reminder",
                    "09:00",
                    "Let's find popular user on GitHub User!"
                )
                Toast.makeText(this, "Repeating Reminder set up", Toast.LENGTH_SHORT).show()
            } else {
                onReminderSet(false)
                reminderReceiver.cancelReminder(this)
            }
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun onReminderSet(state: Boolean) {
        val reminderPrf = ReminderPrf(this)
        reminder = ReminderModel()

        reminder.isReminder = state
        reminderPrf.setReminder(reminder)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        return super.onOptionsItemSelected(item)
    }
}