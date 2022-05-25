package me.ruyeo.newcut.utils.keyboard

import android.R
import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.annotation.NonNull
import kotlin.math.roundToInt

object KeyboardVisibilityEvent {
    private const val KEYBOARD_VISIBLE_THRESHOLD_DP = 100f

    fun setEventListener(
        @NonNull activity: Activity,
        @NonNull listener: KeyboardVisibilityEventListener,
    ) {
        val activityRoot = getActivityRoot(activity)
        activityRoot.viewTreeObserver.addOnGlobalLayoutListener(
            object : OnGlobalLayoutListener {
                private val r = Rect()
                private val visibleThreshold = convertDpToPx(activity,
                    KEYBOARD_VISIBLE_THRESHOLD_DP).roundToInt()
                private var wasOpened = false
                override fun onGlobalLayout() {
                    activityRoot.getWindowVisibleDisplayFrame(r)
                    val heightDiff = activityRoot.rootView.height - r.height()
                    val isOpen = heightDiff > visibleThreshold
                    if (isOpen == wasOpened) {
                        return
                    }
                    wasOpened = isOpen
                    listener.onVisibilityChanged(isOpen)
                }
            })
    }

    private fun getActivityRoot(activity: Activity): View {
        val view = activity.findViewById<View>(R.id.content) as ViewGroup
        try {
            return (view).getChildAt(0)
        } catch (e: Exception) {
        }
        return view
    }

    fun convertDpToPx(context: Context, dp: Float): Float {
        val res = context.resources
        return dp * (res.displayMetrics.densityDpi / 160f)
    }
}
