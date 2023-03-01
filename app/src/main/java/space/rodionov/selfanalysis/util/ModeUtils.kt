package space.rodionov.selfanalysis.util

import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import space.rodionov.selfanalysis.R
import space.rodionov.selfanalysis.util.Constants.TAG_MODE

//===============================CONSTANTS==================================

object ModeConstants {

    const val MODE_LIGHT = 0
    const val MODE_DARK = 1

}

//===========================FETCHING=========================================

fun fetchTheme(mode: Int, res: Resources): Resources.Theme {
    return when (mode) {
        0 -> {
            val theme = res.newTheme()
            theme.applyStyle(R.style.LightModeColors, false)
            theme
        }
        1 -> {
            val theme = res.newTheme()
            theme.applyStyle(R.style.DarkModeColors, false)
            theme
        }

        else -> {
            val theme = res.newTheme()
            theme.applyStyle(R.style.LightModeColors, false)
            theme
        }
    }
}

fun Resources.Theme.fetchColors(): Array<Int> {

    val bgMainValue = TypedValue()
    this.resolveAttribute(R.attr.bg_main, bgMainValue, true)
    val bgMain = bgMainValue.data

    val bgBetaValue = TypedValue()
    this.resolveAttribute(R.attr.bg_beta, bgBetaValue, true)
    val bgBeta = bgBetaValue.data

    val textMainValue = TypedValue()
    this.resolveAttribute(R.attr.text_main, textMainValue, true)
    val textMain = textMainValue.data

    val textBetaValue = TypedValue()
    this.resolveAttribute(R.attr.text_beta, textBetaValue, true)
    val textBeta = textBetaValue.data

    val bgAccentValue = TypedValue()
    this.resolveAttribute(R.attr.bg_accent, bgAccentValue, true)
    val bgAccent = bgAccentValue.data

    val bgLightValue = TypedValue()
    this.resolveAttribute(R.attr.bg_light, bgLightValue, true)
    val bgLight = bgLightValue.data

    val colors = intArrayOf(
        bgMain,
        bgBeta,
        textMain,
        textBeta,
        bgAccent,
        bgLight
    )
    return colors.toTypedArray()
}

fun fetchColors(mode: Int, res: Resources): Array<Int> {
    return fetchTheme(mode, res).fetchColors()
}

//===========================FETCHING==END====================================


//===========================EXTENSIONS====================================

fun LinearLayout.redrawLinear(mode: Int) {
    Log.d(TAG_MODE, "redrawLinear: CALLED")
    this.redrawViewGroup(mode)
}

fun ScrollView.redrawScrollView(mode: Int) {
    Log.d(TAG_MODE, "redrawScrollView: CALLED")
    this.redrawViewGroup(mode)
}

fun CardView.redrawCardView(colors: Array<Int>) {
    Log.d(TAG_MODE, "redrawCardView: CALLED")
    this.setCardBackgroundColor(colors[1])
    this.children.forEach {
        if (it is TextInputLayout) it.redrawTIL(colors)
    }
}

fun TextInputLayout.redrawTIL(colors: Array<Int>) {
    Log.d(TAG_MODE, "redrawTIL: CALLED")
    this.editText?.setTextColor(colors[2])
    this.editText?.backgroundTintList = null
    this.editText?.backgroundTintList = ColorStateList.valueOf(colors[1])
    this.defaultHintTextColor = ColorStateList.valueOf(colors[3])
    this.hintTextColor = ColorStateList.valueOf(colors[4])
}

fun EditText.redrawET(colors: Array<Int>) {
    this.backgroundTintList = null
    this.backgroundTintList = ColorStateList.valueOf(colors[1])
    this.setTextColor(colors[2])
    this.setHintTextColor(colors[3])
}

fun TextView.redrawTextView(colors: Array<Int>) {
    if (this.tag?.toString() == "tv_beta") {
        this.setTextColor(colors[3])
    } else {
        this.setTextColor(colors[2])
    }
}

fun ImageView.redrawImageView(colors: Array<Int>) {
    if (this.tag?.toString() == "iv_beta") {
        this.imageTintList = null
        this.imageTintList = ColorStateList.valueOf(colors[3])
    } else {
        this.imageTintList = null
        this.imageTintList = ColorStateList.valueOf(colors[2])
    }
}

fun CoordinatorLayout.redrawCoord(colors: Array<Int>) {
    this.setBackgroundColor(colors[0])
}

fun ConstraintLayout.redrawConstraint(colors: Array<Int>) {
    this.setBackgroundColor(colors[0])
}

fun FloatingActionButton.redrawFAB(colors: Array<Int>) {
    this.backgroundTintList = null
    this.backgroundTintList = ColorStateList.valueOf(colors[4])
    this.imageTintList = null
    this.imageTintList = ColorStateList.valueOf(resources.getColor(R.color.gray100))
}

fun ChipGroup.redrawChips(colors: Array<Int>) {
    this.children.forEach {
        if (it is Chip) {
            val csl = ColorStateList(
                arrayOf(
                    intArrayOf(-android.R.attr.state_checked),  // Disabled
                    intArrayOf(android.R.attr.state_checked)    // Enabled
                ),
                intArrayOf(
                    resources.getColor(R.color.gray600),     // The color for the Disabled state
                    colors[4]        // The color for the Enabled state
                )
            )
            it.chipBackgroundColor = csl
        }
    }
}

fun ViewGroup.redrawAllRecyclerAdapters(mode: Int) {
    this.children.forEach { child ->
        if (child is RecyclerView) {
            val adapter = child.adapter
            if (adapter != null && adapter is ModeForAdapter) {
                Log.d(TAG_MODE, "redrawAllRecyclerAdapters: adapter tag = ${adapter.getTag()}, mode = $mode")
                adapter.updateMode(mode)
            }
        }
        if (child is ViewGroup) child.redrawAllRecyclerAdapters(mode)
    }
}

fun Toolbar.redrawToolbar(colors: Array<Int>) {
    this.setBackgroundDrawable(ColorDrawable(colors[4]))
}

fun SwitchCompat.redrawSwitch(colors: Array<Int>) {
    this.setTextColor(colors[2])
    val csl = ColorStateList(
        arrayOf(
            intArrayOf(-android.R.attr.state_checked),  // Unchecked
            intArrayOf(android.R.attr.state_checked)    // Checked
        ),
        intArrayOf(
            resources.getColor(R.color.gray600),     // The color for the Disabled state
            colors[4]        // The color for the Enabled state
        )
    )
    this.trackTintList = null
    this.trackTintList = csl
}

//================================REDRAW VIEWGROUP=========================================

fun ViewGroup.redrawViewGroup(mode: Int) {
    Log.d(TAG_MODE, "redrawViewGroup: VG class = ${this.javaClass.toString()}")

    this.redrawAllRecyclerAdapters(mode)

    val colors = fetchColors(mode, resources)

    if (this is CoordinatorLayout) this.redrawCoord(colors)
    if (this is ConstraintLayout) this.redrawConstraint(colors)
    if (this is CardView) {
        this.redrawCardView(colors)
        this.children.forEach {
            if (it is RelativeLayout) {
                it.redrawViewGroup(mode)
            }
        }
    }

    // Run through children:
    this.children.forEach {

        if (it is Toolbar) it.redrawToolbar(colors)
        if (it is TextView) it.redrawTextView(colors)
        if (it is ImageView) it.redrawImageView(colors)
        if (it is FloatingActionButton) it.redrawFAB(colors)
        if (it is ChipGroup) it.redrawChips(colors)
        if (it is TextInputLayout) it.redrawTIL(colors)
        if (it is EditText) it.redrawET(colors)
        if (it is SwitchCompat) it.redrawSwitch(colors)

        if (it is CardView) {
            it.redrawCardView(colors)
            it.children.forEach { child->
                if (child is RelativeLayout) {
                    child.redrawViewGroup(mode)
                }
            }
        }

        if (it is LinearLayout) it.redrawLinear(mode)
        if (it is ScrollView) it.redrawScrollView(mode)

    }
}

//===========================EXTENSIONS==END====================================


interface ModeForAdapter {
    fun updateMode(mode: Int)

    fun getTag() : String
}











