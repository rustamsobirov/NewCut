package me.ruyeo.newcut.ui

import androidx.fragment.app.Fragment
import me.ruyeo.newcut.utils.dialogs.MessageDialog

abstract class BaseFragment(private val layoutRes: Int): Fragment() {

    protected fun showMessage(message: String) {
        val dialog = MessageDialog(message)
        dialog.onClickListener = {
            dialog.dismiss()
        }
        dialog.show(childFragmentManager, "message_dialog")
    }
}