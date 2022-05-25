package me.ruyeo.newcut.ui.client.profile

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.icu.text.DateFormat.DAY
import android.icu.text.DateFormat.MONTH
import android.icu.text.DateTimePatternGenerator.DAY
import android.icu.util.MeasureUnit.DAY
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import dagger.hilt.android.AndroidEntryPoint
import me.ruyeo.newcut.R
import me.ruyeo.newcut.databinding.FragmentEditProfileBinding
import me.ruyeo.newcut.ui.BaseFragment
import me.ruyeo.newcut.utils.extensions.viewBinding
import java.util.*

@AndroidEntryPoint
class EditProfileFragment : BaseFragment(R.layout.fragment_edit_profile),
    AdapterView.OnItemSelectedListener {
    private val binding by viewBinding { FragmentEditProfileBinding.bind(it) }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Calendar
        val c= Calendar.getInstance()
        val year=c.get(Calendar.YEAR)
        val month=c.get(Calendar.MONTH)
        val day= c.get(Calendar.DAY_OF_MONTH)

        binding.apply {
            birthdayTv.setOnClickListener {
                val datePickerDialog = DatePickerDialog(requireContext(),
                    DatePickerDialog.OnDateSetListener{view,mYear,mMonth,mDay->
                        birthdayTv.text = "$mDay/$mMonth/$mYear"
                    },year,month,day)
                datePickerDialog.show()
            }
        }


        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(requireContext(),
            R.array.gender,R.layout.item_spinner_list)
        adapter.setDropDownViewResource(R.layout.item_spinner_list)
        binding.spinnerGender.adapter = adapter

        binding.spinnerGender.onItemSelectedListener = this

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }


}