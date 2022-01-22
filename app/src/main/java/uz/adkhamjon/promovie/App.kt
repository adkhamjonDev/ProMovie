package uz.adkhamjon.promovie

import android.app.Application
import com.droidnet.DroidNet
import com.yariksoffice.lingver.Lingver
import com.yariksoffice.lingver.store.PreferenceLocaleStore
import uz.adkhamjon.promovie.di.component.AppComponent
import uz.adkhamjon.promovie.di.component.DaggerAppComponent
import java.util.*

class App: Application() {
    companion object{
        lateinit var appComponent: AppComponent
        const val LANGUAGE_ENGLISH = "en"
        const val LANGUAGE_ENGLISH_COUNTRY = "US"
        const val LANGUAGE_RUSSIAN = "ru"
        const val LANGUAGE_RUSSIAN_COUNTRY = "RU"
    }
    override fun onCreate() {
        super.onCreate()
        appComponent= DaggerAppComponent.builder()
            .application(this)
            .build()!!
        DroidNet.init(this)
        val store = PreferenceLocaleStore(this, Locale(LANGUAGE_ENGLISH))
        val lingver = Lingver.init(this, store)
    }
    override fun onLowMemory() {
        super.onLowMemory()
        DroidNet.getInstance().removeAllInternetConnectivityChangeListeners()
    }
}