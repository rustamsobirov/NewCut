package me.ruyeo.newcut.ui.client.appoitment

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import me.ruyeo.newcut.R
import me.ruyeo.newcut.databinding.FragmentHistoryAppointmentBinding
import me.ruyeo.newcut.ui.BaseFragment
import me.ruyeo.newcut.utils.extensions.viewBinding

@AndroidEntryPoint
class HistoryAppointmentFragment : BaseFragment(R.layout.fragment_history_appointment) {

    private val binding by viewBinding { FragmentHistoryAppointmentBinding.bind(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}