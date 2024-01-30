package com.example.mobimarket.presentation.verifyPhone

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mobimarket.R
import com.example.mobimarket.data.model.StateResult
import com.example.mobimarket.databinding.FragmentVerifyCodeBinding
import com.example.mobimarket.utils.BaseFragment
import com.example.mobimarket.utils.setSafeOnClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class FragmentVerifyCode :
    BaseFragment<FragmentVerifyCodeBinding>(FragmentVerifyCodeBinding::inflate) {

    private val viewModel: VerifyCodeViewModel by viewModels()
    private var countDownJob: Job? = null
    private var timeRemainingMillis: Long = 0
    private var isTimerFinished = false

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
            viewModel.verifyPhoneCode(code = codeFromSms)
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
        viewModel.verifyPhone.observe(viewLifecycleOwner) { result ->
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
                    isTimerFinished = true
                }
            }
        }
    }

    private fun navigateToProfileUpdate() {
        findNavController().navigate(R.id.action_fragmentVerifyCode_to_profileUpdateFragment)
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