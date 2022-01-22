package uz.adkhamjon.promovie.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import uz.adkhamjon.promovie.network.ApiService

import uz.adkhamjon.promovie.viewmodels.MovieViewModel
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val apiService: ApiService
    ) {
    suspend fun getDetails(id:Int)= flow { emit(apiService.getMovieDetails(movie_id = id)) }.flowOn(Dispatchers.IO)
    suspend fun getImages(id:Int)= flow { emit(apiService.getMovieImages(movie_id = id)) }.flowOn(Dispatchers.IO)
    suspend fun getSimilar(id:Int)= flow { emit(apiService.getSimilar(movie_id = id)) }.flowOn(Dispatchers.IO)
    suspend fun search(str:String)= flow { emit(apiService.getSearchMovies(query = str)) }.flowOn(Dispatchers.IO)


}
