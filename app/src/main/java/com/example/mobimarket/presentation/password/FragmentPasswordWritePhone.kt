package com.example.mobimarket.presentation.password

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mobimarket.R
import com.example.mobimarket.data.model.StateResult
import com.example.mobimarket.databinding.FragmentWritePhoneBinding
import com.example.mobimarket.utils.BaseFragment
import com.example.mobimarket.utils.setSafeOnClickListener
import com.example.mobimarket.utils.showCustomSnackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentPasswordWritePhone :
    BaseFragment<FragmentWritePhoneBinding>(FragmentWritePhoneBinding::inflate) {

    private val viewModel: PasswordWritePhoneViewModel by viewModels()

    override fun onBindView() {
        super.onBindView()

        writePhoneNumber()
        observeVerifyCode()
    }

    private fun writePhoneNumber() {
        binding.verifyPhoneBtn.setSafeOnClickListener {
            val codePicker = binding.countryCode
            val countryCode = codePicker.selectedCountryCode
            val phoneNumber = "+" + countryCode + binding.phoneNumber.text.toString()
            viewModel.forgotPasswordGetCode(phoneNumber)
        }
    }

    private fun observeVerifyCode() {
        viewModel.forgotPassword.observe(viewLifecycleOwner) { result ->
            when (result) {
                StateResult.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is StateResult.Success<*> -> {
                    binding.progressBar.visibility = View.GONE
                    findNavController().navigate(R.id.action_fragmentPasswordWritePhone_to_fragmentPasswordVerifyCode)
                }

                is StateResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                    requireView().showCustomSnackbar("Повторите запрос что-то пошло не так")
                }
            }
        }
    }
}