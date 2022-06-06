package me.ruyeo.newcut.repository

import me.ruyeo.newcut.data.model.Login
import me.ruyeo.newcut.data.remote.ApiService
import retrofit2.http.Body
import javax.inject.Inject

class AuthRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun login(phoneNumber: Login) = apiService.login(phoneNumber)

    suspend fun register(phoneNumber: Login) = apiService.register(phoneNumber)

    suspend fun confirmationRegisterCode(map: HashMap<String,Any>) = apiService.confirmationRegisterCode(map)

    suspend fun confirmationLoginCode(map: HashMap<String,Any>) = apiService.confirmationLoginCode(map)
}