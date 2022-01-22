package uz.adkhamjon.promovie.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.adkhamjon.promovie.databinding.CreditsItemBinding
import uz.adkhamjon.promovie.databinding.ImageItemBinding
import uz.adkhamjon.promovie.databinding.ImagesItemBinding
import uz.adkhamjon.promovie.databinding.VideoItemBinding
import uz.adkhamjon.promovie.models.Credits.Cast
import uz.adkhamjon.promovie.models.Images.Backdrop
import uz.adkhamjon.promovie.models.Video.Result

class CreditsItemAdapter(
    var list: List<Cast>,
    var context: Context,
    var onItemClickListener: OnItemClickListener):
    RecyclerView.Adapter<CreditsItemAdapter.MyViewHolder>(){
    inner class MyViewHolder(var creditsItemBinding: CreditsItemBinding): RecyclerView.ViewHolder(
        creditsItemBinding.root){
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            CreditsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val obj=list[position]
        Glide.with(context).load("https://image.tmdb.org/t/p/w500/${obj.profile_path}")
            .into(holder.creditsItemBinding.icon)
        holder.creditsItemBinding.name.text=obj.original_name
        //holder.creditsItemBinding.character.text=obj.character

        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(position)
        }
    }
    override fun getItemCount(): Int {
        return list.size
    }
    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }
}