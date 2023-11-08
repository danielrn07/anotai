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

        configureThemeFromPreferences()

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        installSplashScreen()
        setContentView(binding.root)

        setupSwitch()
    }

    private fun configureThemeFromPreferences() {
        sharedPref = getSharedPreferences("checkTheme", Context.MODE_PRIVATE)
        val isDarkMode = sharedPref.getBoolean("checked", false)
        setAppTheme(isDarkMode)
        setTheme(if (isDarkMode) R.style.Theme_Anotai_Dark else R.style.Theme_Anotai)
    }


    private fun setupSwitch() {
        val switch: SwitchCompat = binding.btnNightMode
        switch.isChecked = sharedPref.getBoolean("checked", false)

        switch.setOnCheckedChangeListener { _, isChecked ->
            with(sharedPref.edit()) {
                putBoolean("checked", isChecked)
                apply()
            }
            setAppTheme(isChecked)
        }
    }

    private fun setAppTheme(isDarkMode: Boolean) {
        val newThemeMode =
            if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        AppCompatDelegate.setDefaultNightMode(newThemeMode)
    }
}