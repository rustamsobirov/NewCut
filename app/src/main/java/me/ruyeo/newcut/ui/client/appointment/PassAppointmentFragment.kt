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
import me.ruyeo.newcut.adapter.appointment.PassAppointmentAdapter
import me.ruyeo.newcut.databinding.FragmentPassAppointmentBinding
import me.ruyeo.newcut.ui.BaseFragment
import me.ruyeo.newcut.utils.UiStateList
import me.ruyeo.newcut.utils.extensions.viewBinding
import javax.inject.Inject

@AndroidEntryPoint
class PassAppointmentFragment : BaseFragment(R.layout.fragment_pass_appointment) {
    private val binding by viewBinding { FragmentPassAppointmentBinding.bind(it) }
    private val adapter by lazy { PassAppointmentAdapter() }
    private val viewModel by viewModels<PassViewModel>()
    @Inject
    lateinit var sharedPref: SharedPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getPassedOrders(sharedPref.getUser().id)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObservers()
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.getPassedOrder.collect {
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

    private fun setupUI() {
        binding.apply {
            rvPass.adapter = adapter
        }
    }

}