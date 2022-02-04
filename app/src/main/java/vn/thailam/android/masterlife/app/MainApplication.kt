package vn.thailam.android.masterlife.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import vn.thailam.android.masterlife.app.di.appModules
import vn.thailam.android.masterlife.app.di.dataModules

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(applicationContext)
            modules(dataModules + appModules)
        }
    }
}