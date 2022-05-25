package me.ruyeo.newcut.ui.client.home.mapview

import android.Manifest
import android.app.Activity
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.PagerSnapHelper
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import me.ruyeo.newcut.R
import me.ruyeo.newcut.adapter.MapBarberShopAdapter
import me.ruyeo.newcut.databinding.FragmentMapBinding
import me.ruyeo.newcut.model.map.BarberShopLatLongModel
import me.ruyeo.newcut.model.map.MapBarberShopModel
import me.ruyeo.newcut.ui.BaseFragment
import me.ruyeo.newcut.ui.client.home.HomeViewModel
import me.ruyeo.newcut.utils.extensions.isLocationEnabled
import me.ruyeo.newcut.utils.extensions.viewBinding
import me.ruyeo.newcut.utils.keyboard.KeyboardVisibilityEvent
import me.ruyeo.newcut.utils.keyboard.KeyboardVisibilityEventListener


@AndroidEntryPoint
class MapFragment : BaseFragment(R.layout.fragment_map), GoogleMap.OnMarkerClickListener {
    private lateinit var map: GoogleMap
    private val binding by viewBinding { FragmentMapBinding.bind(it) }

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location
    private val barberShopAdapter by lazy { MapBarberShopAdapter() }
    private var mapBarberList = ArrayList<MapBarberShopModel>()
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private val viewModel by viewModels<HomeViewModel>()
    private var barberShopLatLongList = ArrayList<BarberShopLatLongModel>()
    private lateinit var myLocationMarker: Marker
    var permissionDeniedCount = 0
    var loginUpdater = false
    private val myLocationZoom = 15f

    private val callback = OnMapReadyCallback { googleMap ->
        googleMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
        googleMap.uiSettings.isZoomControlsEnabled = false
        googleMap.isMyLocationEnabled = true
        googleMap.uiSettings.isMyLocationButtonEnabled = false
        map = googleMap
        map.setOnMarkerClickListener(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        installLocation()
        playerSheetInstall(view)
        hideStatusBarAndBottomBar()
        collapseManager()
        searchButtonManager()
        keyboardChangeListener()
        barberShopRecyclerItem()
        checkLocationPermission()
    }

    private fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (permissionDeniedCount >= 1) {
                binding.locationAllowed.isVisible = true
            } else {
                permissionDeniedCount++
                requestLocationPermission()
            }
        } else {
            binding.locationAllowed.isVisible = false
            //1
            if (isLocationEnabled()) {
                setUpMap()
            } else {
                showLocationOn()
            }
        }
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 99)
    }

    private fun btnMyLocationClickManager() {
        binding.btnMyLocation.setOnClickListener {
            if (isLocationEnabled()) {
                updateLastLocation()
            } else {
                showLocationOn()
            }
        }
    }

    private fun showLocationOn() {
        val locationRequest = LocationRequest.create()
        locationRequest.apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 30 * 1000.toLong()
            fastestInterval = 5 * 1000.toLong()
        }
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        builder.setAlwaysShow(true)
        val result =
            LocationServices.getSettingsClient(requireContext())
                .checkLocationSettings(builder.build())
        result.addOnCompleteListener {
            try {
                val response: LocationSettingsResponse = it.getResult(ApiException::class.java)
                if (response.locationSettingsStates!!.isGpsPresent)
                    Log.d("@@@", "ERROR..........")
            } catch (e: ApiException) {
                when (e.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                        val intentSenderRequest =
                            IntentSenderRequest.Builder(e.status.resolution!!).build()
                        launcher.launch(intentSenderRequest)
                    } catch (e: IntentSender.SendIntentException) {
                    }
                }
            }
        }
    }

    //2
    private var launcher =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                checkLocationPermission()
            } else {
                toaster(getString(R.string.please_allow_phone_local))
                showLocationOn()
            }
        }

    private fun updateLastLocation() {
        try {
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lastLocation.latitude,
                lastLocation.longitude), myLocationZoom))
        } catch (e: UninitializedPropertyAccessException) {
            toaster("error $e")
        }
    }


    private fun installLocation() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(requireContext())
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        if (marker != myLocationMarker) {
            toaster("${marker.position}")
        }
        return false
    }

    private fun setUpMap() {
        barShopLatLongListAdder()
        userFusedLocation()
        btnMyLocationClickManager()
            updateLastLocation()
    }

    private fun locationChangeListener() {
        map.setOnMyLocationChangeListener {
            myLocationMarker.position = LatLng(it.latitude, it.longitude)
                lastLocation.latitude = it.latitude
                lastLocation.longitude = it.longitude
        }
    }

    private fun userFusedLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng,
                    myLocationZoom))
                markerAdder(currentLatLng)
                locationChangeListener()
            }
        }
    }

    private fun markerAdder(currentLatLng: LatLng) {
        myLocationMarker = map.addMarker(MarkerOptions().position(currentLatLng)
            .title("My Location")
            .icon(bitmapFromVector(R.drawable.ic_location_darker)))!!
        for (i in 0 until barberShopLatLongList.size) {
            map.addMarker(MarkerOptions().position(barberShopLatLongList[i].latLng)
                .title(barberShopLatLongList[i].localName)
                .icon(bitmapFromVector(R.drawable.ic_location_yellow)))
        }
    }

    private fun barShopLatLongListAdder() {
        barberShopLatLongList.add(BarberShopLatLongModel("1", LatLng(41.324122, 69.229472)))

        barberShopLatLongList.add(BarberShopLatLongModel("2", LatLng(41.317578, 69.217327)))

        barberShopLatLongList.add(BarberShopLatLongModel("3", LatLng(41.321744, 69.236438)))

        barberShopLatLongList.add(BarberShopLatLongModel("4", LatLng(41.315098, 69.223595)))
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

    override fun onDestroyView() {
        super.onDestroyView()
        hideKeyboard()
        showStatusBarAndBottomBar()
    }
}