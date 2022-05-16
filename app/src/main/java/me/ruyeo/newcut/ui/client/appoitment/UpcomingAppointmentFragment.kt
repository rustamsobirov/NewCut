package me.ruyeo.newcut.ui.client.appoitment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.ruyeo.newcut.R
import me.ruyeo.newcut.databinding.FragmentPassAppointmentBinding
import me.ruyeo.newcut.databinding.FragmentUpcomingAppointmentBinding
import me.ruyeo.newcut.utils.extensions.viewBinding


class UpcomingAppointmentFragment : Fragment() {
    private val binding by viewBinding { FragmentUpcomingAppointmentBinding.bind(it) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upcoming_appointment, container, false)
    }

}