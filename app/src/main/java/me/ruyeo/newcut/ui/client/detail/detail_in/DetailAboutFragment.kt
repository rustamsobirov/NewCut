package me.ruyeo.newcut.ui.client.detail.detail_in

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import me.ruyeo.newcut.R
import me.ruyeo.newcut.databinding.FragmentDetailAboutBinding
import me.ruyeo.newcut.ui.BaseFragment
import me.ruyeo.newcut.utils.extensions.viewBinding


@AndroidEntryPoint
class DetailAboutFragment : BaseFragment(R.layout.fragment_detail_about) {
    private val binding by viewBinding { FragmentDetailAboutBinding.bind(it) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contactNumberManager()
        installMap()
    }

    private fun installMap() {
        childFragmentManager.beginTransaction().replace(R.id.frameLayout, DetailMapsFragment())
            .commit()
    }


    private fun contactNumberManager() {
        binding.apply {
            contactNumber.paintFlags = Paint.UNDERLINE_TEXT_FLAG
            contactNumber.setOnClickListener {
                val callIntent = Intent(Intent.ACTION_DIAL)
                callIntent.data =
                    Uri.parse("tel:" + Uri.encode(contactNumber.text.toString().trim()))
                callIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(callIntent)
            }
        }
    }
}