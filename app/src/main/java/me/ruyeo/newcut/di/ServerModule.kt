package me.ruyeo.newcut.di

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.ruyeo.newcut.data.remote.ApiService
import me.ruyeo.newcut.data.remote.GeoService
import me.ruyeo.newcut.utils.Constants
import me.ruyeo.newcut.utils.Constants.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServerModule {

    @Provides
    @Singleton
    @Named("Normal")
    fun getRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun getApiService(@Named("Normal") retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun getClient(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout( 60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        //   .addInterceptor(ChuckInterceptor(context))
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .addInterceptor(Interceptor { chain ->
            val builder = chain.request().newBuilder()
            builder.header("Content-Type", "application/json")
            builder.header("Accept", "application/json")
           //   if (sharedPref.user != ""){
             //     builder.header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIrOTk4OTQxMTEwNzE3Iiwicm9sZXMiOlsiQ0xJRU5UIl0sImV4cCI6MTY1NTA0OTkyMn0.jmnpKdM0Dub-ylE_LesdGWXpmzhHGGr5DcrZj7bRYJI")
            //  }
            chain.proceed(builder.build())
        })
        .build()

    @Provides
    @Singleton
    @Named("Geo")
    fun getRetrofitGeo(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.MAP_BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun getApiServiceGeo(@Named("Geo") retrofit: Retrofit): GeoService =
        retrofit.create(GeoService::class.java)

    @[Provides Singleton]
    fun fusedLocationClient(@ApplicationContext context: Context): FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

}