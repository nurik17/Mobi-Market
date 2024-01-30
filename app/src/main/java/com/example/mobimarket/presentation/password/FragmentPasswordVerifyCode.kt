package com.example.mobimarket.presentation.password

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mobimarket.R
import com.example.mobimarket.data.model.ForgotPasswordResponse
import com.example.mobimarket.data.model.StateResult
import com.example.mobimarket.databinding.FragmentPasswordVerifyBinding
import com.example.mobimarket.utils.BaseFragment
import com.example.mobimarket.utils.setSafeOnClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class FragmentPasswordVerifyCode :
    BaseFragment<FragmentPasswordVerifyBinding>(FragmentPasswordVerifyBinding::inflate) {

    private val viewModel: PasswordVerifyCodeViewModel by viewModels()
    private var countDownJob: Job? = null

    private var timeRemainingMillis: Long = 0
    private var isTimerFinished = false
    private val forgotPasswordInfo: ForgotPasswordResponse? = null

    override fun onBindView() {
        super.onBindView()

        getCodeFromMessage()
        startCountdownTimer()
        requestAgain()
        observeVerifyCode()
    }

    private fun getCodeFromMessage() {
        binding.verifyCodeBtn.setSafeOnClickListener {
            val codeFromSms = binding.smsCode.text.toString()
            viewModel.verifyCode(codeFromSms, userId = 214)
        }
    }

    private fun requestAgain() {
        binding.againCode.setSafeOnClickListener {
            startCountdownTimer()
            binding.againCode.visibility = View.GONE
            binding.againCode.visibility = View.GONE
            binding.requestProgress.visibility = View.VISIBLE
            binding.requestProgressText.visibility = View.VISIBLE
            binding.tvErrorCode.visibility = View.GONE
        }
    }


    private fun observeVerifyCode() {
        viewModel.resetPasswordById.observe(viewLifecycleOwner) { result ->
            when (result) {
                StateResult.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.tvErrorCode.visibility = View.GONE
                }

                is StateResult.Success<*> -> {
                    binding.progressBar.visibility = View.GONE
                    binding.tvErrorCode.visibility = View.GONE
                    navigateToProfileUpdate()
                }

                is StateResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.tvErrorCode.visibility = View.VISIBLE
                  /*  Snackbar.make(requireView(), "Повторите запрос что-то пошло не так", 30)
                        .setBackgroundTint(ContextCompat.getColor(requireContext()R.color.main_red)
                        .show()*/
                    isTimerFinished = true
                }
            }
        }
    }

    private fun navigateToProfileUpdate() {
        findNavController().navigate(R.id.action_fragmentPasswordVerifyCode_to_fragmentChangePassword)
    }

    private fun startCountdownTimer() {
        //setting duration 1 minute in milliseconds
        isTimerFinished = false
        val countdownDurationMillis = TimeUnit.MINUTES.toMillis(1)

        countDownJob = viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            for (millisUntilFinished in countdownDurationMillis downTo 0 step 1000) {
                timeRemainingMillis = millisUntilFinished
                updateCountdownText()
                delay(1000)
            }
            isTimerFinished = true
            updateCountdownText()
        }
    }


    private fun updateCountdownText() {
        if (isTimerFinished) {
            binding.apply {
                againCode.visibility = View.VISIBLE
                requestProgress.visibility = View.GONE
                requestProgressText.visibility = View.GONE
                againRequest.visibility = View.GONE
            }
        } else {
            // Format the remaining time as "mm:ss"
            val minutes = TimeUnit.MILLISECONDS.toMinutes(timeRemainingMillis)
            val seconds = TimeUnit.MILLISECONDS.toSeconds(timeRemainingMillis) % 60
            val formattedTime = String.format("%02d:%02d", minutes, seconds)

            binding.requestProgressText.text = formattedTime
        }
    }


    override fun onDestroy() {
        countDownJob?.cancel()
        super.onDestroy()
    }

}
