package me.ruyeo.newcut.ui.client.profile


import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import me.ruyeo.newcut.R
import me.ruyeo.newcut.databinding.FragmentFaqsBinding
import me.ruyeo.newcut.ui.BaseFragment
import me.ruyeo.newcut.utils.extensions.viewBinding

@AndroidEntryPoint
class FAQsFragment : BaseFragment(R.layout.fragment_faqs) {

    private val binding by viewBinding { FragmentFaqsBinding.bind(it) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}