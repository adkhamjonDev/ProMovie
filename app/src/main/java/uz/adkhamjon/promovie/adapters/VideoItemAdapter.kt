package uz.adkhamjon.promovie.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.adkhamjon.promovie.databinding.ImageItemBinding
import uz.adkhamjon.promovie.databinding.ImagesItemBinding
import uz.adkhamjon.promovie.databinding.VideoItemBinding
import uz.adkhamjon.promovie.models.Images.Backdrop
import uz.adkhamjon.promovie.models.Video.Result

class VideoItemAdapter(
    var list: List<Result>,
    var context: Context,
    var onItemClickListener: OnItemClickListener):
    RecyclerView.Adapter<VideoItemAdapter.MyViewHolder>(){
    inner class MyViewHolder(var videoItemBinding: VideoItemBinding): RecyclerView.ViewHolder(
        videoItemBinding.root){
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            VideoItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val obj=list[position]
        Glide.with(context).load("https://img.youtube.com/vi/${obj.key}/hqdefault.jpg")
            .into(holder.videoItemBinding.icon)
        holder.videoItemBinding.type.text=obj.type
        holder.videoItemBinding.site.text=obj.site

        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(obj.key)
        }
    }
    override fun getItemCount(): Int {
        return list.size
    }
    interface OnItemClickListener{
        fun onItemClick(id:String)
    }
}