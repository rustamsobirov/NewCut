package me.ruyeo.newcut.ui.client.home.mapview

import android.annotation.SuppressLint
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.PagerSnapHelper
import com.directions.route.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import me.ruyeo.newcut.R
import me.ruyeo.newcut.adapter.MapBarberShopAdapter
import me.ruyeo.newcut.databinding.FragmentMapBinding
import me.ruyeo.newcut.model.map.*
import me.ruyeo.newcut.ui.BaseFragment
import me.ruyeo.newcut.ui.client.home.HomeViewModel
import me.ruyeo.newcut.utils.extensions.distance
import me.ruyeo.newcut.utils.extensions.showSnackMessage
import me.ruyeo.newcut.utils.extensions.viewBinding
import me.ruyeo.newcut.utils.keyboard.KeyboardVisibilityEvent
import me.ruyeo.newcut.utils.keyboard.KeyboardVisibilityEventListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@AndroidEntryPoint
class MapFragment : BaseFragment(R.layout.fragment_map), RoutingListener,
    GoogleMap.OnMarkerClickListener {
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
    private val myLocationZoom = 15f
    private var polyLines: MutableList<Polyline>? = null
    private var markerList = ArrayList<Marker>()

    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->
//        googleMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
        googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        googleMap.uiSettings.isZoomControlsEnabled = false
        googleMap.isMyLocationEnabled = true
        googleMap.uiSettings.isMyLocationButtonEnabled = false
        map = googleMap
        cameraMoveStartedListener(googleMap)
        setUpMap()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        installLocation()
        playerSheetInstall(view)
        collapseManager()
        searchButtonManager()
        keyboardChangeListener()
        barberShopRecyclerItem()
        openDetailFragment()
    }

    private fun btnMyLocationClickManager() {
        binding.btnMyLocation.setOnClickListener {
            updateLastLocation()
        }
    }

    private fun openDetailFragment() {
        barberShopAdapter.itemClick = {
            findNavController().navigate(R.id.action_mapFragment_to_detailFragment)
        }
    }

    private fun updateLastLocation() {
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    lastLocation.latitude,
                    lastLocation.longitude
                ), myLocationZoom
            )
        )
    }

    private fun installLocation() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        if (marker != myLocationMarker) {
            toaster("${marker.position}")
            findRoutes(LatLng(lastLocation.latitude, lastLocation.longitude), marker.position)
        }
        return true
    }

    private fun animateCamera(toLatLong: LatLng, zoom: Float) {
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(toLatLong, zoom))
    }

    private fun findRoutes(start: LatLng?, end: LatLng?) {
        if (start == null || end == null) {
            Toast.makeText(requireContext(), "Unable to get location", Toast.LENGTH_LONG).show()
            updateLastLocation()
        } else {
            val routing = Routing.Builder()
                .travelMode(AbstractRouting.TravelMode.DRIVING)
                .withListener(this)
                .alternativeRoutes(true)
                .waypoints(start, end)
                .key(requireContext().resources.getString(R.string.map_key))
                .build()
            routing.execute()
            animateCamera(LatLng(lastLocation.latitude, lastLocation.longitude), 16f)
        }
    }

    private fun setUpMap() {
        setupMe()
        barShopLatLongListAdder()
        btnMyLocationClickManager()
    }

    private fun setupMe() {
        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
        userFusedLocation()
    }

    @SuppressLint("MissingPermission")
    private fun userFusedLocation() {
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
                markerAdder(currentLatLng)
            }
        }
        fusedLocationClient.lastLocation.addOnFailureListener {
            toaster("$it")
        }
    }

    private fun markerAdder(currentLatLng: LatLng) {
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
            mapBarberList.add(
                MapBarberShopModel(
                    "https://firebasestorage.googleapis.com/v0/b/wallpapers-23e0e.appspot.com/o/Salon-image.png?alt=media&token=88fe9b51-1a16-442b-a994-bcdbf6b37559",
                    "Sartaroshxona",
                    "chorsu",
                    4,
                    "25 km"
                )
            )
            mapBarberList.add(
                MapBarberShopModel(
                    "https://beautyhealthtips.in/wp-content/uploads/2019/02/Quiff-Haircuts.jpg",
                    "Sartaroshxona",
                    "chorsu",
                    4,
                    "25 km"
                )
            )
            mapBarberList.add(
                MapBarberShopModel(
                    "https://i.pinimg.com/originals/e5/1f/e7/e51fe72785398953ab9095023bc98505.jpg",
                    "Sartaroshxona",
                    "chorsu",
                    4,
                    "25 km"
                )
            )
            mapBarberList.add(
                MapBarberShopModel(
                    "https://wallpaperaccess.com/full/3696056.jpg",
                    "Sartaroshxona",
                    "chorsu",
                    4,
                    "25 km"
                )
            )
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
                            locationAddress.isVisible = false
                            searchEditText.requestFocus()
                            searchEditText.isFocusableInTouchMode = true
                            searchEditText.isFocusable = true
                            showKeyboard(searchEditText)
                        }
                        BottomSheetBehavior.STATE_COLLAPSED -> {
                            searchEditText.isVisible = false
                            linearCompat.isVisible = true
                            locationAddress.isVisible = true
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
    }

    override fun onRoutingFailure(p0: RouteException?) {
        showSnackMessage(p0?.message ?: "")
    }

    override fun onRoutingStart() {}

    override fun onRoutingSuccess(route: java.util.ArrayList<Route>?, shortestRouteIndex: Int) {

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
                polyOptions.width(7f)
                polyOptions.addAll(route[shortestRouteIndex].points)
                val polyline = map.addPolyline(polyOptions)
                polylineStartLatLng = polyline.points[0]
                val k = polyline.points.size
                polylineEndLatLng = polyline.points[k - 1]
                (polyLines as ArrayList<Polyline>).add(polyline)
            }
        }
        val endMarker = MarkerOptions()
        endMarker.position(polylineEndLatLng!!)
        deleteMarker(polylineEndLatLng)
//        endMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_dest))

        endMarker.title("Borilishi kerak manzil")
        map.addMarker(endMarker)
    }

    override fun onRoutingCancelled() {

    }

    private fun deleteMarker(polylineEndLatLng: LatLng) {

    }

    private fun cameraMoveStartedListener(googleMap: GoogleMap) {
        googleMap.setOnCameraMoveStartedListener {
            binding.mapPoint.playAnimation()
            binding.mapPoint.loop(true)
        }
        googleMap.setOnCameraIdleListener {
            binding.mapPoint.loop(false)
            //Bu yerda yaxshiroq logika bo'lishi mumkin edi,
            // lekin API clear text trafic bilan muammo sababli response yoki boshqa wrapper
            // class'lardan foydalanad olmadim, call ishlayabdi ))
            // GeoLocation label olishda free api service'dan foydalanilgan shuning uchun to'liq aniq
            // chiqarib bermasligi mumkin

            getCurrentLatLngLabel(current = googleMap.cameraPosition.target)

        }
    }

    private fun getCurrentLatLngLabel(current: LatLng) {
        with(current) {
            GeoClient.geoService.getGeoCodeInfo(
                "f69e3c084c93c56331d9ac63f0df2e41",
                Latlng(this.latitude, this.longitude)
            ).enqueue(object : Callback<GeoResponse> {
                override fun onResponse(
                    call: Call<GeoResponse>,
                    response: Response<GeoResponse>,
                ) {
                    if (response.isSuccessful) {
                        binding.included.locationAddress.text =
                            calculateDestination(
                                response = response.body()!!,
                                current
                            )?.name
                    }
                }

                override fun onFailure(call: Call<GeoResponse>, t: Throwable) {
                    binding.included.locationAddress.text = "${t.message}"
                }

            })
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