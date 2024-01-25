package com.example.mobimarket.presentation.profile

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mobimarket.R
import com.example.mobimarket.data.entity.LogoutResult
import com.example.mobimarket.databinding.FragmentProfileBinding
import com.example.mobimarket.utils.BaseFragment
import com.example.mobimarket.utils.setSafeOnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel: ProfileViewModel by viewModels()

    override fun onBindView() {
        super.onBindView()
        updateProfileNavigate()

        binding.tvLogout.setSafeOnClickListener {
            viewModel.logout()
        }
        observeLogoutResult()
    }


    private fun updateProfileNavigate() {
        binding.btnFinishRegistration.setSafeOnClickListener {

        }
    }
    private fun observeLogoutResult() {
        viewModel.logoutCase.observe(viewLifecycleOwner) { result ->
            when (result) {
                is LogoutResult.Loading -> {
                    Log.d("ProfileFragment", "observeLogoutResult: loading")
                }

                is LogoutResult.Success -> {
                    findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
                    Log.d("ProfileFragment", "observeLogoutResult: success")
                }

                is LogoutResult.Error -> {
                    val errorMessage = result.error
                    Log.d("ProfileFragment", "$errorMessage observeLogoutResult: error")
                }
            }
        }
    }
}