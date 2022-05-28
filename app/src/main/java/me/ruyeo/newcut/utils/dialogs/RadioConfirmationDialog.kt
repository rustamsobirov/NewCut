package me.ruyeo.newcut.utils.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import android.widget.RadioButton
import me.ruyeo.newcut.R

class RadioConfirmationDialog(context: Context) {
    private val dialog = Dialog(context)

    fun loadDialog() {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_gender)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    fun manClick(): View {
        return dialog.findViewById<RadioButton>(R.id.btn_erkak)
    }

    fun womenClick(): View {
        return dialog.findViewById<RadioButton>(R.id.btn_ayol)
    }

    fun showDialog() {
        dialog.show()
    }

    fun hideDialog() {
        dialog.hide()
    }
}