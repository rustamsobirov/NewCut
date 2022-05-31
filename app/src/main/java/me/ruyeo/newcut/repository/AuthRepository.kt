package me.ruyeo.newcut.repository

import me.ruyeo.newcut.data.remote.ApiService
import javax.inject.Inject

class AuthRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun login(phoneNumber: String) = apiService.login(phoneNumber)
}