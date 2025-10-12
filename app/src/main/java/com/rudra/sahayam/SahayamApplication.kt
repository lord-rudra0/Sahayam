package com.rudra.sahayam

import android.app.Application
import com.rudra.sahayam.di.MeshrabiyaManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SahayamApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        MeshrabiyaManager.initialize(this)
    }
}
