package com.raphaelweis.rcube

import android.app.Application
import com.raphaelweis.rcube.data.AppContainer
import com.raphaelweis.rcube.data.AppDataContainer

class RCubeApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}