package me.ruyeo.newcut.ui.auth

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import me.ruyeo.newcut.R
import me.ruyeo.newcut.databinding.FragmentConfirmationBinding
import me.ruyeo.newcut.ui.BaseFragment
import me.ruyeo.newcut.utils.extensions.viewBinding

@AndroidEntryPoint
class ConfirmationFragment : BaseFragment(R.layout.fragment_confirmation) {
    companion object {
        private const val TEST_VERIFY_CODE = "1234"
    }

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
            ed1.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun afterTextChanged(p0: Editable) {
                    if (p0.length == 1) {
                        ed2.requestFocus()
                        ed1.setBackgroundResource(R.drawable.code_background)
                        checkAllEditToSendCodeServer()
                    }
                }
            })
            ed1.setOnKeyListener { _, keyCode, _ ->
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    if (ed1.text.isEmpty()) {
                        ed1.setBackgroundResource(R.drawable.edtextbackground)
                    }
                }
                false
            }
            //edit txt 2
            ed2.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun afterTextChanged(p0: Editable?) {
                    if (p0!!.length == 1) {
                        ed3.requestFocus()
                        ed2.setBackgroundResource(R.drawable.code_background)
                        checkAllEditToSendCodeServer()
                    }
                }
            })
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
            ed3.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun afterTextChanged(p0: Editable?) {
                    if (p0!!.length == 1) {
                        ed4.requestFocus()
                        ed3.setBackgroundResource(R.drawable.code_background)
                        checkAllEditToSendCodeServer()
                    }
                }
            })
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
            ed4.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: Editable?) {
                    if (p0!!.length == 1) {
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
            })
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

    private fun phoneNumberColor() {
        binding.tvFourDigit.text = Html.fromHtml(
            "Please enter the 4 diget security code we just sent you at " + "<font color=#4F38E1>" + arguments?.getString(
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
                binding.tvResend.text = "Resend In $min:$s Sec"
                if (sec == 0) {
                    binding.tvResend.text = getString(R.string.resend)
                    binding.tvResend.setTextColor(Color.parseColor("#052F61"))
                    cancel()
                }
                delay(1000)
            }
        }
    }
}