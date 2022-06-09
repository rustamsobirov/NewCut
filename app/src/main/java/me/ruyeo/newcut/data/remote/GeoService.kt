package me.ruyeo.newcut.data.remote

import me.ruyeo.newcut.model.map.GeoResponse
import me.ruyeo.newcut.model.map.Latlng
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoService {

    @GET("reverse")
    suspend fun getGeoCodeInfo(
        @Query("access_key") access_key: String = "abfb06c1b3f6d63c60c62b98c212ad28",
        @Query("query") query: Latlng,
    ): GeoResponse


}