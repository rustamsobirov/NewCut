package me.ruyeo.newcut.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import me.ruyeo.newcut.utils.dialogs.MessageDialog
import me.ruyeo.newcut.utils.extensions.showSnackMessage

abstract class BaseFragment(private val layoutRes: Int): Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(layoutRes, container, false)
    }

    protected fun showMessage(message: String) {
        val dialog = MessageDialog(message)
        dialog.onClickListener = {
            dialog.dismiss()
        }
        dialog.show(childFragmentManager, "message_dialog")
    }
}