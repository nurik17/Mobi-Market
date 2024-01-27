package com.example.mobimarket.presentation.login

import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mobimarket.R
import com.example.mobimarket.data.entity.StateResult
import com.example.mobimarket.databinding.FragmentLoginBinding
import com.example.mobimarket.utils.BaseFragment
import com.example.mobimarket.utils.setSafeOnClickListener
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()

    override fun onBindView() {
        super.onBindView()
        binding.btnLogin.setSafeOnClickListener {
            val userName = binding.userNameEdit.text.toString()
            val password = binding.passwordEdit.text.toString()
            viewModel.login(password, userName)
        }
        observeLogin()
        writeInfo()
        buttonState()
        navigateToRegister()
    }

    private fun navigateToRegister(){
        binding.tvRegistration.setSafeOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun observeLogin() {
        viewModel.loginResult.observe(viewLifecycleOwner) { loginResult ->
            when (loginResult) {
                is StateResult.Error -> {
                    handleLoginError(loginResult.error)
                }

                StateResult.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is StateResult.Success<*> -> {
                    binding.progressBar.visibility = View.GONE
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                }
            }
        }
    }

    private fun writeInfo() {
        binding.userNameEdit.addTextChangedListener(createTextWatcher {
            viewModel.updateButtonState(it, binding.passwordEdit.text.toString())
            clearErrorState()
        })

        binding.passwordEdit.addTextChangedListener(createTextWatcher{
            clearErrorState()
        })

    }

    private fun createTextWatcher(onTextChanged: (String) -> Unit = {}) = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChanged(s?.toString() ?: "")
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    private fun buttonState(){
        viewModel.isButtonEnabled.observe(this) { isEnabled ->
            binding.apply {
                btnLogin.isEnabled = isEnabled
                btnLogin.setBackgroundColor(
                    if (isEnabled) ContextCompat.getColor(requireContext(), R.color.maine_blue)
                    else ContextCompat.getColor(requireContext(), R.color.main_red)
                )
            }
        }
    }

    private fun handleLoginError(errorMessage: String) {
        binding.apply {
            progressBar.visibility = View.GONE
            btnLogin.isEnabled = false

            userNameEdit.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.main_red
                )
            )
            passwordEdit.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.main_red
                )
            )
        }
            snackBarSettings(errorMessage)
    }

    private fun snackBarSettings(errorMessage: String){
        val snackbar = Snackbar.make(requireView(), errorMessage, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.main_red))

        val snackbarLayout: View = snackbar.view
        val params = snackbarLayout.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        snackbarLayout.layoutParams = params

        val textView: TextView = snackbarLayout.findViewById(com.google.android.material.R.id.snackbar_text)
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_warning, 0, 0, 0,)
        textView.compoundDrawablePadding = resources.getDimensionPixelOffset(R.dimen.snackbar_icon_padding)

        snackbar.show()
    }

    private fun clearErrorState() {
        binding.userNameEdit.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        binding.passwordEdit.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
    }
}