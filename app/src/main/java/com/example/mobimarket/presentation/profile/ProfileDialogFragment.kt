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
import com.google.android.material.snackbar.Snackbar
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
                    binding.progressBar.visibility = View.VISIBLE
                }

                is StateResult.Success<*> -> {
                    binding.progressBar.visibility = View.GONE
                    Snackbar.make(requireView(),"Вы успешно вышди из приложения",30).show()
                    findNavController().navigate(R.id.action_profileDialogFragment_to_loginFragment)
                }

                is StateResult.Error -> {
                    Snackbar.make(requireView(),"Повторите что-то пошло не так",30).show()
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}