package uz.adkhamjon.promovie.di.component

import dagger.Component
import dagger.Module
import uz.adkhamjon.promovie.di.module.NetworkModule
import uz.adkhamjon.promovie.ui.home.HomeFragment
import javax.inject.Singleton
import android.app.Application

import dagger.BindsInstance




@Singleton
@Component(modules = [NetworkModule::class])
interface AppComponent {

    fun inject(homeFragment: HomeFragment)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent?

        @BindsInstance
        fun application(application: Application?): Builder
    }
}