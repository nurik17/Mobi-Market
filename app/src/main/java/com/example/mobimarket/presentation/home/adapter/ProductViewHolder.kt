package com.example.mobimarket.presentation.home.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.mobimarket.data.model.Product
import com.example.mobimarket.databinding.ItemProductBinding

class ProductViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        product: Product,
        onClick: (clickableView: ClickableView, item: Product) -> Unit
    ) {
        binding.apply {
            productName.text = product.name
            productPrice.text = product.price
            countFavourite.text = product.like_count.toString()

            Glide.with(productImage)
                .load(product.images.firstOrNull()?.image)
                .apply(RequestOptions().transform(RoundedCorners(20))) //round corner
                .into(productImage)

            icDetails.setOnClickListener {
                onClick(ClickableView.ONDETAILSCLICK,product)
            }
            root.setOnClickListener {
                onClick.invoke(ClickableView.ONCLICK,product)
            }
        }
    }
}

enum class ClickableView {
    ONCLICK, ONDETAILSCLICK
}
