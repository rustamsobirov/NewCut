package me.ruyeo.newcut.ui.auth

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import me.ruyeo.newcut.R
import me.ruyeo.newcut.data.model.Login
import me.ruyeo.newcut.databinding.FragmentRegistrationBinding
import me.ruyeo.newcut.ui.BaseFragment
import me.ruyeo.newcut.ui.client.MainActivity
import me.ruyeo.newcut.utils.UiStateObject
import me.ruyeo.newcut.utils.extensions.viewBinding

@AndroidEntryPoint
class RegistrationFragment : BaseFragment(R.layout.fragment_registration) {

    private val binding by viewBinding { FragmentRegistrationBinding.bind(it) }
    private val viewModel by viewModels<RegisterViewModel>()
    private var phoneNumber = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            phoneNumber = it.getString("phoneNumber")!!
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showKeyboard(binding.nameEdt)
        sensorToHideKeyBoard()

        setupUI()
        setupObservers()

    }

    private fun setupUI() {
        binding.signUpBtn.setOnClickListener {
            viewModel.register(phoneNumber)
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.register.collect {
                when (it) {
                    is UiStateObject.LOADING -> {
                        showProgress()
                    }
                    is UiStateObject.SUCCESS -> {
                        hideProgress()
                        findNavController().navigate(R.id.action_registrationFragment_to_confirmationFragment, bundleOf("phoneNumber" to phoneNumber))
                        viewModel.reset()
                    }
                    is UiStateObject.ERROR -> {
                        hideProgress()
                        showMessage(it.message)
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun sensorToHideKeyBoard() {
        binding.linearLayout.setOnClickListener {
            hideKeyboard()
        }
    }

}