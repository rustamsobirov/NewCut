package me.ruyeo.newcut.ui.client.appointment

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import me.ruyeo.newcut.R
import me.ruyeo.newcut.adapter.appointment.UpcomingAppointmentAdapter
import me.ruyeo.newcut.adapter.appointment.UpcomingAppointmentWithMapAdapter
import me.ruyeo.newcut.databinding.FragmentUpcomingAppointmentBinding
import me.ruyeo.newcut.model.appointment.AppointmentSharedViewModel
import me.ruyeo.newcut.model.appointment.UpcomingAppointment
import me.ruyeo.newcut.model.appointment.UpcomingAppointmentMapModel
import me.ruyeo.newcut.ui.BaseFragment
import me.ruyeo.newcut.utils.extensions.viewBinding

@AndroidEntryPoint
class UpcomingAppointmentFragment : BaseFragment(R.layout.fragment_upcoming_appointment) {
    private val binding by viewBinding { FragmentUpcomingAppointmentBinding.bind(it) }
    private val adapter by lazy { UpcomingAppointmentAdapter() }
    private val adapterWithMap by lazy { UpcomingAppointmentWithMapAdapter() }
    var upcomingList = ArrayList<UpcomingAppointment>()
    var mapUpcomingList = ArrayList<UpcomingAppointmentMapModel>()
    private val sharedViewModel by activityViewModels<AppointmentSharedViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpcomingRecyclerList()
        setUpcomingRecyclerMapList()

        sharedViewModel.getValue().observe(viewLifecycleOwner, Observer {

            binding.apply {
                if (!upcomingRecyclerView.isVisible){
                    upcomingRecyclerView.isVisible = true
                    upcomingWithMapRv.visibility = View.GONE
                }else{
                    upcomingRecyclerView.visibility = View.GONE
                    upcomingWithMapRv.visibility = View.VISIBLE
                }

            }
        })


    }

    private fun setUpcomingRecyclerList() {
        recyclerViewInstall()
        upcomingRecyclerList()
        adapterClickManager()
    }

    private fun setUpcomingRecyclerMapList() {
        mapRecyclerViewInstall()
        mapUpcomingRecyclerList()
    }

    private fun upcomingRecyclerList() {
        upcomingList.add(UpcomingAppointment("Salom", "toshkent", "Nimadur"))
        upcomingList.add(UpcomingAppointment("Salom", "toshkent", "Nimadur"))
        upcomingList.add(UpcomingAppointment("Salom", "toshkent", "Nimadur"))
        upcomingList.add(UpcomingAppointment("Salom", "toshkent", "Nimadur"))
        adapter.submitList(upcomingList)
    }

    private fun mapUpcomingRecyclerList() {
        mapUpcomingList.add(UpcomingAppointmentMapModel())
        mapUpcomingList.add(UpcomingAppointmentMapModel())
        mapUpcomingList.add(UpcomingAppointmentMapModel())
        adapterWithMap.submitList(mapUpcomingList)
    }


    private fun recyclerViewInstall() {
        binding.apply {
            upcomingRecyclerView.adapter = adapter
        }
    }

    private fun mapRecyclerViewInstall() {
        binding.apply {
            upcomingWithMapRv.adapter = adapterWithMap
        }
    }


    private fun adapterClickManager() {
        adapter.spinnerClick = {
            // Toast.makeText(requireContext(), "ok", Toast.LENGTH_SHORT).show()
        }
    }

}