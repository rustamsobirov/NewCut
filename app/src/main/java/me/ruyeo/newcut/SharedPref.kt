package me.ruyeo.newcut

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import me.ruyeo.newcut.data.model.User
import javax.inject.Inject

class SharedPref @Inject constructor(@ApplicationContext context: Context) {

    private var mySharedPref:SharedPreferences =
        context.getSharedPreferences("filename", Context.MODE_PRIVATE)

    var token: String
        set(value) = mySharedPref.edit().putString("token",value).apply()
        get() = mySharedPref.getString("token", "")!!


    fun setUser(user: User) {
        val editor = mySharedPref.edit()
        editor.putInt("userId", user.id)
        editor.putInt("organizationId", user.organizationId)
        editor.putString("fullName", user.fullName)
        editor.putString("phoneNumber", user.phoneNumber)
        editor.putString("pic", user.picturePath)
        editor.putString("role", user.role)
        editor.apply()
    }

    fun getUser(): User {
        return User(
            mySharedPref.getString("fullName","")!!,
            mySharedPref.getInt("userId", 0),
            mySharedPref.getInt("organizationId", 0),
            mySharedPref.getString("phoneNumber", "")!!,
            mySharedPref.getString("pic", "") ?: "",
            mySharedPref.getString("role", "")!!
        )
    }
}