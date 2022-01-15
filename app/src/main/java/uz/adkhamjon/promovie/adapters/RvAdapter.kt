package uz.adkhamjon.promovie.adapters
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.adkhamjon.promovie.databinding.GridItem2Binding
import uz.adkhamjon.promovie.databinding.GridItem3Binding
import uz.adkhamjon.promovie.databinding.LinearItemBinding
import uz.adkhamjon.promovie.models.MovieClass

class RvAdapter(var context: Context,var gridLayoutManager: GridLayoutManager,var onItemClickListener: OnItemClickListener) :
    PagingDataAdapter<MovieClass, RecyclerView.ViewHolder>(MyDiffUtill()) {
    class MyDiffUtill : DiffUtil.ItemCallback<MovieClass>() {
        override fun areItemsTheSame(oldItem: MovieClass, newItem: MovieClass): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: MovieClass, newItem: MovieClass): Boolean {
            return oldItem == newItem
        }
    }
    inner class Linear(var linearItemBinding: LinearItemBinding) :
        RecyclerView.ViewHolder(linearItemBinding.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(movieClass: MovieClass) {
            linearItemBinding.tittle.text=movieClass.title
            linearItemBinding.date.text="Year: ${movieClass.release_date}"
            Glide.with(context).load("https://image.tmdb.org/t/p/w500/${movieClass.poster_path}")
                .into(linearItemBinding.icon)
        }
    }
    inner class Grid2(var gridImageBinding: GridItem2Binding) :
        RecyclerView.ViewHolder(gridImageBinding.root) {
        fun onBind(movieClass: MovieClass) {
            gridImageBinding.name.text = movieClass.title
            Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500/${movieClass.poster_path}")
                .into(gridImageBinding.icon)
        }

    }
    inner class Grid3(var gridImageBinding: GridItem3Binding) :
        RecyclerView.ViewHolder(gridImageBinding.root) {
        fun onBind(movieClass: MovieClass) {
            gridImageBinding.name.text = movieClass.title
            Glide.with(context).load("https://image.tmdb.org/t/p/w500/${movieClass.poster_path}")
                .into(gridImageBinding.icon)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1) {
            return Linear(
                LinearItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else if(viewType==2) {
            return Grid2(
                GridItem2Binding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
        else{
            return Grid3(
                GridItem3Binding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            )
        }
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == 1) {
            val fromVh = holder as Linear
            getItem(position)?.let { movie->
                fromVh.onBind(movie)
                holder.itemView.setOnClickListener {
                    onItemClickListener.itemClick(movie)
                }
            }
        } else if(getItemViewType(position)==2){
            val toVh = holder as Grid2
            getItem(position)?.let { movie->
                toVh.onBind(movie)
                holder.itemView.setOnClickListener {
                    onItemClickListener.itemClick(movie)
                }
            }
        }
        else{
            val toVh = holder as Grid3
            getItem(position)?.let { movie->
                toVh.onBind(movie)
                holder.itemView.setOnClickListener {
                    onItemClickListener.itemClick(movie)
                }
            }
        }
    }
    override fun getItemViewType(position: Int): Int {
        if (gridLayoutManager.spanCount == 1) {
            return 1
        }
        else if(gridLayoutManager.spanCount==2){
            return 2
        }
        return 3
    }
    interface OnItemClickListener{
         fun itemClick(movieClass: MovieClass)
    }
}