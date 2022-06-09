package me.ruyeo.newcut.ui.auth

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import me.ruyeo.newcut.R
import me.ruyeo.newcut.data.model.Login
import me.ruyeo.newcut.databinding.FragmentLoginBinding
import me.ruyeo.newcut.ui.BaseFragment
import me.ruyeo.newcut.utils.UiStateObject
import me.ruyeo.newcut.utils.extensions.getMyDrawable
import me.ruyeo.newcut.utils.extensions.viewBinding

@AndroidEntryPoint
class LoginFragment : BaseFragment(R.layout.fragment_login) {
    private val binding by viewBinding { FragmentLoginBinding.bind(it) }
    private val viewModel by viewModels<LoginViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        phoneEditTextManager()
        continueButtonManager()
        callBack()
        setupObservers()
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.login.collect {
                    when (it) {
                        is UiStateObject.LOADING -> {
                            toaster("show loading")
                        }
                        is UiStateObject.SUCCESS -> {
                            if (it.data.success) {
                                findNavController().navigate(
                                    R.id.action_loginFragment_to_confirmationFragment,
                                    bundleOf("phoneNumber" to binding.phoneNumberEdt.text.toString())
                                )
                                viewModel.reset()
                            } else {
                                findNavController().navigate(
                                    R.id.action_loginFragment_to_registrationFragment,
                                    bundleOf("phoneNumber" to binding.phoneNumberEdt.text.toString())
                                )
                                viewModel.reset()
                            }
                        }
                        is UiStateObject.ERROR -> {
                            showMessage(it.message)
                        }
                        else -> Unit
                    }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.login.collect {
                when (it) {
                    is UiStateObject.LOADING -> {
                        toaster("show loading")
                    }
                    is UiStateObject.SUCCESS -> {
                        if (it.data.success) {
                            findNavController().navigate(
                                R.id.action_loginFragment_to_confirmationFragment,
                                bundleOf("phoneNumber" to binding.phoneNumberEdt.text.toString().trim())
                            )
                        } else {
                            findNavController().navigate(
                                R.id.action_loginFragment_to_registrationFragment,
                                bundleOf("phoneNumber" to binding.phoneNumberEdt.text.toString().trim())
                            )
                        }
                    }
                    is UiStateObject.ERROR -> {
                        showMessage(it.message)
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun callBack() {
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun continueButtonManager() {
        binding.phoneNumberEdt.apply {
            binding.continueBtn.setOnClickListener {
                when {
                    text!!.length > 17 -> {
                        inputLayoutBoxDisable()
                        viewModel.login(text.toString())
                    }
                    else -> {
                        inputLayoutBoxEnable()
                    }
                }
            }
        }
    }

    private fun inputLayoutBoxEnable() {
        binding.textInputLayout.boxStrokeColor =
            ContextCompat.getColor(requireContext(), R.color.red_500)
    }

    private fun inputLayoutBoxDisable() {
        binding.textInputLayout.boxStrokeColor =
            ContextCompat.getColor(requireContext(), R.color.green_default)
    }

    @SuppressLint("SetTextI18n")
    private fun phoneEditTextManager() {
        binding.apply {
            phoneNumberEdt.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                @SuppressLint("SetTextI18n")
                override fun afterTextChanged(p0: Editable?) {
                    inputLayoutBoxDisable()
                    if (!phoneNumberEdt.text!!.contains("+998(") ||
                        phoneNumberEdt.text!![0].toString() != "+"
                    ) {
                        phoneNumberEdt.setText("+998(")
                        editLastCursor()
                    }
                    if (phoneNumberEdt.text!!.length < 5) {
                        phoneNumberEdt.setText("+998(")
                        editLastCursor()
                    }

                    if (phoneNumberEdt.text!!.length == 18) {
                        hideKeyboard()
                    }
                }
            })
            phoneNumberEdt.onFocusChangeListener = View.OnFocusChangeListener { _, p1 ->
                if (p1) {
                    if (phoneNumberEdt.text!!.isEmpty())
                        phoneNumberEdt.setText("${phoneNumberEdt.text}+998(")
                    editLastCursor()
                }
            }

            phoneNumberEdt.setOnKeyListener { _, keyCode, _ ->
                if (keyCode == KeyEvent.KEYCODE_DEL) {

                    if (phoneNumberEdt.text!![phoneNumberEdt.text!!.length - 1].toString() == " ") {
                        phoneNumberEdt.setText(
                            phoneNumberEdt.text!!.substring(
                                0,
                                phoneNumberEdt.text!!.length - 1
                            )
                        )
                        editLastCursor()
                    } else if (phoneNumberEdt.text?.get(phoneNumberEdt.text?.length!! - 1)
                            .toString() == ")"
                    ) {
                        phoneNumberEdt.setText(
                            phoneNumberEdt.text!!.substring(
                                0,
                                phoneNumberEdt.text!!.length - 1
                            )
                        )
                        editLastCursor()
                    }

                } else {
                    if (phoneNumberEdt.text!!.length == 7) {
                        phoneNumberEdt.setText("${phoneNumberEdt.text})")
                        editLastCursor()
                    }

                    if (phoneNumberEdt.text!!.length == 8) {
                        phoneNumberEdt.setText("${phoneNumberEdt.text} ")
                        editLastCursor()
                    }
                    if (phoneNumberEdt.text!!.length == 12) {
                        phoneNumberEdt.setText("${phoneNumberEdt.text} ")
                        editLastCursor()
                    }
                    if (phoneNumberEdt.text!!.length == 15) {
                        phoneNumberEdt.setText("${phoneNumberEdt.text} ")
                        editLastCursor()
                    }
                }
                false
            }
        }
    }

    override fun onStart() {
        super.onStart()
        inputLayoutBoxDisable()
    }

    private fun editLastCursor() {
        binding.apply {
            phoneNumberEdt.setSelection(phoneNumberEdt.length())
        }
    }
}