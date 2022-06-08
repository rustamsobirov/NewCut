package me.ruyeo.newcut.data.remote

import me.ruyeo.newcut.data.model.*
import retrofit2.http.*

interface ApiService {

    //auth
    @POST("auth/loginByPhone")
    suspend fun login(@Body phoneNumber: Login): LoginResponse // we need model class to fetch response

    @POST("auth/register")
    suspend fun register(@Body phoneNumber: Login): LoginResponse

    @POST("auth/confirmationLoginCode")
    suspend fun confirmationLoginCode(@Body map: HashMap<String,Any>): LoginResponse

    @POST("auth/confirmationRegisterCode")
    suspend fun confirmationRegisterCode(@Body map: HashMap<String,Any>): LoginResponse



    //orders
    @GET("order/list")
    suspend fun getAllOrders(): Order



    //organization
    @GET("all")
    suspend fun getAllOrganization(): Organization



    //barbershop
    @GET("barbershop/getAll")
    suspend fun getAllBarbershops(): Barbershop

    @POST("barbershop/create")
    suspend fun createBarbershop(@Body map: HashMap<String,Any>): Barbershop

    @PATCH("barbershop/update")
    suspend fun updateBarbershop(@Body map: HashMap<String,Any>)


    //barberbshop rating

    @POST("rating/create")
    suspend fun giveRating(@Body map: HashMap<String,Any>)

    @GET("rating/{id}")
    suspend fun getRatingById(@Path("id") id: Int): Rating


    //notification

    @GET("notification/list")
    suspend fun getAllNotifications(): Notification
}