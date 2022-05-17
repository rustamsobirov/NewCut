package me.ruyeo.newcut.ui.auth

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
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

        phoneEditTextManager()
        binding.continueBtn.setOnClickListener {
            if (binding.phoneNumberEdt.text!!.isNotEmpty()) {
                findNavController().navigate(
                    R.id.action_loginFragment_to_confirmationFragment,
                    bundleOf("phoneNumber" to binding.phoneNumberEdt.text.toString())
                )
            }
        }

    }

    private fun phoneEditTextManager() {
        binding.apply {
            phoneNumberEdt.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                @SuppressLint("SetTextI18n")
                override fun afterTextChanged(p0: Editable?) {
                    if (phoneNumberEdt.text.toString() == "+998") {
                        phoneNumberEdt.setText("+998(")
                        editLastCursor()
                    }
                }
            })
            phoneNumberEdt.onFocusChangeListener = object : View.OnFocusChangeListener {
                @SuppressLint("SetTextI18n")
                override fun onFocusChange(p0: View?, p1: Boolean) {
                    if (p1) {
                        phoneNumberEdt.setText("${phoneNumberEdt.text}+998(")
                    }
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
                    }

                    if (phoneNumberEdt.text?.get(phoneNumberEdt.text?.length!! - 1)
                            .toString() == ")"
                    ) {
                        phoneNumberEdt.setText(
                            phoneNumberEdt.text!!.substring(
                                0,
                                phoneNumberEdt.text!!.length - 2
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

    private fun editLastCursor() {
        binding.apply {
            phoneNumberEdt.setSelection(phoneNumberEdt.length())
        }
    }
}