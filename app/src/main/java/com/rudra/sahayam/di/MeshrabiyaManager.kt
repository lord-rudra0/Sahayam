package com.rudra.sahayam.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.ustadmobile.meshrabiya.vnet.AndroidVirtualNode

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "meshr_settings")

object MeshrabiyaManager {

    lateinit var virtualNode: AndroidVirtualNode
        private set

    fun initialize(context: Context) {
        virtualNode = AndroidVirtualNode(
            appContext = context.applicationContext,
            dataStore = context.dataStore
        )
    }
}
