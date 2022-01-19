package uz.adkhamjon.promovie.viewmodels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.adkhamjon.promovie.network.ApiService
import uz.adkhamjon.promovie.repository.MovieRepository
import java.lang.IllegalArgumentException
import javax.inject.Inject

class ViewModelFactory @Inject constructor(val apiService: ApiService,val movieRepository: MovieRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(MovieViewModel::class.java)){
            return MovieViewModel(apiService,movieRepository) as T
        }
        throw IllegalArgumentException("Error")
    }


}