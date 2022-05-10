@file:Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "DEPRECATION")

package me.ruyeo.newcut.utils.extensions
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.gson.JsonSyntaxException
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.text.SimpleDateFormat
import java.util.*


fun String.toLongDate(pattern: String = "dd MM yyyy HH:mm"): Long =
    SimpleDateFormat(pattern, Locale.getDefault()).parse(this).time

fun String.toDate(): Date =
    SimpleDateFormat("dd MM yyyy HH:mm", Locale.getDefault()).parse(this)

fun Fragment.showMessage(string: String){
    Toast.makeText(requireContext(), string, Toast.LENGTH_SHORT).show()
}

fun Fragment.customLog(message:String){
    Log.e("AAA",message)
}

fun Throwable.userMessage() = when (this) {
    is HttpException -> when (this.code()) {
        304 -> "304 Not Modified"
        400 -> "400 Bad Request"
        401 -> "401 Unauthorized"
        403 -> "403 Forbidden"
        404 -> "404 Not Found"
        405 -> "405 Method Not Allowed"
        422 -> "422 Unprocessable"
        500 -> "500 Server Error"
        else -> "Something went wrong"
    }
    is IOException -> "Network error"
    is JsonSyntaxException -> "Ma'lumot olishda xatolik"
    is ConnectException -> "Internet yo'q"
    else -> "Unknown error"
}

/*fun Fragment.unAuthorized(){
    startActivity(Intent(requireActivity(), LoginActivity::class.java))
    requireActivity().finish()
}
fun FragmentActivity.unAuthorized(){
    startActivity(Intent(this, MainActivity::class.java))
    finish()
}*/


