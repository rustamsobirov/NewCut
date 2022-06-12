package me.ruyeo.newcut.ui.client.home.mapview

import android.annotation.SuppressLint
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.directions.route.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import me.ruyeo.newcut.R
import me.ruyeo.newcut.adapter.MapBarberShopAdapter
import me.ruyeo.newcut.data.model.Criteria
import me.ruyeo.newcut.databinding.FragmentMapBinding
import me.ruyeo.newcut.model.map.BarberShopLatLongModel
import me.ruyeo.newcut.model.map.GeoCodeInfo
import me.ruyeo.newcut.model.map.GeoResponse
import me.ruyeo.newcut.model.map.Latlng
import me.ruyeo.newcut.ui.BaseFragment
import me.ruyeo.newcut.ui.client.filter.FilterAndBookingBarberFragment
import me.ruyeo.newcut.ui.client.home.HomeViewModel
import me.ruyeo.newcut.utils.UiStateList
import me.ruyeo.newcut.utils.UiStateObject
import me.ruyeo.newcut.utils.extensions.distance
import me.ruyeo.newcut.utils.extensions.showSnackMessage
import me.ruyeo.newcut.utils.keyboard.KeyboardVisibilityEvent
import me.ruyeo.newcut.utils.keyboard.KeyboardVisibilityEventListener

@AndroidEntryPoint
class MapFragment : BaseFragment(R.layout.fragment_map), RoutingListener,
    GoogleMap.OnMarkerClickListener {
    private lateinit var map: GoogleMap

    //    private var binding: FragmentMapBinding? = null
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var lastLocation: Location? = null
    private val barberShopAdapter by lazy { MapBarberShopAdapter() }
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private val viewModel by viewModels<HomeViewModel>()
    private var barberShopLatLongList = ArrayList<BarberShopLatLongModel>()
    private val myLocationZoom = 14.5f
    private var polyLines: MutableList<Polyline>? = null
    private var markerList = ArrayList<Marker>()
    private var cameraCurrentLatLng: LatLng? = null

    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->
//        googleMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
        googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        googleMap.uiSettings.isZoomControlsEnabled = false
        googleMap.isMyLocationEnabled = true
        googleMap.uiSettings.isMyLocationButtonEnabled = false
        map = googleMap
        cameraMoveStartedListener(googleMap)
        map.setOnMarkerClickListener(this)
        setUpMap()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cri = Criteria(69.226296, 41.3264751, 4)
        viewModel.getByCriteria(cri)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMapBinding.bind(view)
        installLocation()
        playerSheetInstall(view)
        collapseManager()
        searchButtonManager()
        keyboardChangeListener()
        openDetailFragment()
        replaceFrameManager()

        setupObservers()
        binding.barberShopRecyclerView.adapter = barberShopAdapter
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.criteriaState.collect {
                    when (it) {
                        is UiStateList.LOADING -> {
                            showProgress()
                        }
                        is UiStateList.SUCCESS -> {
                            hideProgress()
                            barberShopAdapter.submitList(it.data)
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

    private fun replaceFrameManager() {
        binding.included.openerFrame.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, FilterAndBookingBarberFragment()).commit()
    }

    private fun btnMyLocationClickManager() {
        binding.btnMyLocation.setOnClickListener {
            updateLastLocation()
        }
    }

    private fun openDetailFragment() {
        barberShopAdapter.itemClick = {
            findNavController().navigate(
                R.id.action_mapFragment_to_detailFragment,
                bundleOf("barbershopId" to it)
            )
        }
    }

    private fun updateLastLocation() {
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    lastLocation!!.latitude,
                    lastLocation!!.longitude
                ), myLocationZoom
            )
        )
    }

    private fun installLocation() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        routeClear()
        if (cameraCurrentLatLng != null)
            findRoutes(
                LatLng(cameraCurrentLatLng!!.latitude, cameraCurrentLatLng!!.longitude),
                marker.position
            ) else
            findRoutes(LatLng(lastLocation!!.latitude, lastLocation!!.longitude), marker.position)
        return true
    }

    private fun routeClear() {
        if (polyLines != null)
            polyLines!![0].remove()
    }

    private fun Float.animateCamera(toLatLong: LatLng) {
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(toLatLong, this))
    }

    private fun findRoutes(start: LatLng?, end: LatLng?) {
        if (start == null || end == null) {
            toaster("Unable to get location")
            updateLastLocation()
        } else {
            val routing = Routing.Builder()
                .travelMode(AbstractRouting.TravelMode.DRIVING)
                .withListener(this)
                .alternativeRoutes(true)
                .waypoints(start, end)
                .key("AIzaSyCVwdU3slouglv7TBDh3juGegafJVnKx8U")
                .build()
            routing.execute()
            if (cameraCurrentLatLng != null)
                16f.animateCamera(
                    LatLng(
                        cameraCurrentLatLng!!.latitude,
                        cameraCurrentLatLng!!.longitude
                    )
                ) else
                16f.animateCamera(LatLng(lastLocation!!.latitude, lastLocation!!.longitude))
        }
    }

    private fun setUpMap() {
        setupMe()
        btnMyLocationClickManager()
    }

    private fun setupMe() {
        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(requireContext())

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                map.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        currentLatLng,
                        myLocationZoom
                    )
                )
                markerAdder()

            } else {
                Handler(Looper.getMainLooper()).postDelayed({
                    fusedLocationClient =
                        LocationServices.getFusedLocationProviderClient(requireContext())
                    toaster("loading your location...")
                    findNavController().popBackStack()
                }, 400)
            }
        }
        fusedLocationClient.lastLocation.addOnFailureListener {
            toaster("$it")
        }
    }

    private fun markerAdder() {
        for (i in 0 until barberShopLatLongList.size) {
            val myMarker = map.addMarker(
                MarkerOptions().position(barberShopLatLongList[i].latLng)
                    .title(barberShopLatLongList[i].localName)
                    .icon(bitmapFromVector(R.drawable.ic_location_yellow))
            )
            markerList.add(myMarker!!)
        }
    }

    private fun markerUpdater() {
        for (i in 0 until markerList.size) {
            val myMarker = map.addMarker(
                MarkerOptions().position(barberShopLatLongList[i].latLng)
                    .title(barberShopLatLongList[i].localName)
                    .icon(bitmapFromVector(R.drawable.ic_location_yellow))
            )
            markerList.add(myMarker!!)
        }
    }

    private fun searchButtonManager() {
        binding.included.mainContainer.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    private fun playerSheetInstall(view: View) {
        val bottomSheet = view.findViewById<ConstraintLayout>(R.id.included)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
    }

    private fun keyboardChangeListener() {
        KeyboardVisibilityEvent.setEventListener(requireActivity(),
            object : KeyboardVisibilityEventListener {
                override fun onVisibilityChanged(isOpen: Boolean) {
                    if (isOpen) {
                        Log.d("@@@", "show")
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
                        BottomSheetBehavior.STATE_EXPANDED -> {
                            binding.included.openerFrame.isVisible = false
                            locationAddress.isVisible = false
                        }
                        BottomSheetBehavior.STATE_COLLAPSED -> {
                            binding.included.openerFrame.isVisible = true
                            locationAddress.isVisible = true
                            hideKeyboard()
                        }
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {}
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        hideKeyboard()
    }

    override fun onRoutingFailure(p0: RouteException?) {
        showSnackMessage(p0?.message ?: "")
    }

    override fun onRoutingStart() {}

    override fun onRoutingSuccess(route: ArrayList<Route>?, shortestRouteIndex: Int) {

        if (polyLines != null) {
            polyLines?.clear()
        }
        val polyOptions = PolylineOptions()
        var polylineStartLatLng: LatLng? = null
        var polylineEndLatLng: LatLng? = null
        polyLines = ArrayList()
        for (i in route!!.indices) {
            if (i == shortestRouteIndex) {
                polyOptions.color(
                    Color.rgb(
                        (0..255).random(),
                        (0..255).random(),
                        (0..255).random()
                    )
                )
                polyOptions.width(10f)
                polyOptions.addAll(route[shortestRouteIndex].points)
                val polyline = map.addPolyline(polyOptions)
                polylineStartLatLng = polyline.points[0]
                val k = polyline.points.size
                polylineEndLatLng = polyline.points[k - 1]
                (polyLines as ArrayList<Polyline>).add(polyline)
            }
        }
        /*        val endMarker = MarkerOptions()
        endMarker.position(polylineEndLatLng!!)
        deleteMarker(polylineEndLatLng)
        endMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_dest))

        endMarker.title("Borilishi kerak manzil")
        map.addMarker(endMarker)*/
    }

    override fun onRoutingCancelled() {

    }

    private fun cameraMoveStartedListener(googleMap: GoogleMap) {
        googleMap.setOnCameraMoveStartedListener {
            binding.mapPoint.playAnimation()
            binding.mapPoint.loop(true)
            bottomSheetBehavior.isHideable = true
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

//            val layoutParams = binding.map.layoutParams
//            layoutParams.width = screenWidth()
//            layoutParams.height = screenHeight()
//            binding.map.layoutParams = layoutParams

        }
        googleMap.setOnCameraIdleListener {
            binding.mapPoint.loop(false)
            bottomSheetBehavior.isHideable = false
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            getCurrentLatLngLabel(current = googleMap.cameraPosition.target)
            cameraCurrentLatLng = googleMap.cameraPosition.target

//            val layoutParams = binding.map.layoutParams
//            layoutParams.width = screenWidth()
//            layoutParams.height = screenHeight() - 500
//            binding.map.layoutParams = layoutParams

        }
    }

    private fun getCurrentLatLngLabel(current: LatLng) {
        with(current) {
            viewModel.getLocationName(Latlng(this.latitude, this.longitude))

            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.getLocationName.collect {
                        when (it) {
                            is UiStateObject.LOADING -> {

                            }
                            is UiStateObject.SUCCESS -> {
                                binding.included.locationAddress.text =
                                    calculateDestination(
                                        response = it.data,
                                        current
                                    )?.name
                            }
                            is UiStateObject.ERROR -> {

                            }
                            else -> Unit
                        }
                    }
                }
            }
        }
    }

    private fun calculateDestination(response: GeoResponse, latlng: LatLng): GeoCodeInfo? {
        var geoCodeInfo: GeoCodeInfo? = null
        var minDistance = Double.MAX_VALUE

        for (datum in response.data) {
            if (minDistance > distance(
                    datum.latitude,
                    datum.longitude,
                    latlng.latitude,
                    latlng.longitude
                )
            ) {
                minDistance =
                    distance(datum.latitude, datum.longitude, latlng.latitude, latlng.longitude)
                geoCodeInfo = datum
            }
        }

        return geoCodeInfo

    }

}