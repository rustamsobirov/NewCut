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
    suspend fun confirmationCode(@Body map: HashMap<String, Any>): BaseResponseObject<Confirm>


    @GET("auth/$BACK_VER/{id}")
    suspend fun getAboutMe(@Path("id")id: Int):BaseResponseObject<User>


    @PUT("auth/$BACK_VER/update")
    suspend fun upDateProfile(@Body userUpdate: UserUpdate):BaseResponseObject<User>




    //orders
    @GET("order/$BACK_VER/list")
    suspend fun getAllOrders(): BaseResponseList<Order>


    //organization
    @GET("all")
    suspend fun getAllOrganization(): Organization


    //barbershop
    @GET("barbershop/$BACK_VER/getAll")
    suspend fun getAllBarbershops(): BaseResponseList<Barbershop>

    @POST("barbershop/$BACK_VER/getByCriteria")
    suspend fun getByCriteria(@Body criteria: Criteria): BaseResponseList<Barbershop>

    @POST("barbershop/$BACK_VER/create")
    suspend fun createBarbershop(@Body map: HashMap<String, Any>): BaseResponseList<Barbershop>

    @PATCH("barbershop/$BACK_VER/update")
    suspend fun updateBarbershop(@Body map: HashMap<String, Any>)

    @GET("barbershop/$BACK_VER/{id}")
    suspend fun getBarbershopById(@Path("id") id: Int): BaseResponseObject<Barbershop>


    //barberbshop rating

    @POST("rating/create")
    suspend fun giveRating(@Body map: HashMap<String, Any>)

    @GET("rating/{id}")
    suspend fun getRatingById(@Path("id") id: Int): Rating


    //notification

    @GET("notification/$BACK_VER/list")
    suspend fun getAllNotifications(): BaseResponseList<Notification>
}