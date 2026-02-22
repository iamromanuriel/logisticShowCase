package com.example.logisticshowcase.util.location

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.launchNavigation( latitud: Double, longitud: Double) {
    val gmmIntentUri = Uri.parse("google.navigation:q=$latitud,$longitud")
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)

    mapIntent.setPackage("com.google.android.apps.maps")

    if (mapIntent.resolveActivity(this.packageManager) != null) {
        this.startActivity(mapIntent)
    } else {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/dir/?api=1&destination=$latitud,$longitud"))
        this.startActivity(browserIntent)
    }
}
