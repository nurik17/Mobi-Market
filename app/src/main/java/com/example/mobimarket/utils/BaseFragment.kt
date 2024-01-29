package com.example.mobimarket.utils

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB: ViewBinding>(private val inflate: Inflate<VB>): Fragment() {
    private var _binding: VB? = null

    val binding get() = _binding ?: throw java.lang.Exception()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            onInit()
            onBindView()
            bindViewModel()
    }

    open fun onInit() {}
    open fun onBindView() {}
    open fun bindViewModel() {}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

