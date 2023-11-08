package com.danielnascimento.anotai.presentation.activities
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.danielnascimento.anotai.R
import com.danielnascimento.anotai.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Anotai)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        installSplashScreen()
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        sharedPref = getSharedPreferences(
            "checkTheme", Context.MODE_PRIVATE
        )

        checkTheme()
        recoverTheme()
    }

    private fun checkTheme() {
        val switch: SwitchCompat = binding.btnNightMode
        switch.setOnCheckedChangeListener { _, isChecked ->
            with(sharedPref.edit()) {
                putBoolean("checked", isChecked)
                apply()
            }
            setAppTheme(isChecked)
        }
    }

    private fun recoverTheme() {
        val checked = sharedPref.getBoolean("checked", false)
        if (checked) {
            binding.btnNightMode.isChecked = true
        }
    }

    private fun setAppTheme(isDarkMode: Boolean) {
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}