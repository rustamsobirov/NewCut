package me.ruyeo.newcut.ui.client.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import me.ruyeo.newcut.R
import me.ruyeo.newcut.databinding.FragmentHomeBinding
import me.ruyeo.newcut.ui.BaseFragment
import me.ruyeo.newcut.utils.extensions.viewBinding

@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val binding by viewBinding { FragmentHomeBinding.bind(it) }
    private val viewModel by viewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//test fun
//        detailFragmentOpener()
//        filterFragmentOpener()
        mapViewFragmentOpener()
        setupUI()
        setupObservers()
    }

    private fun mapViewFragmentOpener() {
//        binding.mapViewBtn.setOnClickListener {
        Handler(Looper.getMainLooper()).postDelayed({
        // Your Code
            findNavController().navigate(R.id.mapViewFragment)
        }, 3000)

//        }
    }

    private fun filterFragmentOpener() {
        findNavController().navigate(R.id.filterAndBookingBarberFragment)
    }

    private fun detailFragmentOpener() {
        findNavController().navigate(R.id.detailFragment)
    }

    private fun setupUI() {

    }

    private fun setupObservers() {

    }
}