package com.ravi.diagnal.util

import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import java.util.*

fun getGridColumnCount(context: Context): Int {
    val orientation: Int = context.resources.configuration.orientation
    return if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
        7 // Number of columns for landscape orientation
    } else {
        3 // Number of columns for portrait orientation
    }
}

fun highlightText(originalText: String, query: String): SpannableStringBuilder {
    val spannableBuilder = SpannableStringBuilder(originalText)
    val lowercaseOriginalText = originalText.toLowerCase(Locale.getDefault())
    val lowercaseQuery = query.toLowerCase(Locale.getDefault())

    var startIndex = lowercaseOriginalText.indexOf(lowercaseQuery)
    if(query.isNotEmpty()){
        while (startIndex != -1) {
            val endIndex = startIndex + lowercaseQuery.length
            spannableBuilder.setSpan(
                ForegroundColorSpan(Color.YELLOW),
                startIndex,
                endIndex,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )
            startIndex = lowercaseOriginalText.indexOf(lowercaseQuery, endIndex)
        }
    }else{
        spannableBuilder.setSpan(
            ForegroundColorSpan(Color.WHITE),
            0,
            originalText.length,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
    }

    return spannableBuilder
}