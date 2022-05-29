package me.ruyeo.newcut.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import me.ruyeo.newcut.R
import me.ruyeo.newcut.databinding.FragmentLanguageBinding
import me.ruyeo.newcut.databinding.FragmentLoginBinding
import me.ruyeo.newcut.utils.extensions.viewBinding

class LanguageFragment : Fragment(R.layout.fragment_language) {
    private val binding by viewBinding { FragmentLanguageBinding.bind(it) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        openLoginFragment()

    }

    private fun openLoginFragment() {
        binding.apply {
            uzbTv.setOnClickListener {
                openLogin()
            }

            rusTv.setOnClickListener {
                openLogin()
            }

            engTv.setOnClickListener {
                openLogin()
            }
        }
    }

    private fun openLogin() {
        findNavController().navigate(R.id.action_languageFragment_to_loginFragment)
    }

}