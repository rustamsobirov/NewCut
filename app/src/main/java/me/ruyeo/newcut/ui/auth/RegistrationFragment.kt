package me.ruyeo.newcut.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import me.ruyeo.newcut.R
import me.ruyeo.newcut.databinding.FragmentRegistrationBinding
import me.ruyeo.newcut.ui.BaseFragment
import me.ruyeo.newcut.ui.client.MainActivity
import me.ruyeo.newcut.utils.extensions.viewBinding

@AndroidEntryPoint
class RegistrationFragment : BaseFragment(R.layout.fragment_registration) {

    private val binding by viewBinding { FragmentRegistrationBinding.bind(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showKeyboard(binding.nameEdt)
        sensorTohideKeyBoard()
        openToHomeFragment()

    }

    private fun openToHomeFragment() {
        binding.signUpBtn.setOnClickListener {
            showDialog()
            Intent(requireActivity(), MainActivity::class.java).also {
                startActivity(it)

            }
        }
    }

    private fun sensorTohideKeyBoard() {
        binding.linearLayout.setOnClickListener {
            hideKeyboard()
        }
    }

}