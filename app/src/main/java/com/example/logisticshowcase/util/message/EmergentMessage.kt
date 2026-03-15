package com.example.logisticshowcase.util.message

import android.content.Context
import android.widget.Toast

fun Context.toast(value: String, duration: Int = Toast.LENGTH_SHORT){
    Toast.makeText(this, value, duration).show()
}

fun Any.toString(context: Context): String{
    return when(this){
        is String -> return this
        is Int -> return context.getString(this)
        else -> this.toString()
    }
}