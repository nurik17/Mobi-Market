package com.example.mobimarket.presentation.profile

import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.mobimarket.R
import com.example.mobimarket.data.model.StateResult
import com.example.mobimarket.data.model.UserInfoResult
import com.example.mobimarket.databinding.FragmentProfileUpdateBinding
import com.example.mobimarket.domain.User
import com.example.mobimarket.utils.BaseFragment
import com.example.mobimarket.utils.setSafeOnClickListener
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.Executor

@AndroidEntryPoint
class ProfileUpdateFragment :
    BaseFragment<FragmentProfileUpdateBinding>(FragmentProfileUpdateBinding::inflate) {

    private val viewModel: ProfileUpdateViewModel by viewModels()

    private lateinit var cameraExecutor: Executor
    private var selectedImageUri: Uri? = null

    private val galleryLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                putImage(uri)
                Log.d("ProfileUpdateFragment", "$uri")
                selectedImageUri = uri
            }
        }

    override fun onBindView() {
        super.onBindView()
        cameraExecutor = ContextCompat.getMainExecutor(requireContext())

        val codeFromSms = arguments?.getString("codeFromSms")

        Log.d("ProfileUpdateFragment", "Verification Code: $codeFromSms")

        binding.chooseProfileImage.setSafeOnClickListener {
            openGallery()
        }

        binding.tvChange.setSafeOnClickListener {
            fillUserInfo()
        }
        observeUserInfo()
        observeUpdateInfo()

        binding.tvAddNumber.setSafeOnClickListener {
            findNavController().navigate(R.id.action_profileUpdateFragment_to_fragmentWritePhone)
        }
    }

    private fun putImage(uri: Uri?) {
        Glide.with(requireContext())
            .load(uri)
            .into(binding.imageProfilePage)
    }

    private fun fillUserInfo() {
        binding.apply {
            val name = nameEdit.text.toString()
            val lastName = surnameEdit.text.toString()
            val userName = usernameEdit.text.toString()
            val birthDate = birthdateEdit.text.toString()
            val email = emailEdit.text.toString()

            if(name.isNotEmpty() && lastName.isNotEmpty() && userName.isNotEmpty() && email.isNotEmpty()){
                viewModel.getUpdateInfo(
                    name, lastName, userName,
                    null, birthDate, email
                )
            }else{
                Toast.makeText(requireContext(),"Надо заполнить поля корректно",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun openGallery() {
        galleryLauncher.launch("image/*")
    }

    private fun galleryNavigation(imageUri: Uri) {
        selectedImageUri = imageUri
    }

    private fun observeUpdateInfo() {
        viewModel.updateInfo.observe(viewLifecycleOwner) { result ->
            when (result) {
                is StateResult.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    Log.d("ProfileUpdateFragment", "observeUpdateInfo: loading")
                }

                is StateResult.Success<*> -> {
                    binding.progressBar.visibility = View.GONE
                    findNavController().navigate(R.id.action_profileUpdateFragment_to_profileFragment)
                    Log.d("ProfileUpdateFragment", "observeUpdateInfo: success")
                }

                is StateResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun observeUserInfo() {
        viewModel.userInfo.observe(viewLifecycleOwner) { result ->
            when (result) {
                is UserInfoResult.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is UserInfoResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val user = result.data
                    populateEditTextFields(user)
                }

                is UserInfoResult.Error -> {
                    Toast.makeText(
                        requireContext(),
                        "Надо заполнить все поля в данном экране",
                        Toast.LENGTH_LONG
                    ).show()
                    val errorMessage = result.error
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun populateEditTextFields(user: User?) {
        user?.let {
            binding.nameEdit.setText(it.first_name)
            binding.surnameEdit.setText(it.last_name)
            binding.usernameEdit.setText(it.username)
            binding.birthdateEdit.setText(it.birth_date?.toString() ?: "")
            binding.tvNumber.text = it.phone
            binding.emailEdit.text = it.email
        }
    }
}
