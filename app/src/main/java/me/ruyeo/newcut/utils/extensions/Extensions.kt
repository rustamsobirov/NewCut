package me.ruyeo.newcut.utils.extensions

import android.Manifest
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.ColorInt
import androidx.annotation.LayoutRes
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

fun Context.color(colorRes: Int) = ContextCompat.getColor(this, colorRes)

fun Context.getTintDrawable(drawableRes: Int, colorRes: Int): Drawable {
    val source = ContextCompat.getDrawable(this, drawableRes)!!.mutate()
    val wrapped = DrawableCompat.wrap(source)
    DrawableCompat.setTint(wrapped, color(colorRes))
    return wrapped
}

fun EditText.etCondition(condition: Boolean){
    this.isEnabled = condition
}

fun Context.getTintDrawable(
    drawableRes: Int,
    colorResources: IntArray,
    states: Array<IntArray>
): Drawable {
    val source = ContextCompat.getDrawable(this, drawableRes)!!.mutate()
    val wrapped = DrawableCompat.wrap(source)
    DrawableCompat.setTintList(
        wrapped,
        ColorStateList(states, colorResources.map { color(it) }.toIntArray())
    )
    return wrapped
}

fun ImageView.tint(colorRes: Int) = this.setColorFilter(this.context.color(colorRes))
fun EditText.setTint(colorRes: Int) {
    val constantState = background.constantState ?: return
    val drwNewCopy = constantState.newDrawable().mutate()
    val wrapped = DrawableCompat.wrap(drwNewCopy)
    DrawableCompat.setTint(wrapped, context.color(colorRes))
    ViewCompat.setBackground(this, wrapped)
}

fun Context.getMyDrawable(id: Int): Drawable? = ResourcesCompat.getDrawable(resources, id, null)
fun Context.getMyColor(id: Int): Int = ResourcesCompat.getColor(resources, id, null)

fun Boolean.toInt() = if (this) 1 else 0

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun View.visible(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}

fun TextView.showTextOrHide(str: String?) {
    this.text = str
    this.visible(!str.isNullOrBlank())
}

fun EditText.txt() = text.toString()
fun EditText.txtToInt() = if (text.isEmpty()) 0 else text.toString().toInt()


fun Fragment.showSnackMessage(message: String) {
    view?.showSnackMessage(message)

}

fun View.showSnackMessage(message: String) {
    val ssb = SpannableStringBuilder().apply {
        append(message)
        setSpan(ForegroundColorSpan(Color.WHITE), 0, message.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textAlignment = View.TEXT_ALIGNMENT_CENTER
    }
    Snackbar.make(this, ssb, Snackbar.LENGTH_LONG).show()
}

fun Any.objectScopeName() = "${javaClass.simpleName}_${hashCode()}"

fun View.setBackgroundTintByColor(@ColorInt color: Int) {
    val wrappedDrawable = DrawableCompat.wrap(background)
    DrawableCompat.setTint(wrappedDrawable.mutate(), color)
}

fun Int.toBoolean() = this != 0

fun String.textOrNull() = if (isEmpty()) null else this

fun TextView.textOrNull(): Boolean = !this.text.isNullOrEmpty()

fun View.slideDown(animationEnd: () -> Unit = {}) {
    visibility = View.VISIBLE
    val layoutParams = layoutParams
    layoutParams.height = 1
    this.layoutParams = layoutParams

    measure(
        View.MeasureSpec.makeMeasureSpec(
            Resources.getSystem().displayMetrics.widthPixels,
            View.MeasureSpec.EXACTLY
        ),
        View.MeasureSpec.makeMeasureSpec(
            0,
            View.MeasureSpec.UNSPECIFIED
        )
    )

    val height = measuredHeight
    val valueAnimator = ObjectAnimator.ofInt(1, height)
    valueAnimator.addUpdateListener { animation ->
        val value = animation.animatedValue as Int
        if (height > value) {
            layoutParams.height = value
            this.layoutParams = layoutParams
        } else {
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            this.layoutParams = layoutParams
            visibility = View.VISIBLE
        }
    }
    valueAnimator.addListener(object : Animator.AnimatorListener {
        override fun onAnimationRepeat(p0: Animator?) {
        }

        override fun onAnimationEnd(p0: Animator?) {
            animationEnd.invoke()
        }

        override fun onAnimationCancel(p0: Animator?) {
        }

        override fun onAnimationStart(p0: Animator?) {
        }

    })
    valueAnimator.start()
}

fun View.slideUp() {
    post {
        val valueAnimator = ObjectAnimator.ofInt(height, 0)
        valueAnimator.addUpdateListener { animation ->
            val value = animation.animatedValue as Int
            if (value > 0) {
                val layoutParams = layoutParams
                layoutParams.height = value
                this.layoutParams = layoutParams
            } else {
                this.visibility = View.GONE
            }
        }
        valueAnimator.start()
    }
}


private const val ANIMATION_DURATION = 300L

fun View.fadeIn() {
    if (visibility == View.VISIBLE) {
        alpha = 1.0F
        return
    }

    alpha = 0.0F
    visibility = View.VISIBLE
    animate().alpha(1.0F).setDuration(ANIMATION_DURATION)
        .setListener(null)
}

fun View.fadeOut(isCanceled: () -> Boolean = { false }) {
    if (visibility == View.GONE) {
        return
    }

    alpha = 1.0F
    animate().alpha(0.0F).setDuration(ANIMATION_DURATION)
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                val canceled = isCanceled.invoke()
                if (canceled.not()) {
                    visibility = View.GONE
                    alpha = 1.0F
                }
            }
        })
}

fun EditText.isEmptyValue(): Boolean {
    if (this.text.toString().isEmpty()) {
        this.setText("0")
    }
    return true
}

fun Date.toString(format: String, locale: Locale = Locale.US): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(this)
}

fun getCurrentDateTime(): Date {
    return Calendar.getInstance().time
}

fun formatDate(): String {
    return getCurrentDateTime().toString("dd-MM-yyyy")
}

fun Activity.callToCenter() {
    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED
    ) {
        phoneCall(this)
    } else {
        val PERMISSIONS_STORAGE = arrayOf(Manifest.permission.CALL_PHONE)
        //Asking request Permissions
        ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 9)
    }
}

private fun phoneCall(activity: Activity) {
    if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
        val intent = Intent(Intent.ACTION_CALL, Uri.fromParts("tel", "+998712317193", null))
        activity.startActivity(intent)
    } else {
        Toast.makeText(activity, "You don't assign permission.", Toast.LENGTH_SHORT).show()
    }
}

fun Context.showMessage(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun moneyToPin(money: String): String {
    val length = money.length
    var ans = ""
    for (i in 0 until length) {
        ans += "*"
    }
    return ans
}

fun Long.formattedMoney(showDecimal: Boolean = true, tiyinToSum: Boolean = false) =
    formatMoney(if (tiyinToSum) (this / 100.toDouble()) else this.toDouble(), showDecimal)

fun Double.formattedMoney(showDecimal: Boolean = true) =
    formatMoney(this, showDecimal)

fun String.formattedMoney(showDecimal: Boolean = true) =
    formatMoney(this.toDouble(), showDecimal)

fun String.formatInt(): String {
    return if (this.isEmpty()) ""
    else this.toFloat().roundToInt().toString()
}

fun Float.formattedMoney(showDecimal: Boolean = true) =
    formatMoney(this.toDouble(), showDecimal)

fun Int.formattedMoney(showDecimal: Boolean = true) =
    formatMoney(this.toDouble(), showDecimal)

fun String.convert(): String {
    val format = DecimalFormat("#,###.00")
    format.isDecimalSeparatorAlwaysShown = false
    return format.format(this).toString()
}

// TODO 240.000 incorrect format error
fun formatMoney(value: Double, showDecimal: Boolean): String {
    val decimalFormat = DecimalFormat("###,###,##0.00")
    decimalFormat.groupingSize = 3
    decimalFormat.minimumFractionDigits = 0

    val s = DecimalFormatSymbols()
    s.groupingSeparator = ' '
    val symbols = decimalFormat.decimalFormatSymbols
    s.decimalSeparator = symbols.decimalSeparator
    decimalFormat.decimalFormatSymbols = s

    decimalFormat.minimumFractionDigits = if (showDecimal) 2 else 0
    decimalFormat.maximumFractionDigits = if (showDecimal) 2 else 0

    return decimalFormat.format(value)
}

fun Spinner.setSelections(spinnerId: Int?) {
    if (this.adapter.count > spinnerId ?: 0)
        this.setSelection(spinnerId ?: 0)
}

fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
fun Any.toRoundInt(): Int = this.toString().toDouble().roundToInt()

typealias SingleBlock <T> = (T) -> Unit

fun Long.generateId(): String {
    if (this >= 10_000) return this.toString()
    return "00000$this".reversed().substring(0, 5).reversed()
}

fun String.scanId(): String {
    var res = ""
    var isDigit = false
    for (i in this.indices) {
        if (!isDigit) {
            if (this[i].isDigit() && this[i].toRoundInt() > 0) {
                isDigit = true
                res += this[i]
            }
        } else res += this[i]
    }

    return res
}