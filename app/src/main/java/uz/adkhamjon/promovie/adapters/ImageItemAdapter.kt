package uz.adkhamjon.promovie.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.adkhamjon.promovie.databinding.ImagesItemBinding
import uz.adkhamjon.promovie.models.Images.Backdrop


class ImageItemAdapter(var list: List<Backdrop>,var context: Context):
    RecyclerView.Adapter<ImageItemAdapter.MyViewHolder>(){
    inner class MyViewHolder(var imagesItemBinding: ImagesItemBinding): RecyclerView.ViewHolder(
        imagesItemBinding.root){
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ImagesItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val obj=list[position]
        Glide.with(context).load("https://image.tmdb.org/t/p/w500/${obj.file_path}")
            .into(holder.imagesItemBinding.icon)
    }
    override fun getItemCount(): Int {
        return list.size
    }
}