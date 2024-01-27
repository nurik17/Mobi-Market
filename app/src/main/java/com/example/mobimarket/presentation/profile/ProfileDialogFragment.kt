package com.example.mobimarket.presentation.profile

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mobimarket.R
import com.example.mobimarket.data.entity.StateResult
import com.example.mobimarket.databinding.DialogProfileLogoutBinding
import com.example.mobimarket.utils.setSafeOnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileDialogFragment : DialogFragment() {

    private var _binding : DialogProfileLogoutBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = DialogProfileLogoutBinding.inflate(inflater,container,false)
        observeLogoutResult()

        binding.btnNegativeButton.setSafeOnClickListener {
            dismiss()
        }
        binding.btnLogoutPositive.setSafeOnClickListener {
            viewModel.logout()
        }
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            setCanceledOnTouchOutside(true)
            window?.setBackgroundDrawableResource(R.drawable.corner_dialog_bg)
        }
    }

    private fun observeLogoutResult() {
        viewModel.logoutCase.observe(viewLifecycleOwner) { result ->
            when (result) {
                is StateResult.Loading -> {
                    Log.d("ProfileFragment", "observeLogoutResult: loading")
                }

                is StateResult.Success<*> -> {
                    findNavController().navigate(R.id.action_profileDialogFragment_to_loginFragment)
                    Log.d("ProfileFragment", "observeLogoutResult: success")
                }

                is StateResult.Error -> {
                    val errorMessage = result.error
                    Log.d("ProfileFragment", "$errorMessage observeLogoutResult: error")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}