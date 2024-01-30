package com.example.mobimarket.presentation.home.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobimarket.data.model.ProductImage
import com.example.mobimarket.databinding.ScrollItemBinding

class ScrollViewHolder(private val binding: ScrollItemBinding):RecyclerView.ViewHolder(binding.root){

    fun bind(item: ProductImage){
        Glide.with(binding.scrollImage)
            .load(item)
            .into(binding.scrollImage)
    }
}