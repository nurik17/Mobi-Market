package com.example.mobimarket.presentation.profile

import android.net.Uri
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.mobimarket.R
import com.example.mobimarket.databinding.FragmentProfileBinding
import com.example.mobimarket.utils.BaseFragment
import com.example.mobimarket.utils.setSafeOnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    override fun onBindView() {
        super.onBindView()

        val imageUriString = arguments?.getString("imageUri")

        if (!imageUriString.isNullOrEmpty()) {
            val imageUri = Uri.parse(imageUriString)

            val imageView = binding.imageProfilePage
            Glide.with(requireContext())
                .load(imageUri)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView)
        }
        updateProfileNavigate()
        navigateToLogoutDialog()
    }

    private fun navigateToLogoutDialog(){
        binding.tvLogout.setSafeOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_profileDialogFragment)
        }
    }
    private fun updateProfileNavigate() {
        binding.btnFinishRegistration.setSafeOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_profileUpdateFragment)
        }
    }


}