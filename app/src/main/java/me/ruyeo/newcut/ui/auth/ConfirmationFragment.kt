package me.ruyeo.newcut.ui.auth

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import me.ruyeo.newcut.R
import me.ruyeo.newcut.databinding.FragmentConfirmationBinding
import me.ruyeo.newcut.ui.BaseFragment
import me.ruyeo.newcut.utils.TextWatcherWrapper

@AndroidEntryPoint
class ConfirmationFragment : BaseFragment(R.layout.fragment_confirmation) {

    //    private val binding by viewBinding { FragmentConfirmationBinding.bind(it) }
    private var _binding: FragmentConfirmationBinding? = null
    private val binding get() = _binding!!

    private var sec = 10
    private var secondJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentConfirmationBinding.bind(view)
        showKeyboard(binding.ed1)
        perSecond()
        callBack()
        phoneNumberColor()
        inputSmsCodeManager()
        resendTextClickManager()


    }

    private fun resendTextClickManager() {
        binding.apply {
            tvResend.setOnClickListener {
                if (tvResend.text == getString(R.string.resend)) {
                    toaster("Resend")
                    callRequestCodeToServer()
                    perSecond()
                }
            }
        }
    }

    private fun callRequestCodeToServer() {

    }

    private fun inputSmsCodeManager() {
        binding.apply {
            //edit txt 1
            ed1.addTextChangedListener(textWatcherET1)
            ed1.setOnKeyListener { _, keyCode, _ ->
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    if (ed1.text.isEmpty()) {
                        ed1.setBackgroundResource(R.drawable.edtextbackground)
                    }
                }
                false
            }
            //edit txt 2
            ed2.addTextChangedListener(textWatcherET2)
            ed2.setOnKeyListener { _, keyCode, _ ->
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    if (ed2.text.isEmpty()) {
                        ed2.setBackgroundResource(R.drawable.edtextbackground)
                        ed1.requestFocus()
                    }
                }
                false
            }
            //edit txt 3
            ed3.addTextChangedListener(textWatcherET3)
            ed3.setOnKeyListener { _, keyCode, _ ->
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    if (ed3.text.isEmpty()) {
                        ed3.setBackgroundResource(R.drawable.edtextbackground)
                        ed2.requestFocus()
                    }
                }
                false
            }
            //edit txt 4
            ed4.addTextChangedListener(textWatcherET4)
            ed4.setOnKeyListener { _, keyCode, _ ->
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    if (ed4.text.isEmpty()) {
                        Log.d("@@@", "4 DEL")
                        ed4.setBackgroundResource(R.drawable.edtextbackground)
                        allEditTextClickableTrue()
                        ed3.requestFocus()
                    }
                }
                false
            }
        }
    }

    private fun checkAllEditToSendCodeServer(): Boolean {
        binding.apply {
            if (ed1.text.isNotEmpty() && ed2.text.isNotEmpty() && ed3.text.isNotEmpty() && ed4.text.isNotEmpty()) {
                return true
            }
        }
        return false
    }

    private fun allEditClearFocus() {
        binding.apply {
            ed1.clearFocus()
            ed2.clearFocus()
            ed3.clearFocus()
            ed4.clearFocus()
        }
    }

    private fun checkRequestServerCode(code: String) {
        Toast.makeText(context, "Server Send: $code", Toast.LENGTH_SHORT).show()
    }

    private fun allEditTextClickableFalse() {
        binding.apply {
            ed1.isEnabled = false
            ed2.isEnabled = false
            ed3.isEnabled = false
            ed4.isEnabled = false
        }
    }

    private fun allEditTextClickableTrue() {
        binding.apply {
            ed1.isClickable = true
            ed2.isClickable = true
            ed3.isClickable = true
            ed4.isClickable = true
        }
    }

    private val textWatcherET1 = object : TextWatcherWrapper() {
        override fun afterTextChanged(s: Editable) {
            super.afterTextChanged(s)
            if (s.length == 1) {
                binding.ed2.requestFocus()
                binding.ed1.setBackgroundResource(R.drawable.code_background)
                checkAllEditToSendCodeServer()
            }
        }
    }
    private val textWatcherET2 = object : TextWatcherWrapper() {
        override fun afterTextChanged(s: Editable) {
            super.afterTextChanged(s)
            if (s.length == 1) {
                binding.ed3.requestFocus()
                binding.ed2.setBackgroundResource(R.drawable.code_background)
                checkAllEditToSendCodeServer()
            }
        }
    }
    private val textWatcherET3 = object : TextWatcherWrapper() {
        override fun afterTextChanged(s: Editable) {
            super.afterTextChanged(s)
            if (s.length == 1) {
                binding.ed4.requestFocus()
                binding.ed3.setBackgroundResource(R.drawable.code_background)
                checkAllEditToSendCodeServer()
            }
        }
    }
    private val textWatcherET4 = object : TextWatcherWrapper() {
        override fun afterTextChanged(s: Editable) {
            super.afterTextChanged(s)
            if (s.length == 1) {
                binding.apply {
                    ed4.setBackgroundResource(R.drawable.code_background)
                    if (checkAllEditToSendCodeServer()) {
                        hideKeyboard()
                        allEditClearFocus()
                        allEditTextClickableFalse()
                        checkRequestServerCode(ed1.text.toString() + ed2.text.toString() + ed3.text.toString() + ed4.text.toString())
                        findNavController().navigate(R.id.action_confirmationFragment_to_registrationFragment)
                    }
                }
            }
        }
    }

    private fun phoneNumberColor() {
        binding.tvFourDigit.text = Html.fromHtml(
            "Please enter the 4 diget security code we just sent you at " + "<font color=${
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green_default
                )
            }>" + arguments?.getString(
                "phoneNumber"
            )
        )
    }

    private fun callBack() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun perSecond(): Job {
        if (sec == 0) sec = 10
        return MainScope().launch {
            while (isActive) {
                sec--
                val min = sec / 60
                var s = sec - min * 60
//                if (s < 10)
//                    binding.tvResend.text = "Resend in $min:0$s Sec"
//                else
                binding.tvResend.text = getString(R.string.resend_in) + "$min:$s Sec"
                if (sec == 0) {
                    binding.tvResend.text = getString(R.string.resend)
                    binding.tvResend.setTextColor(Color.parseColor("#ff02c65c"))
                    cancel()
                }
                delay(1000)
            }
        }
    }
}