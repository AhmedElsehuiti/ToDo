package com.example.todo.home.activity


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate

import com.example.todo.R
import com.example.todo.home.HomeActivity
import com.example.todo.home.fragments.TodoListFragment

class SettingActivity : AppCompatActivity() {
    private val sharedPreferencesKey = "dark_mode_preference"
    private lateinit var sharedPreferences: SharedPreferences
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        val btn = findViewById<Switch>(R.id.switch1)

        // Initialize SharedPreferences
        sharedPreferences = getPreferences(Context.MODE_PRIVATE)

        // Restore the saved state
        btn.isChecked = sharedPreferences.getBoolean(sharedPreferencesKey, false)

        // Set the switch to listen on checked change
        handleCheckBox(btn)
    }

    private fun handleCheckBox(@SuppressLint("UseSwitchCompatOrMaterialCode") btn: Switch) {
        btn.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

            // Save the state in SharedPreferences
            saveSwitchState(isChecked)
        }
    }

    private fun saveSwitchState(isChecked: Boolean) {
        with(sharedPreferences.edit()) {
            putBoolean(sharedPreferencesKey, isChecked)
            apply()
        }


    }
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}