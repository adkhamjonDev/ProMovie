package uz.adkhamjon.promovie.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.adkhamjon.promovie.App
import uz.adkhamjon.promovie.R
import uz.adkhamjon.promovie.adapters.RvAdapter
import uz.adkhamjon.promovie.databinding.FragmentHomeBinding
import uz.adkhamjon.promovie.models.MovieClass
import uz.adkhamjon.promovie.viewmodels.TypeViewModel
import uz.adkhamjon.promovie.viewmodels.MovieViewModel
import javax.inject.Inject

class HomeFragment : Fragment() {
    private lateinit var binding:FragmentHomeBinding
    private lateinit var rvAdapter: RvAdapter
    @Inject
    lateinit var movieViewModel: MovieViewModel
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var typeViewModel: TypeViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        App.appComponent.inject(this)
        binding= FragmentHomeBinding.inflate(inflater,container,false)

        typeViewModel= ViewModelProviders.of(requireActivity())[TypeViewModel::class.java]

        gridLayoutManager= GridLayoutManager(context,1)
        rvAdapter = RvAdapter(requireContext(),gridLayoutManager,object:RvAdapter.OnItemClickListener{
            override  fun itemClick(movieClass: MovieClass) {

            }
        })
        binding.recView.hasFixedSize()
        binding.recView.layoutManager=gridLayoutManager
        binding.recView.adapter = rvAdapter

        lifecycleScope.launch {
            movieViewModel.popular.collectLatest {
                rvAdapter.submitData(it)
            }
        }
        //---------------------------------------------------------------------
        typeViewModel.getGridType().observe(viewLifecycleOwner,object: Observer<String> {
            override fun onChanged(t: String?) {
               if(t=="1"){
                   gridLayoutManager.spanCount = 1
                   rvAdapter.notifyItemRangeChanged(0, rvAdapter.itemCount)
               }
                else if (t=="2"){
                   gridLayoutManager.spanCount = 2
                   rvAdapter.notifyItemRangeChanged(0, rvAdapter.itemCount)
               }
                else if (t=="3"){
                   gridLayoutManager.spanCount = 3
                   rvAdapter.notifyItemRangeChanged(0, rvAdapter.itemCount)
               }
            }
        })
        //-------------------------------------------------------------------------
        typeViewModel.getDialogType().observe(viewLifecycleOwner,object: Observer<String> {
            override fun onChanged(t: String?) {
                if(t=="Popular"){
                    lifecycleScope.launch {
                        movieViewModel.popular.collectLatest {
                            rvAdapter.submitData(it)
                        }
                    }
                }
                else if (t=="Top Rated"){
                    lifecycleScope.launch {
                        movieViewModel.topRated.collectLatest {
                            rvAdapter.submitData(it)
                        }
                    }
                }
                else if (t=="Upcoming"){
                    lifecycleScope.launch {
                        movieViewModel.upcoming.collectLatest {
                            rvAdapter.submitData(it)
                        }
                    }
                }
                else if (t=="Now Playing"){
                    lifecycleScope.launch {
                        movieViewModel.nowPlaying.collectLatest {
                            rvAdapter.submitData(it)
                        }
                    }
                }
            }
        })
        return binding.root
    }
}