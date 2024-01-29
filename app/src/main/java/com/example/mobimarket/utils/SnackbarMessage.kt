package com.example.mobimarket.utils

import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.mobimarket.R
import com.google.android.material.snackbar.Snackbar

fun View.showCustomSnackbar(message: String) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
    snackbar.setBackgroundTint(ContextCompat.getColor(context, R.color.main_red))

    val snackbarLayout: View = snackbar.view
    val params = snackbarLayout.layoutParams as FrameLayout.LayoutParams
    params.gravity = Gravity.TOP
    snackbarLayout.layoutParams = params

    val textView: TextView = snackbarLayout.findViewById(com.google.android.material.R.id.snackbar_text)
    textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_warning, 0, 0, 0)
    textView.compoundDrawablePadding = resources.getDimensionPixelOffset(R.dimen.snackbar_icon_padding)

    snackbar.show()
}
