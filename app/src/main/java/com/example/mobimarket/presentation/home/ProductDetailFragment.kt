package com.example.mobimarket.presentation.home

import android.annotation.SuppressLint
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.mobimarket.data.model.Product
import com.example.mobimarket.data.model.Resource
import com.example.mobimarket.databinding.FragmentProductDetailBinding
import com.example.mobimarket.presentation.home.adapter.ScrollImageAdapter
import com.example.mobimarket.utils.BaseFragment
import com.example.mobimarket.utils.setSafeOnClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductDetailFragment :
    BaseFragment<FragmentProductDetailBinding>(FragmentProductDetailBinding::inflate) {

    private val viewModel: ProductDetailViewModel by viewModels()

    private val scrollImageAdapter by lazy {
        ScrollImageAdapter()
    }


    override fun onBindView() {
        super.onBindView()

        val args = requireArguments().getParcelable<Product>("product")

        args?.id?.let { getProductInfoById(it) }
        getProductInfo()
        setUpScrollRv()
        setUpBackButton()

    }

    private fun getProductInfoById(productId: Int) {
        viewModel.getProductInfoById(productId)
    }

    @SuppressLint("SetTextI18n")
    private fun getProductInfo() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.productInfo.collect { state ->
                    when (state) {
                        is Resource.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }

                        is Resource.Success -> {
                            binding.apply {
                                progressBar.visibility = View.GONE
                                productDetailPrice.text = state.data?.price
/*
                                productPhoneNumber.text = state.data?.user?.phone
*/
                                textLiked.text = "Нравится: "+state.data?.like_count.toString()
                                productName.text = state.data?.name
                                productNameDescription.text = state.data?.full_description
                                tvDetailDescription.text = state.data?.short_description
                            }
                            scrollImageAdapter.setData(state.data?.images)
                        }

                        is Resource.Error -> {
                            binding.progressBar.visibility = View.GONE
                        }

                        else -> Unit
                    }
                }
            }
        }
    }

    private fun setUpScrollRv() {
        binding.apply {
            viewPagerImages.adapter = scrollImageAdapter
            dotsIndicator.attachTo(viewPagerImages)
        }
    }

    private fun setUpBackButton() {
        binding.icArrowLeft.setSafeOnClickListener {
            findNavController().popBackStack()
        }
    }
}