package me.ruyeo.newcut.data.remote

import me.ruyeo.newcut.data.model.*
import me.ruyeo.newcut.utils.Constants.BACK_VER
import retrofit2.http.*

interface ApiService {

    //auth
    @POST("auth/$BACK_VER/loginByPhone")
    suspend fun login(@Body phoneNumber: String): BaseResponseObject<Login> // we need model class to fetch response

    @POST("auth/$BACK_VER/register")
    suspend fun register(@Body phoneNumber: String): BaseResponseObject<Boolean>

    @POST("auth/$BACK_VER/confirmUserCode")
    suspend fun confirmationCode(@Body map: HashMap<String,Any>): BaseResponseObject<Confirm>




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
    suspend fun createBarbershop(@Body map: HashMap<String,Any>): BaseResponseList<Barbershop>

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