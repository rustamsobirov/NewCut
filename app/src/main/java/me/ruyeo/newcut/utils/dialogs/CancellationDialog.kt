package me.ruyeo.newcut.utils.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import me.ruyeo.newcut.R
import me.ruyeo.newcut.databinding.ViewDialogCancellationBinding

class CancellationDialog: DialogFragment() {
    lateinit var onClickListener: (() -> Unit)
    private var _bn: ViewDialogCancellationBinding? = null
    private val bn get() = _bn!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.CustomDialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _bn = ViewDialogCancellationBinding.inflate(inflater, container, false)
        return bn.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = false
        bn.apply {

            cancelBtn.setOnClickListener {
                onClickListener.invoke()
                dismiss()
            }

            selectBtn.setOnClickListener {
                onClickListener.invoke()
                dismiss()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _bn = null
    }
}