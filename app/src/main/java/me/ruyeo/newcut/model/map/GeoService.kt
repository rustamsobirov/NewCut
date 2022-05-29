package me.ruyeo.newcut.model.map

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoService {

    @GET("reverse")
    fun getGeoCodeInfo(
        @Query("access_key") access_key: String,
        @Query("query") query: Latlng,
    ): Call<GeoResponse>


}