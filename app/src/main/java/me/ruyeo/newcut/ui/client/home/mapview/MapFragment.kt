package me.ruyeo.newcut.ui.client.home.mapview
//private val binding by viewBinding { FragmentMapBinding.bind(it) }
import android.graphics.drawable.Drawable
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import me.ruyeo.newcut.R
import me.ruyeo.newcut.model.map.BarberShopLatLongModel
import me.ruyeo.newcut.ui.BaseFragment
import me.ruyeo.newcut.ui.client.home.HomeViewModel

class MapFragment : BaseFragment(R.layout.fragment_map), GoogleMap.OnMarkerClickListener {
    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location
    private val viewModel by viewModels<HomeViewModel>()
    private var barberShopLatLongList = ArrayList<BarberShopLatLongModel>()
    private lateinit var myLocationMarker: Marker

    private val callback = OnMapReadyCallback { googleMap ->
        googleMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
        map = googleMap
        map.setOnMarkerClickListener(this)
        setUpMap()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        installLocation()
        mapSwipeListener()
    }

    private fun installLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        if (marker != myLocationMarker) {
            toaster("${marker.position}")
        }
        return false
    }

    private fun setUpMap() {
        barShopLatLongListAdder()
        //setup
        map.uiSettings.isZoomControlsEnabled = false
        map.isMyLocationEnabled = true
        map.isIndoorEnabled = false

        userFusedLocation()
        locationChangeListener()
    }

    private fun locationChangeListener() {
        map.setOnMyLocationChangeListener {
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(it.latitude, it.longitude),
                15f))
            myLocationMarker.position = LatLng(it.latitude, it.longitude)
        }
    }

    private fun userFusedLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
                markerAdder(currentLatLng)
            }
        }
    }

    private fun markerAdder(currentLatLng: LatLng) {
        map.animateCamera(CameraUpdateFactory.newLatLng(currentLatLng))
        loadMapIcon(currentLatLng)
    }

    private fun loadMapIcon(currentLatLng: LatLng) {
        myLocationMarker = map.addMarker(MarkerOptions().position(currentLatLng)
            .title("My Location")
            .icon(bitmapFromVector(barberShopLatLongList[0].userImage)))!!
        for (i in 0 until barberShopLatLongList.size) {
            map.addMarker(MarkerOptions().position(barberShopLatLongList[i].latLng)
                .title(barberShopLatLongList[i].localName)
                .icon(bitmapFromVector(barberShopLatLongList[i].userImage)))
        }
    }

    private fun barShopLatLongListAdder() {
        barberShopLatLongList.add(BarberShopLatLongModel("1", LatLng(41.324122, 69.229472),
            "https://icon-library.com/images/tracking_map_location-512_75409.png"))

        barberShopLatLongList.add(BarberShopLatLongModel("2", LatLng(41.317578, 69.217327),
            "https://image.pngaaa.com/132/1259132-middle.png"))

        barberShopLatLongList.add(BarberShopLatLongModel("3", LatLng(41.321744, 69.236438),
            "https://p7.hiclipart.com/preview/702/783/738/computer-icons-computer-software-location-icon.jpg"))

        barberShopLatLongList.add(BarberShopLatLongModel("4", LatLng(41.315098, 69.223595),
            "https://www.clipartmax.com/png/middle/17-170231_location-icons-location-png.png"))
    }

    private fun bitmapFromVector(imageLink: String): BitmapDescriptor {
        val imageView = ImageView(requireContext())
        Glide.with(requireContext()).load(imageLink).placeholder(R.drawable.ic_location_blue)
            .diskCacheStrategy(
                DiskCacheStrategy.ALL)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean,
                ): Boolean {
                    Log.d("@@@", "Load Error")
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean,
                ): Boolean {
                    Log.d("@@@", "Load Success")
                    return true
                }
            })
            .into(imageView)
        return BitmapDescriptorFactory.fromBitmap(imageView.drawable.toBitmap())
    }

    private fun mapSwipeListener() {

    }
}