package me.ruyeo.newcut.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import me.ruyeo.newcut.App
import me.ruyeo.newcut.LocaleManager
import me.ruyeo.newcut.R
import me.ruyeo.newcut.databinding.FragmentLanguageBinding
import me.ruyeo.newcut.databinding.FragmentLoginBinding
import me.ruyeo.newcut.ui.BaseFragment
import me.ruyeo.newcut.utils.extensions.viewBinding

class LanguageFragment : BaseFragment(R.layout.fragment_language) {
    private val binding by viewBinding { FragmentLanguageBinding.bind(it) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        openLoginFragment()

    }

    private fun openLoginFragment() {
        binding.apply {
            uzbTv.setOnClickListener {
                App.localeManager!!.setNewLocale(requireContext(),LocaleManager.LANGUAGE_UZBEK )
                openLogin()
            }

            rusTv.setOnClickListener {
                App.localeManager!!.setNewLocale(requireContext(),LocaleManager.LANGUAGE_RUSSIAN )
                openLogin()
            }

            engTv.setOnClickListener {
                App.localeManager!!.setNewLocale(requireContext(),LocaleManager.LANGUAGE_ENGLISH )
                openLogin()
            }
        }
    }

    private fun openLogin() {
        findNavController().navigate(R.id.action_languageFragment_to_loginFragment)
    }

}