package me.ruyeo.newcut.ui.client.detail

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.ahmadhamwi.tabsync.TabbedListMediator
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import me.ruyeo.newcut.R
import me.ruyeo.newcut.adapter.detail.DetailBottomSalonSpecialistAdapter
import me.ruyeo.newcut.adapter.detail.DetailBottomViewPagerAdapter
import me.ruyeo.newcut.adapter.detail.DetailImageAdapter
import me.ruyeo.newcut.databinding.FragmentDetailBinding
import me.ruyeo.newcut.model.detail.DetailModel
import me.ruyeo.newcut.model.detail.DetailSpecialistModel
import me.ruyeo.newcut.ui.BaseFragment
import me.ruyeo.newcut.utils.UiStateList
import me.ruyeo.newcut.utils.UiStateObject
import me.ruyeo.newcut.utils.extensions.viewBinding
import me.ruyeo.newcut.utils.extensions.visible

@AndroidEntryPoint
class DetailFragment : BaseFragment(R.layout.fragment_detail) {
    private val detailImageAdapter by lazy { DetailImageAdapter() }
    private val detailBottomSalonSpecialistAdapter by lazy { DetailBottomSalonSpecialistAdapter() }
    private var photosList = ArrayList<DetailModel>()
    private val binding by viewBinding { FragmentDetailBinding.bind(it) }
    private val viewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            viewModel.getBarbershopById(it.getInt("barbershopId"))
            viewModel.getBarbers(it.getInt("barbershopId"))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        installRecyclerView()
        carouselRecyclerManager()
        tabLayoutManager()
        detailBottomViewPagerManager()

        setupUI()
        setupObservers()
        setupGetBarbers()
    }

    private fun setupGetBarbers() {
        viewLifecycleOwner.lifecycleScope.launch{
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.getBarberState.collect {
                    when(it){
                        is UiStateList.LOADING -> {
                            showProgress()
                        }
                        is UiStateList.SUCCESS -> {
                            hideProgress()
                            detailBottomSalonSpecialistAdapter.submitList(it.data)
                        }
                        is UiStateList.ERROR -> {
                            hideProgress()
                            showMessage(it.message)
                        }
                        else -> Unit
                    }
                }
            }
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.barbershopState.collect {
                    when (it) {
                        is UiStateObject.LOADING -> {
                            showProgress()
                        }
                        is UiStateObject.SUCCESS -> {
                            hideProgress()
                            binding.apply {
                                barbershopName.text = it.data.name
                                addressTv.text = it.data.address
                                if (it.data.isClosed) {
                                    isOpenTv.text = "Yopiq"
                                } else {
                                    isOpenTv.text = "Ochiq"
                                }
                                ratingBar.rating = it.data.rating
                            }
                        }
                        is UiStateObject.ERROR -> {
                            hideProgress()
                            showMessage(it.message)
                        }
                        else -> Unit
                    }
                }
            }
        }
    }

    private fun setupUI() {
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
    }

    private fun detailBottomViewPagerManager() {
        binding.apply {
            val detailBottomViewPagerAdapter =
                DetailBottomViewPagerAdapter(childFragmentManager, lifecycle)
            detailBottomViewPager.adapter = detailBottomViewPagerAdapter

            detailBottomTabLayout.addTab(detailBottomTabLayout.newTab().setText("About"))
            detailBottomTabLayout.addTab(detailBottomTabLayout.newTab().setText("Gallery"))
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

    @SuppressLint("ResourceType")
    private fun tabLayoutManager() {
        binding.apply {
            if (photosList.size == 1) {
                tabLayout.visible(false)
            }

            for (category in 0 until photosList.size) {
                tabLayout.addTab(binding.tabLayout.newTab())
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1)
                    allTabLayoutIndicatorUnselected()
                else tabLayout.getTabAt(category)?.text = "⬤"
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1)
                tabLayout.getTabAt(0)?.setIcon(R.drawable.ic_circle_select)

            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    detailRecyclerView.smoothScrollToPosition(tab!!.position)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1)
                        allTabLayoutIndicatorUnselected()
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1)
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
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1)
                        allTabLayoutIndicatorUnselected()
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1)
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
            detailRecyclerView.adapter = detailImageAdapter
            PagerSnapHelper().attachToRecyclerView(detailRecyclerView)
        }
    }

    private fun carouselRecyclerManager() {
        photosList.add(DetailModel("https://firebasestorage.googleapis.com/v0/b/wallpapers-23e0e.appspot.com/o/Salon-image.png?alt=media&token=88fe9b51-1a16-442b-a994-bcdbf6b37559"))
        photosList.add(DetailModel("https://firebasestorage.googleapis.com/v0/b/wallpapers-23e0e.appspot.com/o/Cona_Mobile_512x512.png?alt=media&token=88054f45-ea0e-40d8-82d2-37b6ffb7ebef"))
        photosList.add(DetailModel("https://firebasestorage.googleapis.com/v0/b/wallpapers-23e0e.appspot.com/o/Cona_Mobile_512x512.png?alt=media&token=88054f45-ea0e-40d8-82d2-37b6ffb7ebef"))
        detailImageAdapter.submitList(photosList)
    }
}