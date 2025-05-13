package com.hyunuk.flos.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class PhoneNumberTransformation : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val digits = text.text.filter { it.isDigit() }

        val formattedText = when {
            digits.length <= 3 -> digits
            digits.length <= 7 -> "${digits.substring(0, 3)}-${digits.substring(3)}"
            else -> "${digits.substring(0, 3)}-${digits.substring(3, 7)}-${digits.substring(7)}"
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                var adjustedOffset = offset
                if (offset > 3) adjustedOffset++
                if (offset > 7) adjustedOffset++
                return adjustedOffset.coerceAtMost(formattedText.length)
            }

            override fun transformedToOriginal(offset: Int): Int {
                var adjustedOffset = offset
                if (offset > 7) adjustedOffset--
                if (offset > 3) adjustedOffset--
                return adjustedOffset.coerceAtMost(digits.length)
            }
        }

        return TransformedText(AnnotatedString(formattedText), offsetMapping)
    }
}