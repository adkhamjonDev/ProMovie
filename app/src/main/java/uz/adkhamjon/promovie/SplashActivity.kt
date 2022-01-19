package uz.adkhamjon.promovie


import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat

import uz.adkhamjon.promovie.databinding.ActivitySplashBinding
import uz.adkhamjon.promovie.utils.SharedPreferenceTheme

class SplashActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(this, R.color.status_bar_color_splash)
        binding= ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val background: Thread = object : Thread() {
            override fun run() {
                try {
                    sleep(1500)
                    val intent= Intent(applicationContext,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } catch (e: Exception) {
                }
            }
        }
        background.start()

    }
}