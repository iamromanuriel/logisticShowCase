package com.example.logisticshowcase.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class LogisticShowCaseApp : Application(){

    override fun onCreate() {
        super.onCreate()
    }

}