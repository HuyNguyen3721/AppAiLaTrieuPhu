package com.example.appailatrieuphu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appailatrieuphu.databinding.LayoutHightScoreBinding
import com.example.appailatrieuphu.databinding.LayoutItemHightScoreBinding

class AdapterRecycleView(private  val arr : MutableList<Data_hightscore>)  : RecyclerView.Adapter<AdapterRecycleView.ViewHolder>(){

    class ViewHolder(var binding : LayoutItemHightScoreBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return  ViewHolder(LayoutItemHightScoreBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.datahightscore = arr[position]
    }

    override fun getItemCount(): Int {
        return  arr.size
    }

}