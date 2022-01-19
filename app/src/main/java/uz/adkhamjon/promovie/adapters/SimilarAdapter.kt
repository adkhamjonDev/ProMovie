package uz.adkhamjon.promovie.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.adkhamjon.promovie.databinding.SimilarItemBinding
import uz.adkhamjon.promovie.models.Similar.Results

class SimilarAdapter(var context: Context,var list: List<Results>, var onItemClickListener: OnItemClickListener):
    RecyclerView.Adapter<SimilarAdapter.MyViewHolder>(){
    inner class MyViewHolder(var similarItemBinding: SimilarItemBinding): RecyclerView.ViewHolder(
        similarItemBinding.root){
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            SimilarItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val obj=list[position]
        holder.similarItemBinding.name.text=obj.title
        Glide.with(context).load("https://image.tmdb.org/t/p/w500/${obj.poster_path}")
            .into(holder.similarItemBinding.icon)
        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(obj.id)
        }
    }
    override fun getItemCount(): Int {
        return list.size
    }
    interface OnItemClickListener{
        fun onItemClick(id:Int)
    }
}