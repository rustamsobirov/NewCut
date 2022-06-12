package me.ruyeo.newcut.repository

import me.ruyeo.newcut.data.model.Login
import me.ruyeo.newcut.data.model.UserUpdate
import me.ruyeo.newcut.data.remote.ApiService
import retrofit2.http.Body
import javax.inject.Inject

class AuthRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun login(phoneNumber: String) = apiService.login(phoneNumber)

    suspend fun register(phoneNumber: String) = apiService.register(phoneNumber)

    suspend fun confirmationCode(map: HashMap<String,Any>) = apiService.confirmationCode(map)

    // Auth
    suspend fun getAbout(id:Int) = apiService.getAboutMe(id)
    suspend fun updateUser(userUpdate:UserUpdate) = apiService.upDateProfile(userUpdate)



}