package me.ruyeo.newcut.ui.client.profile

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import me.ruyeo.newcut.R
import me.ruyeo.newcut.databinding.FragmentEditProfileBinding
import me.ruyeo.newcut.ui.BaseFragment
import me.ruyeo.newcut.utils.extensions.viewBinding
import java.util.*

@AndroidEntryPoint
class EditProfileFragment : BaseFragment(R.layout.fragment_edit_profile),
    AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {
    private val binding by viewBinding { FragmentEditProfileBinding.bind(it) }


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        callBack()
        dataPickerDialog()
        sensorToKeyBoard()
        phoneEditTextManager()
        continueButtonManager()


    }

    private fun sensorToKeyBoard() {
        binding.linearProfile.setOnClickListener {
            hideKeyboard()
        }
    }


    private fun dataPickerDialog() {
        // Calendar
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        binding.apply {
            birthdayTv.setOnClickListener {
                val datePickerDialog = DatePickerDialog(
                    requireContext(),
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    this@EditProfileFragment,
                    year, month, day
                )
                datePickerDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                datePickerDialog.show()
            }
        }
    }

    private fun callBack() {
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }


    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val day = String.format("%02d", dayOfMonth)
        val mMonth = String.format("%02d", month)
        val date = "$day/$mMonth/$year"
        binding.birthdayTv.text = date

    }

    private fun continueButtonManager() {
        binding.phoneNumberEdt.apply {
            binding.apply {
                saveBtn.setOnClickListener {
                    when {
                        text!!.length > 17 -> {
                            findNavController().popBackStack()
                        }
                        else -> {
                            inputLayoutBoxEnable()
                        }
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