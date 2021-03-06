package uz.adkhamjon.promovie.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.adkhamjon.promovie.databinding.ImageItemBinding
import uz.adkhamjon.promovie.databinding.ImagesItemBinding
import uz.adkhamjon.promovie.models.Images.Backdrop


class ImageItemAdapter(
    var list: List<Backdrop>,
    var context: Context,
    var onItemClickListener: OnItemClickListener):
    RecyclerView.Adapter<ImageItemAdapter.MyViewHolder>(){
    inner class MyViewHolder(var imageItemBinding: ImageItemBinding): RecyclerView.ViewHolder(
        imageItemBinding.root){
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ImageItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val obj=list[position]
        Glide.with(context).load("https://image.tmdb.org/t/p/w500/${obj.file_path}")
            .into(holder.imageItemBinding.icon)

        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(position)
        }
    }
    override fun getItemCount(): Int {
        return list.size
    }
    interface OnItemClickListener{
        fun onItemClick(position:Int)
    }
}