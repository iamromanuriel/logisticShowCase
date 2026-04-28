package com.example.logisticshowcase.util

import android.content.Context
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMapComposable

object MapViewHolder{
    var mapView: MapView? = null

    fun getOrCreate(context: Context): MapView{
        if(mapView == null){
            mapView = MapView(context).apply {
                onCreate(Bundle())
            }
        }

        return mapView!!
    }

    fun clear(){
        mapView = null
    }
}

@Composable
fun RememberedGoogleMap(
    cameraPositionState: CameraPositionState,
    modifier: Modifier = Modifier,
    content: (@Composable @GoogleMapComposable () -> Unit)? = null
){
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val mapView = remember {
        MapViewHolder.getOrCreate(context)
    }

    LaunchedEffect (cameraPositionState.position) {
        mapView.getMapAsync { googleMap ->
            googleMap.moveCamera(
                CameraUpdateFactory.newCameraPosition(cameraPositionState.position)
            )
        }
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when(event){
                Lifecycle.Event.ON_START -> mapView.onStart()
                Lifecycle.Event.ON_RESUME -> mapView.onResume()
                Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                Lifecycle.Event.ON_STOP -> mapView.onStop()
                Lifecycle.Event.ON_DESTROY -> {}
                else -> {}
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    AndroidView(
        factory = { mapView },
        modifier = modifier
    )

}
