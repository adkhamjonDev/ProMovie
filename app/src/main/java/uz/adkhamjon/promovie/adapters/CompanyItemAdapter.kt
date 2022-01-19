package uz.adkhamjon.promovie.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.adkhamjon.promovie.R
import uz.adkhamjon.promovie.databinding.CompanyItemBinding
import uz.adkhamjon.promovie.models.Details.ProductionCompany

class CompanyItemAdapter(var list: List<ProductionCompany>,var context: Context):
    RecyclerView.Adapter<CompanyItemAdapter.MyViewHolder>(){
    inner class MyViewHolder(var companyItemBinding: CompanyItemBinding): RecyclerView.ViewHolder(
        companyItemBinding.root){
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            CompanyItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val obj=list[position]
        holder.companyItemBinding.name.text=obj.name
        Glide.with(context).load("https://image.tmdb.org/t/p/w500/${obj.logo_path}")
            .placeholder(R.drawable.place).into(holder.companyItemBinding.icon)
    }
    override fun getItemCount(): Int {
        return list.size
    }
}