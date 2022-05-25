package me.ruyeo.newcut.ui.client.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import me.ruyeo.newcut.R
import me.ruyeo.newcut.databinding.FragmentProfileBinding
import me.ruyeo.newcut.ui.BaseFragment
import me.ruyeo.newcut.utils.extensions.viewBinding

@AndroidEntryPoint
class ProfileFragment : BaseFragment(R.layout.fragment_profile) {
    private val binding by viewBinding { FragmentProfileBinding.bind(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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




}