package uz.adkhamjon.promovie.di.module

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.adkhamjon.promovie.network.ApiService
import uz.adkhamjon.promovie.utils.Config
import javax.inject.Singleton

@Module
class NetworkModule() {

    @Singleton
    @Provides
    fun provideBaseUrl():String=Config.BASE_URL

    @Singleton
    @Provides
    fun provideGsonConvertorFactory():GsonConverterFactory= GsonConverterFactory.create()


    @Singleton
    @Provides
    fun provideRetrofit(
        baseUrl:String,
        gsonConverterFactory: GsonConverterFactory
    ):Retrofit{
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit):ApiService=retrofit.create(ApiService::class.java)


}