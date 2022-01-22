package uz.adkhamjon.promovie.di.component

import dagger.Component
import dagger.Module
import uz.adkhamjon.promovie.di.module.NetworkModule
import uz.adkhamjon.promovie.ui.home.HomeFragment
import javax.inject.Singleton
import android.app.Application

import dagger.BindsInstance
import uz.adkhamjon.promovie.ui.info.InfoFragment
import uz.adkhamjon.promovie.ui.search.SearchFragment


@Singleton
@Component(modules = [NetworkModule::class])
interface AppComponent {

    fun inject(homeFragment: HomeFragment)
    fun inject(infoFragment: InfoFragment)
    fun inject(searchFragment: SearchFragment)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent?

        @BindsInstance
        fun application(application: Application?): Builder
    }
}