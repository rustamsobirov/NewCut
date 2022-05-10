package me.ruyeo.newcut.data.remote

import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("login")
    suspend fun login(@Body map: HashMap<String,Any>) // we need model class to fetch response
}