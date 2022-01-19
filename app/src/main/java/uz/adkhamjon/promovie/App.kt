package uz.adkhamjon.promovie

import android.app.Application
import com.droidnet.DroidNet
import uz.adkhamjon.promovie.di.component.AppComponent
import uz.adkhamjon.promovie.di.component.DaggerAppComponent

class App: Application() {
    companion object{
        lateinit var appComponent: AppComponent
    }
    override fun onCreate() {
        super.onCreate()
        appComponent= DaggerAppComponent.builder()
            .application(this)
            .build()!!
        DroidNet.init(this)
    }
    override fun onLowMemory() {
        super.onLowMemory()
        DroidNet.getInstance().removeAllInternetConnectivityChangeListeners()
    }
}