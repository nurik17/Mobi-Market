package com.example.mobimarket.presentation.home

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.mobimarket.R
import com.example.mobimarket.data.model.Product
import com.example.mobimarket.databinding.FragmentHomeBinding
import com.example.mobimarket.presentation.home.adapter.ClickableView
import com.example.mobimarket.presentation.home.adapter.HomeAdapter
import com.example.mobimarket.utils.BaseFragment
import com.example.mobimarket.utils.OffsetDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val homeAdapter = HomeAdapter { clickableView, item ->
        onClick(clickableView, item)
    }
    private val viewModel: HomeViewModel by viewModels()

    override fun onBindView() {
        super.onBindView()

        getProducts()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        homeAdapter.loadStateFlow.onEach {
            binding.refreshLayout.isRefreshing = it.refresh == LoadState.Loading
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.productsRecyclerView.adapter = homeAdapter
        binding.productsRecyclerView.addItemDecoration(OffsetDecoration(20, 10))

        binding.refreshLayout.setOnRefreshListener {
            homeAdapter.refresh()
        }


    }
    private fun getProducts(){
        viewModel.getProducts.onEach {
            homeAdapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun onClick(clickableView: ClickableView, product: Product) {
        when (clickableView) {
            ClickableView.ONCLICK -> navigateProductDetails(product)
            ClickableView.ONDETAILSCLICK -> findNavController().navigate(R.id.action_homeFragment_to_productDetailFragment)
        }
    }

    private fun navigateProductDetails(product: Product){
        val bundle = Bundle().apply {
            putParcelable("product",product)
        }
        findNavController().navigate(R.id.action_homeFragment_to_productDetailFragment,bundle)
    }
}
