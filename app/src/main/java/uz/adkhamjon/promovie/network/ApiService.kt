package uz.adkhamjon.promovie.network

import retrofit2.Call
import retrofit2.Response
import uz.adkhamjon.promovie.models.MainClass
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import uz.adkhamjon.promovie.models.Credits.CreditsModel
import uz.adkhamjon.promovie.models.Details.MovieDetails
import uz.adkhamjon.promovie.models.Images.ImageModel
import uz.adkhamjon.promovie.models.Similar.SimilarClass
import uz.adkhamjon.promovie.models.Video.VideoModel
import uz.adkhamjon.promovie.utils.Config

interface ApiService {
    @GET("3/movie/popular")
    suspend fun getPopular(
        @Query("api_key") api_key:String=Config.API_KEY,
        @Query("language") category: String="en-US",
        @Query("page") page: Int
    ): MainClass
    @GET("3/movie/top_rated")
    suspend fun getTopRated(
        @Query("api_key") api_key:String=Config.API_KEY,
        @Query("language") category: String="en-US",
        @Query("page") page: Int
    ): MainClass
    @GET("3/movie/upcoming")
    suspend fun getUpcoming(
        @Query("api_key") api_key:String=Config.API_KEY,
        @Query("language") category: String="en-US",
        @Query("page") page: Int
    ): MainClass
    @GET("3/movie/now_playing")
    suspend fun getNowPlaying(
        @Query("api_key") api_key:String=Config.API_KEY,
        @Query("language") category: String="en-US",
        @Query("page") page: Int
    ): MainClass

    @GET("3/movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movie_id:Int,
        @Query("api_key") api_key:String=Config.API_KEY,
        @Query("language") category: String="en-US",
    ):MovieDetails

    @GET("3/movie/{movie_id}/images")
    suspend fun getMovieImages(
        @Path("movie_id") movie_id:Int,
        @Query("api_key") api_key:String=Config.API_KEY,
    ): ImageModel

    @GET("3/movie/{movie_id}/similar")
    suspend fun getSimilar(
        @Path("movie_id") movie_id:Int,
        @Query("api_key") api_key:String=Config.API_KEY,
        @Query("language") category: String="en-US",
    ): SimilarClass

    @GET("3/search/multi")
    suspend fun getSearchMovies(
        @Query("api_key") api_key:String=Config.API_KEY,
        @Query("language") category: String="en-US",
        @Query("query") query: String,
    ): MainClass

    @GET("3/movie/{movie_id}/videos")
    suspend fun getVideos(
        @Path("movie_id") movie_id:Int,
        @Query("api_key") api_key:String=Config.API_KEY,
        @Query("language") category: String="en-US"
    ): VideoModel

    @GET("3/movie/{movie_id}/credits")
    suspend fun getCredits(
        @Path("movie_id") movie_id:Int,
        @Query("api_key") api_key:String=Config.API_KEY,
        @Query("language") category: String="en-US"
    ): CreditsModel
}