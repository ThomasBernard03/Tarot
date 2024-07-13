package fr.thomasbernard03.tarot

import android.app.Application
import commons.di.initKoin
import fr.thomasbernard03.tarot.commons.di.helpersModule
import fr.thomasbernard03.tarot.commons.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent

class ApplicationController : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@ApplicationController)
            modules(
                listOf(
                    helpersModule,
                    viewModelsModule
                )
            )
        }
    }
}