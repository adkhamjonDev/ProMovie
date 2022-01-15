package uz.adkhamjon.promovie

import android.os.Bundle
import android.view.Gravity
import android.view.View
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import uz.adkhamjon.promovie.databinding.ActivityMainBinding
import com.vimalcvs.switchdn.DayNightSwitchListener
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import uz.adkhamjon.promovie.utils.SharedPreferenceTheme


class MainActivity : AppCompatActivity() {
    private lateinit var sharedPreferenceTheme: SharedPreferenceTheme
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPreferenceTheme= SharedPreferenceTheme.getInstance(this)
        super.onCreate(savedInstanceState)
        if(sharedPreferenceTheme.hasDark) {
            AppCompatDelegate
                .setDefaultNightMode(
                    AppCompatDelegate
                        .MODE_NIGHT_YES
                )
        }
        window.statusBarColor = ContextCompat.getColor(this, R.color.status_bar_color_main)
        isUsingNightModeResources()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home
            ), drawerLayout
        )
        binding.drawerLayout.setViewScale(Gravity.START, 0.85f)
        binding.drawerLayout.setRadius(Gravity.START, 30f)
        binding.drawerLayout.setViewElevation(Gravity.START, 10f)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        binding.appBarMain.gridView.setOnClickListener {
            binding.appBarMain.linearView.visibility= View.VISIBLE
            binding.appBarMain.gridView.visibility= View.GONE

        }
        binding.appBarMain.linearView.setOnClickListener {
            binding.appBarMain.linearView.visibility= View.GONE
            binding.appBarMain.gridView.visibility= View.VISIBLE

        }
        binding.switchItem.setIsNight(sharedPreferenceTheme.hasDark)
        binding.switchItem.setListener { isNight ->
            if (isNight) {
                AppCompatDelegate
                    .setDefaultNightMode(
                        AppCompatDelegate
                            .MODE_NIGHT_YES
                    )
                sharedPreferenceTheme.hasDark=true
            } else {
                AppCompatDelegate
                    .setDefaultNightMode(
                        AppCompatDelegate
                            .MODE_NIGHT_NO
                    )
                sharedPreferenceTheme.hasDark=false
            }
        }
    }
    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
    private fun isUsingNightModeResources() {
         when (resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES ->sharedPreferenceTheme.hasDark=true
            Configuration.UI_MODE_NIGHT_NO -> sharedPreferenceTheme.hasDark=false
            Configuration.UI_MODE_NIGHT_UNDEFINED -> sharedPreferenceTheme.hasDark=false

        }
    }
}