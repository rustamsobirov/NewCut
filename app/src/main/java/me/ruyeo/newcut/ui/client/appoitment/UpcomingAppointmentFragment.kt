package me.ruyeo.newcut.ui.client.appoitment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import dagger.hilt.android.AndroidEntryPoint
import me.ruyeo.newcut.R
import me.ruyeo.newcut.adapter.UpcomingAppointmentAdapter
import me.ruyeo.newcut.databinding.FragmentPassAppointmentBinding
import me.ruyeo.newcut.databinding.FragmentUpcomingAppointmentBinding
import me.ruyeo.newcut.model.UpcomingAppointment
import me.ruyeo.newcut.ui.BaseFragment
import me.ruyeo.newcut.utils.extensions.viewBinding

@AndroidEntryPoint
class UpcomingAppointmentFragment : BaseFragment(R.layout.fragment_upcoming_appointment) {
    private val binding by viewBinding { FragmentUpcomingAppointmentBinding.bind(it) }
    private val adapter by lazy { UpcomingAppointmentAdapter() }
    var upcomingList = ArrayList<UpcomingAppointment>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewInstall()
        upcomingRecyclerList()
        adapterClickManager()
    }

    private fun upcomingRecyclerList() {
        upcomingList.add(UpcomingAppointment("Salom","toshkent","Nimadur"))
        upcomingList.add(UpcomingAppointment("Salom","toshkent","Nimadur"))
        upcomingList.add(UpcomingAppointment("Salom","toshkent","Nimadur"))
        upcomingList.add(UpcomingAppointment("Salom","toshkent","Nimadur"))
        adapter.submitList(upcomingList)
    }

    private fun recyclerViewInstall() {
        binding.apply {
            upcomingRecyclerView.adapter = adapter
        }
    }

    private fun adapterClickManager() {
        adapter.spinnerClick = {
            Toast.makeText(requireContext(), "ok", Toast.LENGTH_SHORT).show()
        }
    }

}