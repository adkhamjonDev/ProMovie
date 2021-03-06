package uz.adkhamjon.promovie.viewmodels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import uz.adkhamjon.promovie.models.Credits.CreditsModel
import uz.adkhamjon.promovie.models.Details.MovieDetails
import uz.adkhamjon.promovie.models.Images.ImageModel
import uz.adkhamjon.promovie.models.MainClass
import uz.adkhamjon.promovie.models.Similar.SimilarClass
import uz.adkhamjon.promovie.models.Video.VideoModel

import uz.adkhamjon.promovie.network.ApiService
import uz.adkhamjon.promovie.pagination.NowPlayingPagination
import uz.adkhamjon.promovie.pagination.PopularPagination
import uz.adkhamjon.promovie.pagination.TopRatedPagination
import uz.adkhamjon.promovie.pagination.UpcomingPagination
import uz.adkhamjon.promovie.repository.MovieRepository
import uz.adkhamjon.promovie.utils.Resource
import javax.inject.Inject

class MovieViewModel @Inject constructor(
    val apiService: ApiService,
    private val movieRepository: MovieRepository
    ) : ViewModel() {
    val popular = Pager(PagingConfig(499)) {
        PopularPagination(apiService)
    }.flow.cachedIn(viewModelScope)
    val topRated = Pager(PagingConfig(499)) {
        TopRatedPagination(apiService)
    }.flow.cachedIn(viewModelScope)
    val upcoming = Pager(PagingConfig(499)) {
        UpcomingPagination(apiService)
    }.flow.cachedIn(viewModelScope)
    val nowPlaying = Pager(PagingConfig(499)) {
        NowPlayingPagination(apiService)
    }.flow.cachedIn(viewModelScope)

    private val movieDetailsLiveData=MutableLiveData<Resource<MovieDetails>>()
    private val movieImagesLiveData=MutableLiveData<Resource<ImageModel>>()
    private val movieSimilarLiveData=MutableLiveData<Resource<SimilarClass>>()
    private val movieSearchLiveData=MutableLiveData<Resource<MainClass>>()
    private val movieVideoLiveData=MutableLiveData<Resource<VideoModel>>()
    private val movieCreditLiveData=MutableLiveData<Resource<CreditsModel>>()

    fun getDetails(id:Int):MutableLiveData<Resource<MovieDetails>>{
        viewModelScope.launch {
            movieDetailsLiveData.postValue(Resource.loading(null))
            movieRepository.getDetails(id)
                    .catch {e->
                movieDetailsLiveData.postValue(Resource.error(e.message?:"Error",null))
            }.collect {
                movieDetailsLiveData.postValue(Resource.success(it))
            }
        }
        return movieDetailsLiveData
    }
    //---------------------------------------------------------------------------
    fun getImages(id:Int):MutableLiveData<Resource<ImageModel>>{
        viewModelScope.launch {
            movieImagesLiveData.postValue(Resource.loading(null))
            movieRepository.getImages(id)
                .catch {e->
                    movieImagesLiveData.postValue(Resource.error(e.message?:"Error",null))
                }.collect {
                    movieImagesLiveData.postValue(Resource.success(it))
                }
        }
        return movieImagesLiveData
    }
    //----------------------------------------------------------------------------
    fun getSimilar(id:Int):MutableLiveData<Resource<SimilarClass>>{
        viewModelScope.launch {
            movieSimilarLiveData.postValue(Resource.loading(null))
            movieRepository.getSimilar(id)
                .catch {e->
                    movieSimilarLiveData.postValue(Resource.error(e.message?:"Error",null))
                }.collect {
                    movieSimilarLiveData.postValue(Resource.success(it))
                }
        }
        return movieSimilarLiveData
    }
    //-------------------------------------------------------------
    fun getSearch(str:String):MutableLiveData<Resource<MainClass>>{
        viewModelScope.launch {
            movieSearchLiveData.postValue(Resource.loading(null))
            movieRepository.search(str)
                .catch {e->
                    movieSearchLiveData.postValue(Resource.error(e.message?:"Error",null))
                }.collect {
                    movieSearchLiveData.postValue(Resource.success(it))
                }
        }
        return movieSearchLiveData
    }

    //-------------------------------------------------------------
    fun getVideo(id: Int):MutableLiveData<Resource<VideoModel>>{
        viewModelScope.launch {
            movieVideoLiveData.postValue(Resource.loading(null))
            movieRepository.getVideos(id)
                .catch {e->
                    movieVideoLiveData.postValue(Resource.error(e.message?:"Error",null))
                }.collect {
                    movieVideoLiveData.postValue(Resource.success(it))
                }
        }
        return movieVideoLiveData
    }

    //-------------------------------------------------------------
    fun getCredits(id: Int):MutableLiveData<Resource<CreditsModel>>{
        viewModelScope.launch {
            movieCreditLiveData.postValue(Resource.loading(null))
            movieRepository.getCredits(id)
                .catch {e->
                    movieCreditLiveData.postValue(Resource.error(e.message?:"Error",null))
                }.collect {
                    movieCreditLiveData.postValue(Resource.success(it))
                }
        }
        return movieCreditLiveData
    }
}