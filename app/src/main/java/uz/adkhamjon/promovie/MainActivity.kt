package uz.adkhamjon.promovie
import android.app.AlertDialog
import android.app.FragmentTransaction
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.RequiresApi
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import uz.adkhamjon.promovie.databinding.ActivityMainBinding
import androidx.lifecycle.ViewModelProviders
import com.droidnet.DroidListener
import uz.adkhamjon.promovie.databinding.TypeDialogBinding
import uz.adkhamjon.promovie.viewmodels.TypeViewModel
import com.droidnet.DroidNet
import uz.adkhamjon.promovie.ui.home.HomeFragment
import uz.adkhamjon.promovie.viewmodels.InternetViewModel


class MainActivity : AppCompatActivity(), DroidListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var typeViewModel: TypeViewModel
    private lateinit var internetViewModel: InternetViewModel
    private lateinit var mDroidNet: DroidNet
    private var internet=false
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        //----------------------------------------------------
        super.onCreate(savedInstanceState)
        //window.statusBarColor = ContextCompat.getColor(this, R.color.status_bar_color_main)

        typeViewModel=ViewModelProviders.of(this)[TypeViewModel::class.java]
        internetViewModel=ViewModelProviders.of(this)[InternetViewModel::class.java]
        //--------------------------------------------------------
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)
        mDroidNet = DroidNet.getInstance()
        mDroidNet.addInternetConnectivityListener(this)
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


        binding.appBarMain.retry.setOnClickListener {
            if(internet){
                binding.appBarMain.internetLayout.visibility=View.GONE
                binding.appBarMain.viewF.visibility=View.VISIBLE
                internetViewModel.setInternet("Connected")
            }
            else{
                internetViewModel.setInternet("Disconnected")
            }


        }
        binding.appBarMain.grid2View.setOnClickListener {
            binding.appBarMain.linearView.visibility= View.GONE
            binding.appBarMain.grid2View.visibility= View.GONE
            binding.appBarMain.grid3View.visibility= View.VISIBLE
            typeViewModel.setGridType("2")

        }
        binding.appBarMain.grid3View.setOnClickListener {
            binding.appBarMain.linearView.visibility= View.VISIBLE
            binding.appBarMain.grid2View.visibility= View.GONE
            binding.appBarMain.grid3View.visibility= View.GONE
            typeViewModel.setGridType("3")

        }
        binding.appBarMain.linearView.setOnClickListener {
            binding.appBarMain.linearView.visibility= View.GONE
            binding.appBarMain.grid2View.visibility= View.VISIBLE
            binding.appBarMain.grid3View.visibility= View.GONE
            typeViewModel.setGridType("1")

        }
        binding.appBarMain.showDialog.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val binding1= TypeDialogBinding.inflate(layoutInflater, null, false)
            builder.setView(binding1.root)
            val alertDialog=builder.create()
            binding1.popular.setOnClickListener {
                typeViewModel.setDialogType("Popular")
                binding.appBarMain.tittle.text="Popular"
                alertDialog.dismiss()
            }
            binding1.top.setOnClickListener {
                typeViewModel.setDialogType("Top Rated")
                binding.appBarMain.tittle.text="Top Rated"
                alertDialog.dismiss()
            }
            binding1.upcoming.setOnClickListener {
                typeViewModel.setDialogType("Upcoming")
                binding.appBarMain.tittle.text="Upcoming"
                alertDialog.dismiss()
            }
            binding1.nowPlaying.setOnClickListener {
                typeViewModel.setDialogType("Now Playing")
                binding.appBarMain.tittle.text="Now Playing"
                alertDialog.dismiss()
            }
            alertDialog.show()

        }
        binding.appBarMain.search.setOnClickListener {
            navController.navigate(R.id.searchFragment)
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
    fun hideToolbar(){
        binding.appBarMain.toolbar.visibility=View.GONE
    }
    fun showToolbar(){
        binding.appBarMain.toolbar.visibility=View.VISIBLE
    }
    override fun onInternetConnectivityChanged(isConnected: Boolean) {
        if(!isConnected){
            internet=false
            binding.appBarMain.internetLayout.visibility=View.VISIBLE
            binding.appBarMain.viewF.visibility=View.GONE
        }
        else internet=true
    }
}