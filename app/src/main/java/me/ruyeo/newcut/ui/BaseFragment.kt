package me.ruyeo.newcut.ui

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import me.ruyeo.newcut.App
import me.ruyeo.newcut.utils.dialogs.CancellationDialog
import me.ruyeo.newcut.utils.dialogs.MessageDialog
import me.ruyeo.newcut.utils.dialogs.ProgressBarDialog


abstract class BaseFragment(private val layoutRes: Int) : Fragment() {

    lateinit var loadingDialog : Dialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return inflater.inflate(layoutRes, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadingDialog = ProgressBarDialog(requireContext())
    }

    protected fun showMessage(message: String) {
        val dialog = MessageDialog(message)
        dialog.onClickListener = {
            dialog.dismiss()
        }
        dialog.show(childFragmentManager, "message_dialog")
    }

    protected fun showCancellationDialog() {
        val dialog = CancellationDialog()
        dialog.onClickListener = {
            dialog.dismiss()
        }
        dialog.show(childFragmentManager, "cancellation_dialog")
    }

    fun showKeyboard(editText: EditText) {
        editText.requestFocus()
        val content =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        content.showSoftInput(editText, 0)
        content.toggleSoftInput(
            InputMethodManager.SHOW_FORCED,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        )
    }

    fun hideKeyboard() {
        val manage =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manage.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    fun toaster(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    fun bitmapFromVector(vectorResId: Int): BitmapDescriptor {
        val vectorDrawable = ContextCompat.getDrawable(requireContext(), vectorResId)
        vectorDrawable!!.setBounds(
            0, 0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    fun onBackPressed() {
        findNavController().popBackStack()
    }

    fun showProgress(){
        loadingDialog.show()
    }

    fun hideProgress(){
        loadingDialog.dismiss()
    }

}