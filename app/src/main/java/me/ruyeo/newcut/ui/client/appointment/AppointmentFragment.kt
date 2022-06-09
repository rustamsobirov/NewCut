package me.ruyeo.newcut.ui.client.appointment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import me.ruyeo.newcut.R
import me.ruyeo.newcut.adapter.appointment.AppointmentVPAdapter
import me.ruyeo.newcut.databinding.FragmentAppoitmentBinding
import me.ruyeo.newcut.ui.BaseFragment
import me.ruyeo.newcut.utils.extensions.viewBinding

@AndroidEntryPoint
class AppointmentFragment : BaseFragment(R.layout.fragment_appoitment) {
    private val binding by viewBinding { FragmentAppoitmentBinding.bind(it) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPagerAdapter = AppointmentVPAdapter(childFragmentManager, lifecycle)
        binding.apply {
            viewPager.adapter = viewPagerAdapter

            tabLayout.addTab(tabLayout.newTab().setText("Upcoming"))
            tabLayout.addTab(tabLayout.newTab().setText("Pass"))

            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    viewPager.currentItem = tab!!.position
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                }
            })

            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    tabLayout.selectTab(tabLayout.getTabAt(position))
                }
            })
        }

    }

}
