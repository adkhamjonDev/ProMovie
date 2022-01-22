package uz.adkhamjon.promovie.ui.search

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.youtubeapi.utils.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.adkhamjon.promovie.App
import uz.adkhamjon.promovie.MainActivity
import uz.adkhamjon.promovie.R
import uz.adkhamjon.promovie.adapters.SearchAdapter
import uz.adkhamjon.promovie.databinding.FragmentSearchBinding
import uz.adkhamjon.promovie.models.MainClass
import uz.adkhamjon.promovie.viewmodels.MovieViewModel
import javax.inject.Inject

class SearchFragment : Fragment() {
    private lateinit var binding:FragmentSearchBinding
    @Inject
    lateinit var movieViewModel: MovieViewModel
    private lateinit var searchAdapter: SearchAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentSearchBinding.inflate(inflater, container, false)
        App.appComponent.inject(this)

        binding.edit.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                CoroutineScope(Dispatchers.Main).launch {
                    movieViewModel.getSearch(p0.toString()).observe(viewLifecycleOwner, {
                        when (it.status) {
                            Status.ERROR -> {
                                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()

                            }
                            Status.LOADING -> {

                            }
                            Status.SUCCESS -> {
                                searchAdapter= SearchAdapter(requireContext(),it.data!!.results,object:SearchAdapter.OnItemClickListener{
                                    override fun onItemClick(id: Int) {
                                        val bundle= bundleOf("id" to id)
                                        findNavController().navigate(R.id.infoFragment,bundle)
                                    }
                                })
                                binding.recView.adapter=searchAdapter

                            }
                        }
                    })

                }
            }
            override fun afterTextChanged(p0: Editable?) {

            }
        })
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.voice.setOnClickListener {
            val intent= Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speech to text")
            startActivityForResult(intent,1)
        }
        return binding.root
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, result: Intent?) {
        if(requestCode==1 && resultCode== Activity.RESULT_OK){
            val stringArrayListExtra = result?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>
            binding.edit.setText(stringArrayListExtra[0])
        }
        super.onActivityResult(requestCode, resultCode, result)

    }
}