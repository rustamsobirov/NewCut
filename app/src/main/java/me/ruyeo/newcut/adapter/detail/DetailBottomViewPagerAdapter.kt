package me.ruyeo.newcut.adapter.detail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import me.ruyeo.newcut.ui.client.detail.detail_in.DetailGalleryFragment
import me.ruyeo.newcut.ui.client.detail.detail_in.DetailReviewFragment
import me.ruyeo.newcut.ui.client.detail.detail_specialists.DetailPortfolioFragment

class DetailBottomViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                DetailGalleryFragment()
            }
            1 -> {
                DetailPortfolioFragment()
            }
            2 -> {
                DetailReviewFragment()
            }
            else -> Fragment()
        }
    }
}