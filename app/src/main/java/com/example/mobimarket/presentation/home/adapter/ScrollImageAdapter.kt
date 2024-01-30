package com.example.mobimarket.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.mobimarket.data.model.ProductImage
import com.example.mobimarket.databinding.ScrollItemBinding

class ScrollImageAdapter(): ListAdapter<ProductImage, ScrollViewHolder>(DiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScrollViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ScrollItemBinding.inflate(inflater, parent, false)
        return ScrollViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScrollViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    fun setData(image: List<ProductImage>?) {
        submitList(image)
    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<ProductImage>() {
        override fun areItemsTheSame(oldItem: ProductImage, newItem: ProductImage): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ProductImage, newItem: ProductImage): Boolean {
            return oldItem == newItem
        }
    }
}