package fr.thomasbernard03.tarot

import android.app.Application
import androidx.room.Room
import fr.thomasbernard03.tarot.data.local.ApplicationDatabase
import fr.thomasbernard03.tarot.data.repositories.GameRepositoryImpl
import fr.thomasbernard03.tarot.domain.repositories.GameRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import org.koin.dsl.module

class ApplicationController : Application() {

    private val database: ApplicationDatabase by lazy {
        Room.databaseBuilder(applicationContext, ApplicationDatabase::class.java, "tarot")
            .fallbackToDestructiveMigration() // If migrations needed delete all data and clear schema
            .build()
    }

    private val module = module {
        // Commons

        // Domain
        single<GameRepository> { GameRepositoryImpl() }

        // Data
        single { database.playerDao() }
        single { database.playerGameDao() }
        single { database.gameDao() }


        // https://developer.android.com/kotlin/coroutines/coroutines-best-practices?hl=fr
        // single<CoroutineDispatcher> { Dispatchers.IO }
    }

    override fun onCreate() {
        super.onCreate()
        GlobalContext.startKoin {
            modules(module)
            androidContext(this@ApplicationController)
        }
    }
}