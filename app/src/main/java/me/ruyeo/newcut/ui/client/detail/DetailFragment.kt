package me.ruyeo.newcut.ui.client.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.ahmadhamwi.tabsync.TabbedListMediator
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayout
import me.ruyeo.newcut.R
import me.ruyeo.newcut.adapter.detail.DetailImageAdapter
import me.ruyeo.newcut.databinding.FragmentDetailBinding
import me.ruyeo.newcut.model.DetailModel
import me.ruyeo.newcut.ui.BaseFragment
import me.ruyeo.newcut.utils.extensions.viewBinding
import me.ruyeo.newcut.utils.extensions.visible


class DetailFragment : BaseFragment(R.layout.fragment_detail) {
    lateinit var detailImageAdapter: DetailImageAdapter
    var photosList = ArrayList<DetailModel>()
    private var bottomSheetBehavior: BottomSheetBehavior<*>? = null
    private val binding by viewBinding { FragmentDetailBinding.bind(it) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideStatusBarAndBottomBar()
        installRecyclerView()
        carouselRecyclerManager()
        tabLayoutManager()
        installBottomSheetBehavior(view)
        bottomSheetBehaviorManager()
    }

    private fun installBottomSheetBehavior(view: View) {
        val sheetLayout: ConstraintLayout = view.findViewById(R.id.sheetLayout)
        bottomSheetBehavior = BottomSheetBehavior.from(sheetLayout)
    }

    override fun onResume() {
        super.onResume()
        Handler(Looper.getMainLooper()).postDelayed({
            bottomSheetBehavior!!.peekHeight =
                getScreenHeight() - binding.detailRecyclerView.measuredHeight - 80
        }, 10)
    }


    private fun getScreenHeight(): Int {
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    private fun bottomSheetBehaviorManager() {
        bottomSheetBehavior?.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when {
                    newState == BottomSheetBehavior.STATE_HIDDEN -> {
                        bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
                    }
                    bottomSheetBehavior?.state == BottomSheetBehavior.STATE_EXPANDED -> {
                    }
                    bottomSheetBehavior?.state == BottomSheetBehavior.STATE_COLLAPSED -> {
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })
    }

    @SuppressLint("ResourceType")
    private fun tabLayoutManager() {
        binding.apply {
            if (photosList.size == 1) {
                tabLayout.visible(false)
            }

            for (category in 0 until photosList.size) {
                tabLayout.addTab(binding.tabLayout.newTab())
                allTabLayoutIndicatorUnselected()
            }
            tabLayout.getTabAt(0)?.setIcon(R.drawable.ic_circle_select)

            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    detailRecyclerView.smoothScrollToPosition(tab!!.position)
                    allTabLayoutIndicatorUnselected()
                    tabLayout.getTabAt(tab.position)?.setIcon(R.drawable.ic_circle_select)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}

                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })

            detailRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    val layoutManager =
                        LinearLayoutManager::class.java.cast(recyclerView.layoutManager)
                    val lastVisible = layoutManager!!.findLastVisibleItemPosition()
                    tabLayout.setScrollPosition(lastVisible, 0f, true)
                    allTabLayoutIndicatorUnselected()
                    tabLayout.getTabAt(lastVisible)?.setIcon(R.drawable.ic_circle_select)
                }
            })

            TabbedListMediator(
                detailRecyclerView,
                tabLayout,
                photosList.indices.toList(),
                true
            )
        }
    }

    private fun allTabLayoutIndicatorUnselected() {
        for (category in 0 until photosList.size) {
            binding.tabLayout.getTabAt(category)?.setIcon(R.drawable.ic_circle_unselect)
        }
    }

    private fun installRecyclerView() {
        binding.apply {
            detailImageAdapter = DetailImageAdapter(requireContext(), photosList)
            detailRecyclerView.adapter = detailImageAdapter
            PagerSnapHelper().attachToRecyclerView(detailRecyclerView)
        }
    }

    private fun carouselRecyclerManager() {
        photosList.add(DetailModel("https://firebasestorage.googleapis.com/v0/b/wallpapers-23e0e.appspot.com/o/Salon-image.png?alt=media&token=88fe9b51-1a16-442b-a994-bcdbf6b37559"))
        photosList.add(DetailModel("https://firebasestorage.googleapis.com/v0/b/wallpapers-23e0e.appspot.com/o/Cona_Mobile_512x512.png?alt=media&token=88054f45-ea0e-40d8-82d2-37b6ffb7ebef"))
        photosList.add(DetailModel("https://firebasestorage.googleapis.com/v0/b/wallpapers-23e0e.appspot.com/o/Cona_Mobile_512x512.png?alt=media&token=88054f45-ea0e-40d8-82d2-37b6ffb7ebef"))
        notifyAdapter()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun notifyAdapter() {
        detailImageAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        showStatusBarAndBottomBar()
    }
}