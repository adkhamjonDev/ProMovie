package uz.adkhamjon.promovie.ui.info

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.youtubeapi.utils.Status
import kotlinx.coroutines.*
import okhttp3.Dispatcher
import uz.adkhamjon.promovie.App
import uz.adkhamjon.promovie.MainActivity
import uz.adkhamjon.promovie.adapters.CompanyItemAdapter
import uz.adkhamjon.promovie.databinding.FragmentInfoBinding
import uz.adkhamjon.promovie.utils.Config
import uz.adkhamjon.promovie.viewmodels.MovieViewModel
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class InfoFragment : Fragment() {
    private lateinit var binding:FragmentInfoBinding
    @Inject
    lateinit var movieViewModel: MovieViewModel
    private var movieID=0
    private lateinit var companyItemAdapter: CompanyItemAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        App.appComponent.inject(this)
        (activity as MainActivity?)?.hideToolbar()
        binding=FragmentInfoBinding.inflate(inflater, container, false)
        if(arguments!=null){
            movieID=arguments?.getInt("id") as Int
        }
        CoroutineScope(Dispatchers.Main).launch {
            movieViewModel.getDetails(movieID).observe(viewLifecycleOwner, {
                when (it.status) {
                    Status.ERROR -> {

                    }
                    Status.LOADING -> {

                    }
                    Status.SUCCESS -> {
                        val details = it.data
                        binding.tittle.text=details?.title
                        binding.name.text=details?.title
                        Glide.with(requireContext()).load(Config.IMAGE_BASE_URL+details?.poster_path)
                            .into(binding.icon)
                        val rate=details?.vote_average as Double
                        val n=(rate*5.0)/10
                        binding.ratingBar.rating=n.toFloat()
                        binding.rateNumber.text=details.vote_average.toString()
                        binding.year.text=details.release_date
                        binding.time.text="${details.runtime} minutes"
                        binding.overview.text=details.overview
                        companyItemAdapter=CompanyItemAdapter(details.production_companies,requireContext())
                        binding.recViewCompanies.adapter=companyItemAdapter

                    }
                }
            })
        }
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }
    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity?)?.showToolbar()
    }
}