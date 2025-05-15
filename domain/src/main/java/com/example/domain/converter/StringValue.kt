package com.example.domain.converter

import android.content.Context
import androidx.annotation.StringRes


sealed class StringValue {

    class StringResource(@StringRes val resId: Int, vararg val args: Any) : StringValue()

    data class DynamicResource(val value: String) : StringValue()

    object EmptyResource : StringValue()

    fun asString(context: Context?): String {
        return when (this) {
            is StringResource -> context?.getString(resId, *args).orEmpty()
            is DynamicResource -> value
            is EmptyResource -> ""
        }
    }

}