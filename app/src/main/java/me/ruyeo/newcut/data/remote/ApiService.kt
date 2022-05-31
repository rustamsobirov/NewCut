package me.ruyeo.newcut.data.remote

import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @POST("login")
    @FormUrlEncoded
    suspend fun login(@Field("phoneNumber") phoneNumber: String) // we need model class to fetch response

}