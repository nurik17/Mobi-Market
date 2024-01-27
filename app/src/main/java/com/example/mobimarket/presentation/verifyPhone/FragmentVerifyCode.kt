package com.example.mobimarket.presentation.verifyPhone

import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mobimarket.R
import com.example.mobimarket.data.entity.StateResult
import com.example.mobimarket.databinding.FragmentVerifyCodeBinding
import com.example.mobimarket.utils.BaseFragment
import com.example.mobimarket.utils.setSafeOnClickListener
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class FragmentVerifyCode :
    BaseFragment<FragmentVerifyCodeBinding>(FragmentVerifyCodeBinding::inflate) {

    private val viewModel: FragmentVerifyCodeViewModel by viewModels()
    private var countDownTimer: CountDownTimer? = null
    private var timeRemainingMillis: Long = 0
    private var isTimerFinished = false

    override fun onBindView() {
        super.onBindView()
        getCodeFromMessage()
        startCountdownTimer()

        binding.againCode.setSafeOnClickListener {
            startCountdownTimer()
            binding.againCode.visibility = View.GONE
            binding.againCode.visibility = View.GONE
            binding.requestProgress.visibility = View.VISIBLE
            binding.requestProgressText.visibility = View.VISIBLE
            binding.tvErrorCode.visibility = View.GONE
        }
        observeVerifyCode()
    }

    private fun getCodeFromMessage(){
        binding.verifyCodeBtn.setSafeOnClickListener {
            val codeFromSms = binding.smsCode.text.toString()
            viewModel.verifyPhoneCode(code = codeFromSms)
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
        val countdownDurationMillis = TimeUnit.MINUTES.toMillis(60)

        object : CountDownTimer(countdownDurationMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeRemainingMillis = millisUntilFinished
                updateCountdownText()
            }

            override fun onFinish() {
                isTimerFinished = true
                updateCountdownText()
            }
        }.start()
    }

    private fun updateCountdownText() {
        if(isTimerFinished){
            binding.apply {
                againCode.visibility = View.VISIBLE
                requestProgress.visibility = View.GONE
                requestProgressText.visibility = View.GONE
                againRequest.visibility = View.GONE
            }
        }else {
            // Format the remaining time as "mm:ss"
            val minutes = TimeUnit.MILLISECONDS.toMinutes(timeRemainingMillis)
            val seconds = TimeUnit.MILLISECONDS.toSeconds(timeRemainingMillis) % 60
            val formattedTime = String.format("%02d:%02d", minutes, seconds)

            binding.requestProgressText.text = formattedTime
        }
    }

    override fun onDestroy() {
        countDownTimer?.cancel()
        super.onDestroy()
    }
}