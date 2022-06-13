package me.ruyeo.newcut.repository

import me.ruyeo.newcut.model.map.Latlng
import me.ruyeo.newcut.data.local.dao.BookingDao
import me.ruyeo.newcut.data.local.enitity.Booking
import me.ruyeo.newcut.data.model.Criteria
import me.ruyeo.newcut.data.remote.ApiService
import me.ruyeo.newcut.data.remote.GeoService
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiService: ApiService,
    private val geoService: GeoService,
    private val bookingDao: BookingDao
) {
    suspend fun getLocationName(latLng: Latlng) = geoService.getGeoCodeInfo(query = latLng)


    //orders
    suspend fun getAllOrders() = apiService.getAllOrders()

    //barbershop
    suspend fun getAllBarbershops() = apiService.getAllBarbershops()

    suspend fun getByCriteria(criteria: Criteria) = apiService.getByCriteria(criteria)

    suspend fun getBarbershopById(id: Int) = apiService.getBarbershopById(id)

    suspend fun getAllBooking() = bookingDao.getAllBooking()

    suspend fun addBooking(booking: Booking) = bookingDao.addBooking(booking)

    //notification
    suspend fun getNotifications() = apiService.getAllNotifications()

    //favourites
  //  suspend fun addFavourites(map: HashMap<String, Any>) = apiService.addFavourites(map)

    suspend fun getFavourites(id: Int) = apiService.getFavourites(id)
}