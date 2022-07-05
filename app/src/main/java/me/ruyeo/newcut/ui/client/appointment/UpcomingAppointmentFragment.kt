package me.ruyeo.newcut.ui.client.appointment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import me.ruyeo.newcut.R
import me.ruyeo.newcut.SharedPref
import me.ruyeo.newcut.adapter.appointment.UpcomingAppointmentAdapter
import me.ruyeo.newcut.databinding.FragmentUpcomingAppointmentBinding
import me.ruyeo.newcut.ui.BaseFragment
import me.ruyeo.newcut.utils.UiStateList
import me.ruyeo.newcut.utils.extensions.viewBinding
import javax.inject.Inject

@AndroidEntryPoint
class UpcomingAppointmentFragment : BaseFragment(R.layout.fragment_upcoming_appointment) {
    private val binding by viewBinding { FragmentUpcomingAppointmentBinding.bind(it) }
    private val adapter by lazy { UpcomingAppointmentAdapter() }
    private val viewModel by viewModels<OrdersViewModel>()
    @Inject
    lateinit var sharedPref: SharedPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getOrders(sharedPref.getUser().id)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupObservers()
    }

    private fun setupUI() {
        binding.apply {
            upcomingRecyclerView.adapter = adapter
        }
        adapter.cancelClick = {
            showCancellationDialog()
        }
    }


    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.getOrderState.collect {
                    when(it){
                        is UiStateList.LOADING -> {
                            showProgress()
                        }
                        is UiStateList.SUCCESS -> {
                            hideProgress()
                            adapter.submitList(it.data)
                        }
                        is UiStateList.ERROR -> {
                            hideProgress()
                            showMessage(it.message)
                        }
                        else -> Unit
                    }
                }
            }
        }
    }

    private fun adapterClickManager() {
        adapter.spinnerClick = {
            // Toast.makeText(requireContext(), "ok", Toast.LENGTH_SHORT).show()
        }
    }
}