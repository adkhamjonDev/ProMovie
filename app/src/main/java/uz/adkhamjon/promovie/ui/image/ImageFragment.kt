package uz.adkhamjon.promovie.ui.image

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import uz.adkhamjon.promovie.MainActivity
import uz.adkhamjon.promovie.databinding.FragmentImageBinding
class ImageFragment : Fragment() {
    private lateinit var binding:FragmentImageBinding
    private var image=""
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity?)?.hideToolbar()
        binding=FragmentImageBinding.inflate(inflater, container, false)
        if(arguments!=null){
            image=arguments?.getString("image")as String
        }
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.rotate.setOnClickListener {

        }
        binding.share.setOnClickListener {
            ShareCompat.IntentBuilder.from(requireActivity())
                .setType("image/jpeg")
                .setText("https://image.tmdb.org/t/p/w500/$image")
                .startChooser()
        }
        binding.more.setOnClickListener {

        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity?)?.showToolbar()
    }

}