package com.example.mobimarket.presentation.password

import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mobimarket.R
import com.example.mobimarket.data.model.StateResult
import com.example.mobimarket.databinding.FragmentChangePasswordBinding
import com.example.mobimarket.utils.BaseFragment
import com.example.mobimarket.utils.setSafeOnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentChangePassword :
    BaseFragment<FragmentChangePasswordBinding>(FragmentChangePasswordBinding::inflate) {

    private val viewModel: ChangePasswordViewModel by viewModels()

    override fun onBindView() {
        super.onBindView()
        changePassword()
        observeChangePassword()
    }

    private fun changePassword() {
        binding.changePasswordBtn.setSafeOnClickListener {
            val password = binding.passwordEdit.text.toString()
            val confirmPassword = binding.confirmPasswordEdit.text.toString()

            if (password == confirmPassword) {
                viewModel.changePassword(password, confirmPassword)
            } else {
                binding.errorText.visibility = View.VISIBLE
                binding.passwordEdit.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.main_red)
                )
                binding.confirmPasswordEdit.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.main_red)
                )
            }
        }
    }


    private fun observeChangePassword() {
        viewModel.changePasswordCase.observe(viewLifecycleOwner) { result ->
            when (result) {
                StateResult.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is StateResult.Success<*> -> {
                    binding.progressBar.visibility = View.GONE
                    findNavController().navigate(R.id.action_fragmentChangePassword_to_loginFragment)
                }

                is StateResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

}