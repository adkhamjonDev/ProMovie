package uz.adkhamjon.promovie.network

import uz.adkhamjon.promovie.models.MainClass
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("3/movie/popular")
    suspend fun getPopular(
        @Query("api_key") api_key:String="44f66b1676556437f4731985995f2dea",
        @Query("language") category: String="en-US",
        @Query("page") page: Int
    ): MainClass
}