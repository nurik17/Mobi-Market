package com.example.mobimarket.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.example.mobimarket.data.model.Product
import com.example.mobimarket.databinding.ItemProductBinding

class HomeAdapter(
    private val onClick: (clickableView: ClickableView, item: Product) -> Unit,
) : PagingDataAdapter<Product, ProductViewHolder>(DiffUtilCallBack()) {

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item, onClick = onClick)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ItemProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}