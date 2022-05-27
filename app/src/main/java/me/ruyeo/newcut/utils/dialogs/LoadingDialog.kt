package me.ruyeo.newcut.utils.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import me.ruyeo.newcut.R

class LoadingDialog(context: Context) {
    private val dialog = Dialog(context)

    fun loadDialog() {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.progress_bar_graph)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    fun showDialog() {
        dialog.show()
    }

    fun hideDialog() {
        dialog.hide()
    }
}