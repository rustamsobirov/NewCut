package me.ruyeo.newcut.ui.auth

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import me.ruyeo.newcut.R
import me.ruyeo.newcut.databinding.FragmentLoginBinding
import me.ruyeo.newcut.ui.BaseFragment
import me.ruyeo.newcut.utils.extensions.viewBinding

@AndroidEntryPoint
class LoginFragment : BaseFragment(R.layout.fragment_login) {
    private val binding by viewBinding { FragmentLoginBinding.bind(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.continueBtn.setOnClickListener {
            if (binding.phoneNumberEdt.text.isNotEmpty()){
                findNavController().navigate(R.id.action_loginFragment_to_confirmationFragment,
                    bundleOf("phoneNumber" to binding.phoneNumberEdt.text.toString()))
            }
        }

    }


}