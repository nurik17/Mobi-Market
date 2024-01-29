package com.example.mobimarket.presentation.authorization

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
import com.example.mobimarket.databinding.FragmentRegisterBinding
import com.example.mobimarket.utils.BaseFragment
import com.example.mobimarket.utils.setSafeOnClickListener
import com.example.mobimarket.utils.showCustomSnackbar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val viewModel: RegisterViewModel by viewModels()

    override fun onBindView() {
        super.onBindView()
        setupClickListeners()
        observeRegisterCheck()
        navigateBack()

        arePasswordsValid(
            binding.passwordEdit.text.toString(),
            binding.confirmPasswordEdit.text.toString()
        )

    }

  /*  private fun arePasswordsValid(password: String, confirmPassword: String) {
        if (password != confirmPassword && password.length < 9) {
            snackBarSettings("Проверьте данные $password")
        }
    }*/


    private fun setupClickListeners() {
        binding.apply {
            binding.btnRegisterCheck.setSafeOnClickListener {
                val userName = userNameEdit.text.toString()
                val email = emailEdit.text.toString()
                val password = passwordEdit.text.toString()
                val confirmPassword = confirmPasswordEdit.text.toString()


                viewModel.registerCheck(userName, email, password, confirmPassword)
            }
        }

        writeInfo()
        buttonState()
    }

    private fun navigateBack() {
        binding.icArrowBack.setSafeOnClickListener {
            findNavController().popBackStack()
        }
    }

     private fun arePasswordsValid(password: String, confirmPassword: String): Boolean {
         val passwordRegex =
             Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}\$")
         return passwordRegex.matches(password) && password == confirmPassword
     }

    private fun observeRegisterCheck() {
        viewModel.registerCheck.observe(viewLifecycleOwner) { result ->
            binding.progressBar.visibility = View.GONE
            when (result) {
                is StateResult.Error -> handleLoginError(result.error)
                StateResult.Loading -> binding.progressBar.visibility = View.VISIBLE
                is StateResult.Success<*> -> {
                    Snackbar.make(requireView(),"Вы успешно зарегестрировались",30).show()
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                }
            }
        }
    }

    private fun buttonState() {
        viewModel.isButtonEnabled.observe(this) { isEnabled ->
            updateButtonState(isEnabled)
        }
    }

    private fun updateButtonState(isEnabled: Boolean) {
        binding.apply {
            btnRegisterCheck.isEnabled = isEnabled
            btnRegisterCheck.setBackgroundColor(
                if (isEnabled) ContextCompat.getColor(requireContext(), R.color.maine_blue)
                else ContextCompat.getColor(requireContext(), R.color.light_grey)
            )
        }
    }

    private fun writeInfo() {
        binding.userNameEdit.addTextChangedListener(createTextWatcher {
            viewModel.updateButtonStateRegister(it, binding.userNameEdit.text.toString())
            clearErrorState()
        })

        binding.passwordEdit.addTextChangedListener(createTextWatcher {
            viewModel.updateButtonStateRegister(it, binding.passwordEdit.text.toString())
            clearErrorState()
        })

        binding.emailEdit.addTextChangedListener(createTextWatcher {
            viewModel.updateButtonStateRegister(it, binding.emailEdit.text.toString())
            clearErrorState()
        })

        binding.confirmPasswordEdit.addTextChangedListener(createTextWatcher {
            viewModel.updateButtonStateRegister(it, binding.confirmPasswordEdit.text.toString())
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

    private fun clearErrorState() {
        binding.userNameEdit.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        binding.passwordEdit.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
    }

    private fun handleLoginError(errorMessage: String) {
        binding.apply {
            progressBar.visibility = View.GONE
            btnRegisterCheck.isEnabled = false

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
        requireView().showCustomSnackbar(errorMessage)
    }

}

