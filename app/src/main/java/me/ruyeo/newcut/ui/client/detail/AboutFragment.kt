package me.ruyeo.newcut.ui.client.detail

import android.os.Bundle
import android.view.View
import me.ruyeo.newcut.R
import me.ruyeo.newcut.databinding.FragmentAboutBinding
import me.ruyeo.newcut.ui.BaseFragment
import me.ruyeo.newcut.utils.extensions.viewBinding

class AboutFragment : BaseFragment(R.layout.fragment_about) {

    private val binding by viewBinding { FragmentAboutBinding.bind(it) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}