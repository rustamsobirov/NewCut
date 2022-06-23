package me.ruyeo.newcut.ui.barber.appointment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import me.ruyeo.newcut.R
import me.ruyeo.newcut.databinding.FragmentHomeAcceptedBinding
import me.ruyeo.newcut.ui.BaseFragment
import me.ruyeo.newcut.utils.extensions.viewBinding

@AndroidEntryPoint
class HomeAcceptedFragment : BaseFragment(R.layout.fragment_home_accepted) {
    private val binding by viewBinding { FragmentHomeAcceptedBinding.bind(it) }
    private val viewModel by viewModels<HomeAcceptedViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }



    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

            }
        }
    }



}