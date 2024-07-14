package commons.di

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import data.local.ApplicationDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.Module
import platform.Foundation.NSHomeDirectory
import org.koin.dsl.module
import data.local.instantiateImpl


fun getDatabaseBuilder(): ApplicationDatabase {
    val dbFile = "${NSHomeDirectory()}/tarot.db"
    return Room.databaseBuilder<ApplicationDatabase>(
        name = dbFile,
        factory = { ApplicationDatabase::class.instantiateImpl() }
    ).setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}

actual fun platformModule(): Module =
    module {
        single<ApplicationDatabase> { getDatabaseBuilder() }
    }
