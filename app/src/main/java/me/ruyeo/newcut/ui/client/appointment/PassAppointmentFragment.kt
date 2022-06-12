package me.ruyeo.newcut.ui.client.appointment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import me.ruyeo.newcut.R
import me.ruyeo.newcut.adapter.appointment.PassAppointmentAdapter
import me.ruyeo.newcut.databinding.FragmentPassAppointmentBinding
import me.ruyeo.newcut.model.appointment.PassAppointmentModel
import me.ruyeo.newcut.ui.BaseFragment
import me.ruyeo.newcut.utils.extensions.viewBinding

@AndroidEntryPoint
class PassAppointmentFragment : BaseFragment(R.layout.fragment_pass_appointment) {

    private val binding by viewBinding { FragmentPassAppointmentBinding.bind(it) }
    private val adapter by lazy { PassAppointmentAdapter() }
    private val viewModel by viewModels<OrdersViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        binding.apply {
            rvPass.adapter = adapter
        }
    }

}