package uz.adkhamjon.promovie.ui.info

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.youtubeapi.utils.Status
import com.google.android.youtube.player.*
import com.mig35.carousellayoutmanager.CarouselLayoutManager
import com.mig35.carousellayoutmanager.CarouselZoomPostLayoutListener
import com.mig35.carousellayoutmanager.CenterScrollListener
import kotlinx.coroutines.*
import uz.adkhamjon.promovie.App
import uz.adkhamjon.promovie.MainActivity
import uz.adkhamjon.promovie.R
import uz.adkhamjon.promovie.adapters.CompanyItemAdapter
import uz.adkhamjon.promovie.adapters.ImageItemAdapter
import uz.adkhamjon.promovie.adapters.SimilarAdapter
import uz.adkhamjon.promovie.databinding.FragmentInfoBinding
import uz.adkhamjon.promovie.models.Details.MovieDetails
import uz.adkhamjon.promovie.utils.Config
import uz.adkhamjon.promovie.viewmodels.MovieViewModel
import javax.inject.Inject
import androidx.fragment.app.FragmentTransaction

import com.google.android.youtube.player.YouTubePlayerSupportFragment












class InfoFragment : Fragment() {
    private lateinit var binding:FragmentInfoBinding
    @Inject
    lateinit var movieViewModel: MovieViewModel
    private var movieID=0
    private lateinit var companyItemAdapter: CompanyItemAdapter
    private lateinit var imageItemAdapter: ImageItemAdapter
    private lateinit var similarAdapter: SimilarAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        App.appComponent.inject(this)
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
                        setDetails(details!!)

                    }
                }
            })

        }
        CoroutineScope(Dispatchers.Main).launch {
            movieViewModel.getImages(movieID).observe(viewLifecycleOwner, {
                when (it.status) {
                    Status.ERROR -> {

                    }
                    Status.LOADING -> {

                    }
                    Status.SUCCESS -> {
                        imageItemAdapter= ImageItemAdapter(it.data!!.backdrops,requireContext())
                        val manager= CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL,true)
                        binding.imageRecView.layoutManager=manager
                        binding.imageRecView.setHasFixedSize(true)
                        manager.maxVisibleItems=3
                        manager.setPostLayoutListener(CarouselZoomPostLayoutListener())
                        binding.imageRecView.adapter = imageItemAdapter
                        binding.imageRecView.addOnScrollListener(CenterScrollListener())
                    }
                }
            })

        }
        CoroutineScope(Dispatchers.Main).launch {
            movieViewModel.getSimilar(movieID).observe(viewLifecycleOwner, {
                when (it.status) {
                    Status.ERROR -> {

                    }
                    Status.LOADING -> {

                    }
                    Status.SUCCESS -> {
                        similarAdapter= SimilarAdapter(requireContext(),it.data!!.results
                            ,object:SimilarAdapter.OnItemClickListener{
                                override fun onItemClick(id: Int) {
                                    val bundle= bundleOf("id" to id)
                                    findNavController().navigate(R.id.infoFragment,bundle)
                                }
                            })
                        binding.recViewSimilar.adapter=similarAdapter
                    }
                }
            })

        }

        val youtubeFragment:YouTubePlayerFragment=childFragmentManager
            .findFragmentById(R.id.you_tube_player) as YouTubePlayerFragment
        youtubeFragment.initialize(Config.YOUTUBE_API_KEY,object:YouTubePlayer.OnInitializedListener{
            override fun onInitializationSuccess(
                p0: YouTubePlayer.Provider?,
                p1: YouTubePlayer?,
                p2: Boolean
            ) {
                if(p1==null) return
                if(p2){
                    p1.play()
                }
                else{
                    p1.cueVideo("3F_hFXKVBuo")
                }

            }

            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {

            }

        })

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun setDetails(details: MovieDetails){
        binding.tittle.text=details.title
        binding.name.text=details.title
        Glide.with(requireContext()).load(Config.IMAGE_BASE_URL+details.poster_path)
            .into(binding.icon)
        val rate=details.vote_average
        val n=(rate*5.0)/10
        binding.ratingBar.rating=n.toFloat()
        binding.rateNumber.text=details.vote_average.toString()
        binding.year.text=details.release_date
        binding.time.text="${details.runtime} minutes"
        binding.status.text=details.status
        binding.overview.text=details.overview
        companyItemAdapter=CompanyItemAdapter(details.production_companies,requireContext())
        binding.recViewCompanies.adapter=companyItemAdapter

        val country=StringBuilder()
        details.production_countries.forEach {
            country.append(it.name+", ")
        }
        binding.country.text=country.toString().subSequence(0,country.length-2)

        val spokenLang=StringBuilder()
        details.spoken_languages.forEach {
            spokenLang.append(it.name+", ")
        }
        binding.language.text=spokenLang.toString().subSequence(0,spokenLang.length-2)

        val genres=StringBuilder()
        details.genres.forEach {
            genres.append(it.name+", ")
        }
        binding.genre.text=genres.toString().subSequence(0,genres.length-2)


    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity?)?.hideToolbar()

    }
    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity?)?.showToolbar()
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity?)?.hideToolbar()
    }



}