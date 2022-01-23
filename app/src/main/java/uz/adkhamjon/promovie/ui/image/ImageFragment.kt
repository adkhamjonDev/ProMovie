package uz.adkhamjon.promovie.ui.image

import android.Manifest
import android.R.attr
import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ixuea.android.downloader.DownloadService
import uz.adkhamjon.promovie.MainActivity
import uz.adkhamjon.promovie.adapters.ImagePagerAdapter
import uz.adkhamjon.promovie.databinding.FragmentImageBinding
import android.os.Environment
import android.widget.Toast
import java.io.File


import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import android.app.Notification
import android.app.WallpaperManager
import android.graphics.Bitmap
import com.artjimlop.altex.AltexImageDownloader
import com.google.android.gms.common.images.ImageManager
import uz.adkhamjon.promovie.R

import android.widget.LinearLayout
import android.widget.TextView

import com.google.android.material.bottomsheet.BottomSheetDialog
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.drawable.BitmapDrawable
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.squareup.picasso.Picasso
import java.io.IOException




@RequiresApi(Build.VERSION_CODES.N)
class ImageFragment : Fragment(), PopupMenu.OnMenuItemClickListener {
    private lateinit var binding:FragmentImageBinding
    private lateinit var imagePagerAdapter: ImagePagerAdapter
    private lateinit var list:ArrayList<String>
    private var pos=0
    private var currentItem=0
    private val PERMISSION_REQUEST_CODE = 7
    private lateinit var file:File

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity?)?.hideToolbar()
        binding=FragmentImageBinding.inflate(inflater, container, false)
        if(arguments!=null){
            pos= arguments?.getInt("pos") as Int
            list=arguments?.getStringArrayList("list") as ArrayList<String>
        }
        Picasso.get().load("https://image.tmdb.org/t/p/w500/${list[pos]}").into(binding.image)
        imagePagerAdapter= ImagePagerAdapter(requireContext(),list)
        binding.viewPager.adapter=imagePagerAdapter
        binding.viewPager.currentItem=pos
        currentItem = binding.viewPager.currentItem
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.rotate.setOnClickListener {
            (activity as MainActivity?)?.toLandscape()
        }
        binding.share.setOnClickListener {
            ShareCompat.IntentBuilder.from(requireActivity())
                .setType("image/jpeg")
                .setText("https://image.tmdb.org/t/p/w500/${list[currentItem]}")
                .startChooser()
        }
        binding.more.setOnClickListener {
            val popup = PopupMenu(requireContext(),it)
            popup.setOnMenuItemClickListener(this)
            popup.inflate(R.menu.image_page_menu)
            popup.show()
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity?)?.showToolbar()
        (activity as MainActivity?)?.toPortrait()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onMenuItemClick(p0: MenuItem?): Boolean {
        when(p0?.itemId){
            R.id.download->{
//                val downloadManager = DownloadService.
//                getDownloadManager(requireContext().applicationContext)
//                val dirPath="${Environment.getExternalStorageDirectory()}/DCIM"
//                val fileName=list[currentItem]
//                file= File(dirPath,fileName)
//                val downloadInfo = DownloadInfo
//                    .Builder()
//                    .setUrl(
//                    "https://image.tmdb.org/t/p/w500/${list[currentItem]}"
//                ).setPath(file.absolutePath)
//                    .build()
//               downloadInfo.downloadListener = object : DownloadListener {
//                   override fun onStart() {
//
//
//                   }
//
//                   override fun onWaited() {
//
//
//                   }
//
//                   override fun onPaused() {
//
//
//                   }
//
//                   override fun onDownloading(progress: Long, size: Long) {
//
//                   }
//
//                   override fun onRemoved() {
//
//                   }
//
//                   override fun onDownloadSuccess() {
//
//                   }
//
//                   override fun onDownloadFailed(e: DownloadException) {
//
//                   }
//               }
//
//               downloadManager.download(downloadInfo)


                AltexImageDownloader(object:
                    AltexImageDownloader.OnImageLoaderListener{
                    override fun onError(error: AltexImageDownloader.ImageError?) {
                        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                    }

                    override fun onProgressChange(percent: Int) {
                        Toast.makeText(requireContext(), "Downloading", Toast.LENGTH_SHORT).show()
                    }

                    override fun onComplete(result: Bitmap?) {
                        Toast.makeText(requireContext(), "Downloaded", Toast.LENGTH_SHORT).show()
                    }
                }).download("https://image.tmdb.org/t/p/w500/${list[currentItem]}",
                    true)
                AltexImageDownloader.writeToDisk(requireContext(),
                    "https://image.tmdb.org/t/p/w500/${list[currentItem]}",
                    "Promovie")
           }
            R.id.wallpaper->{

            showBottomSheetDialog()
            }
        }
        return false
    }

    private fun askPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                createDirectory()
            } else {
                Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun createDirectory() {
        val file = File(Environment.getExternalStorageDirectory().absolutePath + "/MyFolder")
        val exists = file.exists()
        if (!exists) {
            file.mkdir()
            Toast.makeText(requireContext(), "Successful", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Folder Already Exists", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showBottomSheetDialog() {
        val wallManager = WallpaperManager.getInstance(context)
        val bmpImg = (binding.image.drawable as BitmapDrawable).bitmap
        val bottomSheetDialog = BottomSheetDialog(requireContext(),R.style.CustomBottomSheetDialogTheme)
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog)
        val home=bottomSheetDialog.findViewById<TextView>(R.id.home)
        val lock=bottomSheetDialog.findViewById<TextView>(R.id.lock)
        val homeLock=bottomSheetDialog.findViewById<TextView>(R.id.homeAndLock)
        home?.setOnClickListener {
            try {
                wallManager.setBitmap(bmpImg,null, true,WallpaperManager.FLAG_SYSTEM)
                Toast.makeText(context, "Wallpaper Set Successfully!!", Toast.LENGTH_SHORT).show()

            } catch (e: IOException) {
                Toast.makeText(context, "Setting WallPaper Failed!!", Toast.LENGTH_SHORT).show()
            }
            bottomSheetDialog.hide()
        }
        lock?.setOnClickListener {
            try {
                wallManager.setBitmap(bmpImg,null, true,WallpaperManager.FLAG_LOCK)
                Toast.makeText(context, "Wallpaper Set Successfully!!", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                Toast.makeText(context, "Setting WallPaper Failed!!", Toast.LENGTH_SHORT).show()
            }
            bottomSheetDialog.hide()
        }
        homeLock?.setOnClickListener {
            try {
                wallManager.setBitmap(bmpImg,null, true,WallpaperManager.FLAG_SYSTEM)
                wallManager.setBitmap(bmpImg,null, true,WallpaperManager.FLAG_LOCK)
                Toast.makeText(context, "Wallpaper Set Successfully!!", Toast.LENGTH_SHORT).show()

            } catch (e: IOException) {
                Toast.makeText(context, "Setting WallPaper Failed!!", Toast.LENGTH_SHORT).show()
            }
            bottomSheetDialog.hide()
            bottomSheetDialog.hide()
        }
        bottomSheetDialog.show()
    }
}