package me.ruyeo.newcut.ui.client.home.mapview

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.PagerSnapHelper
import com.google.android.material.bottomsheet.BottomSheetBehavior
import me.ruyeo.newcut.R
import me.ruyeo.newcut.adapter.MapBarberShopAdapter
import me.ruyeo.newcut.databinding.FragmentMapViewBinding
import me.ruyeo.newcut.model.map.MapBarberShopModel
import me.ruyeo.newcut.ui.BaseFragment
import me.ruyeo.newcut.ui.client.home.HomeViewModel
import me.ruyeo.newcut.utils.extensions.viewBinding
import me.ruyeo.newcut.utils.keyboard.KeyboardVisibilityEvent
import me.ruyeo.newcut.utils.keyboard.KeyboardVisibilityEventListener

class MapViewFragment : BaseFragment(R.layout.fragment_map_view) {
    private val binding by viewBinding { FragmentMapViewBinding.bind(it) }
    private val viewModel by viewModels<HomeViewModel>()
    private val barberShopAdapter by lazy { MapBarberShopAdapter() }
    var mapBarberList = ArrayList<MapBarberShopModel>()

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playerSheetInstall(view)
        installMap()
        hideStatusBarAndBottomBar()
        collapseManager()
        searchButtonManager()
        keyboardChangeListener()
        barberShopRecyclerItem()
    }

    private fun installMap() {
        childFragmentManager.beginTransaction().replace(R.id.frameLayout, MapFragment())
            .commit()
    }

    private fun barberShopRecyclerItem() {
        binding.apply {
            barberShopRecyclerView.adapter = barberShopAdapter
            PagerSnapHelper().attachToRecyclerView(barberShopRecyclerView)
            mapBarberList.add(MapBarberShopModel("https://firebasestorage.googleapis.com/v0/b/wallpapers-23e0e.appspot.com/o/Salon-image.png?alt=media&token=88fe9b51-1a16-442b-a994-bcdbf6b37559",
                "Sartaroshxona",
                "chorsu",
                4,
                "25 km"))
            mapBarberList.add(MapBarberShopModel("https://beautyhealthtips.in/wp-content/uploads/2019/02/Quiff-Haircuts.jpg",
                "Sartaroshxona",
                "chorsu",
                4,
                "25 km"))
            mapBarberList.add(MapBarberShopModel("https://i.pinimg.com/originals/e5/1f/e7/e51fe72785398953ab9095023bc98505.jpg",
                "Sartaroshxona",
                "chorsu",
                4,
                "25 km"))
            mapBarberList.add(MapBarberShopModel("https://wallpaperaccess.com/full/3696056.jpg",
                "Sartaroshxona",
                "chorsu",
                4,
                "25 km"))
            barberShopAdapter.submitList(mapBarberList)
        }
    }

    private fun searchButtonManager() {
        binding.included.linearCompat.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    private fun playerSheetInstall(view: View) {
        val bottomSheet = view.findViewById<ConstraintLayout>(R.id.included)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        view.findViewById<LinearLayoutCompat>(R.id.linear_compat)
    }

    private fun keyboardChangeListener() {
        KeyboardVisibilityEvent.setEventListener(requireActivity(),
            object : KeyboardVisibilityEventListener {
                override fun onVisibilityChanged(isOpen: Boolean) {
                    if (isOpen) {
                        Log.d("@@@", "show")
                    } else {
                        hideInclude()
                    }
                }
            })
    }

    private fun collapseManager() {
        binding.included.apply {
            bottomSheetBehavior.addBottomSheetCallback(object :
                BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    when (newState) {
                        BottomSheetBehavior.STATE_HIDDEN -> {
                            hideInclude()
                            hideKeyboard()
                        }
                        BottomSheetBehavior.STATE_EXPANDED -> {
                            searchEditText.isVisible = true
                            linearCompat.isVisible = false
                            searchEditText.requestFocus()
                            searchEditText.isFocusableInTouchMode = true
                            searchEditText.isFocusable = true
                            showKeyboard(searchEditText)
                        }
                        BottomSheetBehavior.STATE_COLLAPSED -> {
                            searchEditText.isVisible = false
                            linearCompat.isVisible = true
                            hideKeyboard()
                        }
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {}
            })
        }
    }

    private fun hideInclude() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun showInclude() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onDestroyView() {
        super.onDestroyView()
        hideKeyboard()
        showStatusBarAndBottomBar()
    }
}