package me.ruyeo.newcut.ui.auth

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.Html
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
        callBack()
        setListener()
        phoneNumberColor()


    }

    private fun phoneNumberColor() {
        binding.tvFourDigit.setText(
            Html.fromHtml(
                "Please enter the 4 diget security code we just sent you at " + "<font color=#4F38E1>" + arguments?.getString(
                    "phoneNumber"
                )
            )
        )
    }

    private fun callBack() {
        binding.ivBack.setOnClickListener {
            findNavController().navigate(R.id.action_confirmationFragment_to_loginFragment)
        }
    }


    @SuppressLint("ServiceCast")
    fun initFocus() {
        EditTextOne.isEnabled = true

        EditTextOne.postDelayed({
            EditTextOne.requestFocus()
        }, 1000)
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

    private fun setTextChengeListener(
        fromEditText: EditText,
        targetEdittext: EditText? = null,
        done: (() -> Unit)? = null
    ) {
        fromEditText.addTextChangedListener {
            it?.let { string ->
                if (string.isNotEmpty()) {
                    targetEdittext?.requestFocus() ?: run {
                        done?.let { done ->
                            done()
                        }
                    }
                }
            }
        }
    }

    private fun setKeyListener(fromEditText: EditText, backToEditText: EditText) {
        fromEditText.setOnKeyListener { _, _, event ->
            if (event.action == KeyEvent.ACTION_DOWN
                && event.keyCode == KeyEvent.KEYCODE_DEL
            ) {
                backToEditText.requestFocus()
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

            findNavController().navigate(R.id.action_confirmationFragment_to_registrationFragment)

            return
        } else {
            EditTextOne.setBackgroundResource(R.drawable.true_code_background)
            EditTextTwo.setBackgroundResource(R.drawable.true_code_background)
            EditTextThree.setBackgroundResource(R.drawable.true_code_background)
            EditTextFour.setBackgroundResource(R.drawable.true_code_background)
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

    override fun onDestroyView() {
        super.onDestroyView()
        secondJob?.cancel()
    }
}