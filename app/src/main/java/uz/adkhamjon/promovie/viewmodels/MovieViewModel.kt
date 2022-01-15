package uz.adkhamjon.promovie.viewmodels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn

import uz.adkhamjon.promovie.network.ApiService
import uz.adkhamjon.promovie.pagination.PopularPagination
import javax.inject.Inject

class MovieViewModel @Inject constructor(val apiService: ApiService, val name:String) : ViewModel() {
    val popular = Pager(PagingConfig(499)) {
        PopularPagination(apiService)
    }.flow.cachedIn(viewModelScope)
//    val topRated = Pager(PagingConfig(499)) {
//        TopRatedPagination(apiService)
//    }.flow.cachedIn(viewModelScope)
//    val upcoming = Pager(PagingConfig(499)) {
//        UpcomingPagination(apiService)
//    }.flow.cachedIn(viewModelScope)
}