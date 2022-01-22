package uz.adkhamjon.promovie.adapters
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.adkhamjon.promovie.databinding.LinearItemBinding
import uz.adkhamjon.promovie.models.MovieClass

class SearchAdapter(
    var context: Context,
    var list: List<MovieClass>,
    var onItemClickListener: OnItemClickListener):
    RecyclerView.Adapter<SearchAdapter.MyViewHolder>() {

    inner class MyViewHolder(var linearItemBinding: LinearItemBinding): RecyclerView.ViewHolder(
        linearItemBinding.root){
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LinearItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val obj=list[position]
        holder.linearItemBinding.tittle.text=obj.original_title
        holder.linearItemBinding.date.text="Year: ${obj.release_date}"
        Glide.with(context).load("https://image.tmdb.org/t/p/w500/${obj.poster_path}")
            .into(holder.linearItemBinding.icon)
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