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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.adkhamjon.promovie.App
import uz.adkhamjon.promovie.adapters.RvAdapter
import uz.adkhamjon.promovie.databinding.FragmentHomeBinding
import uz.adkhamjon.promovie.models.MovieClass
import uz.adkhamjon.promovie.viewmodels.GridTypeViewModel
import uz.adkhamjon.promovie.viewmodels.MovieViewModel
import javax.inject.Inject

class HomeFragment : Fragment() {
    private lateinit var binding:FragmentHomeBinding

    private lateinit var rvAdapter: RvAdapter
    @Inject
    lateinit var movieViewModel: MovieViewModel
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var gridTypeViewModel: GridTypeViewModel
    private var spanCount=0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        App.appComponent.inject(this)
        binding= FragmentHomeBinding.inflate(inflater,container,false)

        gridTypeViewModel= ViewModelProviders.of(requireActivity())[GridTypeViewModel::class.java]

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
        gridTypeViewModel.getType().observe(viewLifecycleOwner,object: Observer<String> {
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
        return binding.root
    }
}