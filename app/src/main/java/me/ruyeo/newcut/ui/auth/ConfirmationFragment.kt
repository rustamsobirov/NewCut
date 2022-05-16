package me.ruyeo.newcut.ui.auth

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
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

    private val EditTextOne: EditText by lazy {
        binding.ed1
    }
    private val EditTextTwo: EditText by lazy {
        binding.ed2
    }
    private val EditTextThree: EditText by lazy {
        binding.ed3
    }
    private val EditTextFour: EditText by lazy {
        binding.ed4
    }
    private val binding by viewBinding { FragmentConfirmationBinding.bind(it) }

    private var sec = 20
    private var secondJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        secondJob = perSecond()
        onBack()
        setListener()
        initFocus()

    }

    private fun onBack() {
        binding.ivBack.setOnClickListener {
            findNavController().navigate(R.id.action_confirmationFragment_to_loginFragment)
        }
    }
    private fun setListener() {

        setTextChengeListener(fromEditText = EditTextOne, targetEdittext = EditTextTwo)
        setTextChengeListener(fromEditText = EditTextTwo, targetEdittext = EditTextThree)
        setTextChengeListener(fromEditText = EditTextThree, targetEdittext = EditTextFour)
        setTextChengeListener(fromEditText = EditTextFour, done = {

            verifyOTPCode()
        })

        setKeyListener(fromEditText = EditTextTwo, backToEditText = EditTextOne)
        setKeyListener(fromEditText = EditTextThree, backToEditText = EditTextTwo)
        setKeyListener(fromEditText = EditTextFour, backToEditText = EditTextThree)
    }

    @SuppressLint("ServiceCast")
    private fun initFocus() {
        EditTextOne.isEnabled = true

        EditTextOne.postDelayed({
            EditTextOne.requestFocus()
        }, 500)
    }

    private fun setTextChengeListener(
        fromEditText: EditText,
        targetEdittext: EditText? = null,
        done: (() -> Unit)? = null
    ) {
        fromEditText.addTextChangedListener {
            it?.let { string ->
                if (string.isNotEmpty()) {
                    targetEdittext?.let { edittext ->
                        edittext.isEnabled = true
                        edittext.requestFocus()
                    } ?: run {
                        done?.let { done ->
                            done()
                        }

                    }

                    fromEditText.clearFocus()
                    fromEditText.isEnabled = false
                }
            }
        }

    }

    private fun setKeyListener(fromEditText: EditText, backToEditText: EditText) {
        fromEditText.setOnKeyListener { _, _, event ->
            if (null != event && KeyEvent.KEYCODE_DEL == event.keyCode) {
                backToEditText.isEnabled = true
                backToEditText.requestFocus()
                backToEditText.setText("")


                fromEditText.clearFocus()
                fromEditText.isEnabled = false
            }
            false

        }
    }

    private fun verifyOTPCode() {
        val otpCode =
            "${EditTextOne.text.toString().trim()}" +
                    "${EditTextTwo.text.toString().trim()}" +
                    "${EditTextThree.text.toString().trim()}" +
                    "${EditTextFour.text.toString().trim()}"

        if (4 != otpCode.length) {
            return
        }
        if (otpCode == TEST_VERIFY_CODE) {
            EditTextOne.setBackgroundResource(R.drawable.code_background)
            EditTextTwo.setBackgroundResource(R.drawable.code_background)
            EditTextThree.setBackgroundResource(R.drawable.code_background)
            EditTextFour.setBackgroundResource(R.drawable.code_background)

            Toast.makeText(context, "Success, should do next", Toast.LENGTH_SHORT).show()

            return
        }

        Toast.makeText(context, "error, input again", Toast.LENGTH_SHORT).show()

    }

    private fun perSecond(): Job {
        return MainScope().launch {
            while (isActive) {
                sec--
                val min = sec / 60
                val s = sec - min * 60
                if (s < 10)
                    binding.tvResend.text = "Resend in $min:0$s Sec"
                else
                    binding.tvResend.text = "Resend In $min:$s Sec"
                if (sec == 0) {
                    binding.tvResend.setText("Resend!")
                    binding.tvResend.setTextColor(Color.parseColor("#052F61"))
                    cancel()
                }
                delay(1000)
            }
        }
    }
}