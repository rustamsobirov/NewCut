package me.ruyeo.newcut.ui.client.appoitment

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import me.ruyeo.newcut.R
import me.ruyeo.newcut.adapter.PassAppointmentAdapter
import me.ruyeo.newcut.databinding.FragmentPassAppointmentBinding
import me.ruyeo.newcut.model.PassAppointmentModel
import me.ruyeo.newcut.ui.BaseFragment
import me.ruyeo.newcut.utils.extensions.viewBinding

@AndroidEntryPoint
class PassAppointmentFragment : BaseFragment(R.layout.fragment_pass_appointment) {

    private val binding by viewBinding { FragmentPassAppointmentBinding.bind(it) }
    private val adapter by lazy { PassAppointmentAdapter() }
    var passList = ArrayList<PassAppointmentModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewInstall()
        passRecyclerList()
        adapterClickManager()
    }

    private fun adapterClickManager() {

    }

    private fun passRecyclerList() {
        passList.add(PassAppointmentModel("Salom","toshkent","Nimadur"))
        passList.add(PassAppointmentModel("Salom","toshkent","Nimadur"))
        passList.add(PassAppointmentModel("Salom","toshkent","Nimadur"))
        passList.add(PassAppointmentModel("Salom","toshkent","Nimadur"))
        adapter.submitList(passList)
    }

    private fun recyclerViewInstall() {
        binding.apply {
            rvPass.adapter = adapter
        }
    }

}