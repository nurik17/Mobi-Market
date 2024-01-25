package com.example.mobimarket.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.mobimarket.databinding.DialogProfileLogoutBinding
import com.example.mobimarket.utils.setSafeOnClickListener

class ProfileDialogFragment : DialogFragment() {


    private var _binding : DialogProfileLogoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogProfileLogoutBinding.inflate(inflater,container,false)

        binding.btnNegativeButton.setSafeOnClickListener {
            dismiss()
        }
        binding.btnLogoutPositive.setSafeOnClickListener {

        }

        return binding.root
    }


}