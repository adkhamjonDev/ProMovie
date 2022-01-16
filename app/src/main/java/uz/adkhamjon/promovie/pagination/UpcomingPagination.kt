package uz.adkhamjon.promovie.pagination
import androidx.paging.PagingSource
import androidx.paging.PagingState
import uz.adkhamjon.promovie.models.MovieClass
import uz.adkhamjon.promovie.network.ApiService

import java.lang.Exception
import javax.inject.Inject

class UpcomingPagination @Inject constructor(val apiService: ApiService): PagingSource<Int, MovieClass>() {
    override fun getRefreshKey(state: PagingState<Int, MovieClass>): Int? {
        return state.anchorPosition
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieClass> {
        try {
            val nextPageNumber = params.key ?: 1
            val movie = apiService.getUpcoming(page = nextPageNumber)
            if (nextPageNumber > 1) {
                return LoadResult.Page(movie.results, nextPageNumber - 1, nextPageNumber + 1)
            } else {
                return LoadResult.Page(movie.results, null, nextPageNumber + 1)
            }
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }


}