package me.ruyeo.newcut.ui.client.detail.detail_in

import android.location.Location
import android.os.Bundle
import android.view.View
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import me.ruyeo.newcut.R
import me.ruyeo.newcut.ui.BaseFragment

class DetailMapsFragment : BaseFragment(R.layout.fragment_detail_maps),
    GoogleMap.OnMarkerClickListener {
    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        val myPlace = LatLng(40.73, -73.99)
//        googleMap.addMarker(MarkerOptions().position(myPlace).title("My Favorite City"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(myPlace))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(myPlace, 12.0f))
        map.uiSettings.isZoomControlsEnabled = true
        map.setOnMarkerClickListener(this)
        setUpMap()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        installLocation()
    }

    private fun installLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
    }

    override fun onMarkerClick(p0: Marker): Boolean {
        return false
    }

    private fun setUpMap() {
        map.isMyLocationEnabled = true
        fusedLocationClient.lastLocation.addOnSuccessListener() { location ->
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
            }
        }
    }
}