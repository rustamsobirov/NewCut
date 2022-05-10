package me.ruyeo.newcut.repository

import me.ruyeo.newcut.data.remote.ApiService
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun login(map: HashMap<String,Any>) = apiService.login(map)
}