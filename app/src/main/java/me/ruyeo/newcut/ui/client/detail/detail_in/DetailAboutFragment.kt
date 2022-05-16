package me.ruyeo.newcut.ui.client.detail.detail_in

import android.graphics.Paint
import android.os.Bundle
import android.view.View
import me.ruyeo.newcut.R
import me.ruyeo.newcut.databinding.FragmentDetailAboutBinding
import me.ruyeo.newcut.ui.BaseFragment
import me.ruyeo.newcut.utils.extensions.viewBinding

class DetailAboutFragment : BaseFragment(R.layout.fragment_detail_about) {
    private val binding by viewBinding { FragmentDetailAboutBinding.bind(it) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contactNumberManager()
    }

    private fun contactNumberManager() {
        binding.contactNumber.paintFlags = Paint.UNDERLINE_TEXT_FLAG
    }
}