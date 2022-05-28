package me.ruyeo.newcut.ui.client.home.mapview

import android.Manifest
import android.app.Activity
import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.*
import me.ruyeo.newcut.R
import me.ruyeo.newcut.databinding.FragmentMapPermissionBinding
import me.ruyeo.newcut.ui.BaseFragment
import me.ruyeo.newcut.utils.extensions.isLocationEnabled
import me.ruyeo.newcut.utils.extensions.viewBinding

class MapPermissionFragment : BaseFragment(R.layout.fragment_map_permission) {
    private val binding by viewBinding { FragmentMapPermissionBinding.bind(it) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestPermissions()
    }

    private fun requestPermissions() {
        if (!hasPermissionLocation()) {
            val checkLocationPermission = registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) { permissions ->
                if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true) {
                    binding.locationAllowed.isVisible = false
                    if (isLocationEnabled()) {
                        openMap()
                    } else {
                        showLocationOn()
                    }
                } else {
                    binding.locationAllowed.isVisible = true
                }
            }
            checkLocationPermission.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION))
        } else {
            if (!isLocationEnabled()) {
                showLocationOn()
            } else {
                openMap()
            }
        }
    }

    private fun hasPermissionLocation() = ContextCompat.checkSelfPermission(
        requireContext(),
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

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
                    Log.d("@@@", "ERROR")
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

    private var launcher =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                openMap()
            } else {
                showLocationOn()
            }
        }

    private fun openMap() {
        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(R.id.mapFragment)
        }, 3000)

    }
}