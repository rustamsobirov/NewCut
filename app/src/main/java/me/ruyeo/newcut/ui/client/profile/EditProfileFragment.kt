package me.ruyeo.newcut.ui.client.profile

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
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

        binding.genderEdt.text = "Jins tanlash"
        callBack()
        dataPickerDialog()
        showRadioConfirmationDialog()
        sensorToKeyBoard()


    }

    private fun sensorToKeyBoard() {
        binding.linearProfile.setOnClickListener {
            hideKeyboard()
        }
    }


    private fun showRadioConfirmationDialog() {
        binding.genderEdt.setOnClickListener {
            showRadioDialog()
        }

        radioConfirmationDialog.manClick().setOnClickListener {
            hideRadioDialog()
            binding.genderEdt.text = "Erkak"
        }
        radioConfirmationDialog.womenClick().setOnClickListener {
            hideRadioDialog()
            binding.genderEdt.text = "Ayol"
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


}