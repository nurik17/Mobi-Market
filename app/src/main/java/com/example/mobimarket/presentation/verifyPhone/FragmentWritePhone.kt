package com.example.mobimarket.presentation.verifyPhone

import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mobimarket.R
import com.example.mobimarket.data.entity.StateResult
import com.example.mobimarket.databinding.FragmentWritePhoneBinding
import com.example.mobimarket.utils.BaseFragment
import com.example.mobimarket.utils.setSafeOnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentWritePhone: BaseFragment<FragmentWritePhoneBinding>(FragmentWritePhoneBinding::inflate) {

    private val viewModel: FragmentWritePhoneViewModel by viewModels()

    override fun onBindView() {
        super.onBindView()

        writePhoneNUmber()
        observeVerifyCode()
    }

    private fun writePhoneNUmber(){
        binding.verifyPhoneBtn.setSafeOnClickListener {
            val codePicker = binding.countryCode
            val countryCode = codePicker.selectedCountryCode
            val phoneNumber = "+" + countryCode + binding.phoneNumber.text.toString()
            Log.d("FragmentWritePhone", phoneNumber)
            viewModel.addPhoneNumber(phoneNumber)
        }
    }

    private fun observeVerifyCode() {
        viewModel.addNumber.observe(viewLifecycleOwner) { result ->
            when (result) {
                StateResult.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    Log.d("FragmentWritePhone", "observeUpdateInfo: loading")
                }
                is StateResult.Success<*> -> {
                    binding.progressBar.visibility = View.GONE
                    findNavController().navigate(R.id.action_fragmentWritePhone_to_fragmentVerifyCode)
                    Log.d("FragmentWritePhone", "observeUpdateInfo: success")
                }

                is StateResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Log.d("FragmentWritePhone", "observeUpdateInfo: error")
                }
            }
        }
    }
}