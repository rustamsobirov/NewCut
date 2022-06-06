package me.ruyeo.newcut.ui.client.detail.detail_specialists

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import me.ruyeo.newcut.R
import me.ruyeo.newcut.adapter.detail.DetailBottomViewPagerAdapter
import me.ruyeo.newcut.databinding.FragmentDetailSpecialistBinding
import me.ruyeo.newcut.ui.BaseFragment
import me.ruyeo.newcut.utils.extensions.viewBinding

class DetailSpecialistFragment : BaseFragment(R.layout.fragment_detail_specialist) {
    private val binding by viewBinding { FragmentDetailSpecialistBinding.bind(it) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        installUserInfo()
        detailBottomViewPagerManager()
    }

    private fun installUserInfo() {
        binding.apply {
            backBtn.setOnClickListener {
                findNavController().popBackStack()
            }
            Glide.with(binding.root)
                .load("https://st2.depositphotos.com/2931363/9695/i/950/depositphotos_96952028-stock-photo-young-handsome-man-in-barbershop.jpg")
                .into(detailImageView)
            Glide.with(binding.root)
                .load("https://media.istockphoto.com/photos/shot-of-a-handsome-young-barber-standing-alone-in-his-salon-picture-id1365608023?b=1&k=20&m=1365608023&s=170667a&w=0&h=hkcbZSXI3AXG-a44bFrjdHFKF-XRwBGUbVx5NSkur3s=")
                .into(stylistImage)
        }
    }

    private fun detailBottomViewPagerManager() {
        binding.apply {
            val detailBottomViewPagerAdapter =
                DetailBottomViewPagerAdapter(childFragmentManager, lifecycle)
            detailBottomViewPager.adapter = detailBottomViewPagerAdapter

            detailBottomTabLayout.addTab(detailBottomTabLayout.newTab().setText("Gallery"))
            detailBottomTabLayout.addTab(detailBottomTabLayout.newTab().setText("Portfolio"))
            detailBottomTabLayout.addTab(detailBottomTabLayout.newTab().setText("Review"))

            detailBottomTabLayout.addOnTabSelectedListener(object :
                TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    detailBottomViewPager.currentItem = tab!!.position
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}

                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })

            detailBottomViewPager.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    detailBottomTabLayout.selectTab(detailBottomTabLayout.getTabAt(position))
                }
            })
        }
    }
}