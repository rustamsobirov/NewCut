package me.ruyeo.newcut.ui.client.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import me.ruyeo.newcut.R
import me.ruyeo.newcut.databinding.FragmentProfileBinding
import me.ruyeo.newcut.ui.BaseFragment
import me.ruyeo.newcut.utils.UiStateList
import me.ruyeo.newcut.utils.UiStateObject
import me.ruyeo.newcut.utils.extensions.viewBinding

@AndroidEntryPoint
class ProfileFragment : BaseFragment(R.layout.fragment_profile) {
    private val binding by viewBinding { FragmentProfileBinding.bind(it) }
    private val viewModel by viewModels<ProfileViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getAboutMe(0)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        profileFragmentsManager()
        setupObservers()
    }

    private fun profileFragmentsManager() {
        binding.apply {
            FAQsTv.setOnClickListener {
                findNavController().navigate(R.id.action_profileFragment_to_FAQsFragment)
            }
            notifIv.setOnClickListener {
                findNavController().navigate(R.id.action_profileFragment_to_notificationFragment)
            }
            editFl.setOnClickListener {
                findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
            }
            favoriteIv.setOnClickListener {
                findNavController().navigate(R.id.action_profileFragment_to_favoriteFragment)
            }
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.mainProfile.collect {
                    when (it) {
                        is UiStateObject.LOADING -> {
                            toaster("Show progress")
                        }
                        is UiStateObject.SUCCESS -> {
                           binding.fullname.text =it.data.fullName
                            binding.phoneNumberTv.text = it.data.phoneNumber
                        }
                        is UiStateObject.ERROR -> {
                            showMessage(it.message)
                        }
                        else -> Unit
                    }
                }
            }
        }
    }
}