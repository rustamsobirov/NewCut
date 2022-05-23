package me.ruyeo.newcut.ui.client.appointment

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import me.ruyeo.newcut.R
import me.ruyeo.newcut.databinding.FragmentUpcomingWithMapBinding
import me.ruyeo.newcut.ui.BaseFragment
import me.ruyeo.newcut.utils.extensions.viewBinding

@AndroidEntryPoint
class UpcomingFragmentWithMap : BaseFragment(R.layout.fragment_upcoming_with_map) {
    private val binding by viewBinding {FragmentUpcomingWithMapBinding.bind(it)}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}