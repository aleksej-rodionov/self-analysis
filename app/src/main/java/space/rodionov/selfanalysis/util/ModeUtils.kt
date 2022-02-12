package space.rodionov.selfanalysis.util

import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import space.rodionov.selfanalysis.R

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

    val colors = intArrayOf(
        bgMain,
        bgBeta,
        textMain,
        textBeta,
        bgAccent
    )
    return colors.toTypedArray()
}

fun fetchColors(mode: Int, res: Resources): Array<Int> {
    return fetchTheme(mode, res).fetchColors()
}

//===========================FETCHING==END====================================




//===========================EXTENSIONS====================================

fun CardView.redrawCardView(colors: Array<Int>) {
    this.setCardBackgroundColor(colors[1]) // todo
}

fun TextInputLayout.redrawTIL(colors: Array<Int>) {
    this.children.forEach {
        if (it is TextInputEditText) it.setTextColor(colors[2]) // todo
    }
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

fun FloatingActionButton.redrawFAB(colors: Array<Int>) {
    this.backgroundTintList = ColorStateList.valueOf(colors[4])
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
            if (adapter != null && adapter is ModeAdapter) {
                adapter.updateMode(mode)
            }
        }
        if (child is ViewGroup) child.redrawAllRecyclerAdapters(mode)
    }
}

fun Toolbar.redrawToolbar(colors: Array<Int>) {
    this.setBackgroundDrawable(ColorDrawable(colors[4]))
}

fun ViewGroup.redrawViewGroup(mode: Int) {

    this.redrawAllRecyclerAdapters(mode)

    val colors = fetchColors(mode, resources)

    if (this is CoordinatorLayout) this.redrawCoord(colors)
    if (this is CardView) this.redrawCardView(colors)

    if (this is Toolbar) this.redrawToolbar(colors)
    if (this is TextView) this.redrawTextView(colors)
    if (this is ImageView) this.redrawImageView(colors)
    if (this is FloatingActionButton) this.redrawFAB(colors)
    if (this is ChipGroup) this.redrawChips(colors)
    if (this is TextInputLayout) this.redrawTIL(colors)

}

//===========================EXTENSIONS==END====================================




interface ModeAdapter {
    fun updateMode(mode: Int)
}











