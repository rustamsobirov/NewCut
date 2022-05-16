package me.ruyeo.newcut.ui.client.detail.detail_in

import android.os.Bundle
import android.view.View
import me.ruyeo.newcut.R
import me.ruyeo.newcut.databinding.FragmentDetailReviewBinding
import me.ruyeo.newcut.ui.BaseFragment
import me.ruyeo.newcut.utils.extensions.viewBinding

class DetailReviewFragment : BaseFragment(R.layout.fragment_detail_review) {
    private val binding by viewBinding { FragmentDetailReviewBinding.bind(it) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}