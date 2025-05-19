package com.example.cohabiaproject

import android.app.Application
import android.util.Log
import com.example.cohabiaproject.di.appModule
import com.example.cohabiaproject.domain.model.Sesion
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyUserApp : Application() {
    override fun onCreate() {
        super.onCreate()
        CoroutineScope(Dispatchers.IO).launch { Sesion.cargarSesion()}
        Log.d("Casa",Sesion.userId+" "+ Sesion.casaId)
        startKoin {
            androidContext(this@MyUserApp)
            modules(appModule)
        }
    }
}