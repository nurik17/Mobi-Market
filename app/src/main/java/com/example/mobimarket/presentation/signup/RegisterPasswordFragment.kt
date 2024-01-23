/*
package com.example.mobimarket.presentation.signup

import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mobimarket.R
import com.example.mobimarket.data.entity.RegisterResult
import com.example.mobimarket.databinding.FragmentRegisterPasswordBinding
import com.example.mobimarket.utils.BaseFragment
import com.example.mobimarket.utils.setSafeOnClickListener
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterPasswordFragment :
    BaseFragment<FragmentRegisterPasswordBinding>(FragmentRegisterPasswordBinding::inflate) {


    override fun onBindView() {
        super.onBindView()

        val userName = arguments?.getString("userName")
        val email = arguments?.getString("email")

        binding.apply {
            btnRegisterFully.setSafeOnClickListener {
                val password = mainPasswordEdit.text.toString()
                val confirmPassword = confirmPasswordEdit.text.toString()
                if (userName != null && email != null) {
                    if(arePasswordsValid(password, confirmPassword)){
                        viewModel.registerCheck(userName,email,password,confirmPassword)
                    }
                }else{
                    handleLoginError("Данные не верны")
                }
            }
        }
        observeRegisterCheck()
        setupNavigateBack()
    }

    private fun setupNavigateBack(){
        binding.icArrowBack.setSafeOnClickListener {
            findNavController().popBackStack()
        }
    }


    private fun observeRegisterCheck() {
        viewModel.registerCheck.observe(viewLifecycleOwner) { result ->
            when (result) {
                is RegisterResult.Error -> {
                    handleLoginError(result.error)
                }
                RegisterResult.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is RegisterResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    findNavController().navigate(R.id.action_registerPasswordFragment_to_homeFragment)
                }
            }
        }
    }

    private fun arePasswordsValid(password: String, confirmPassword: String): Boolean {
        val passwordRegex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}\$")
        return passwordRegex.matches(password) && password == confirmPassword
    }


    private fun handleLoginError(errorMessage: String) {
        binding.apply {
            progressBar.visibility = View.GONE

            mainPasswordEdit.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.main_red
                )
            )
            confirmPasswordEdit.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.main_red
                )
            )
        }
        snackBarSettings(errorMessage)
    }
    private fun snackBarSettings(errorMessage: String) {
        val snackbar = Snackbar.make(requireView(), errorMessage, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.main_red))

        val snackbarLayout: View = snackbar.view
        val params = snackbarLayout.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        snackbarLayout.layoutParams = params

        val textView: TextView =
            snackbarLayout.findViewById(com.google.android.material.R.id.snackbar_text)
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_warning, 0, 0, 0)
        textView.compoundDrawablePadding =
            resources.getDimensionPixelOffset(R.dimen.snackbar_icon_padding)

        snackbar.show()
    }
}*/
