package uz.adkhamjon.promovie.ui.search

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import uz.adkhamjon.promovie.MainActivity
import uz.adkhamjon.promovie.R
import uz.adkhamjon.promovie.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private lateinit var binding:FragmentSearchBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity?)?.hideToolbar()
        binding=FragmentSearchBinding.inflate(inflater, container, false)


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
    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity?)?.showToolbar()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, result: Intent?) {
        if(requestCode==1 && resultCode== Activity.RESULT_OK){
            val stringArrayListExtra = result?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>
            binding.edit.setText(stringArrayListExtra[0])
        }
        super.onActivityResult(requestCode, resultCode, result)

    }
}