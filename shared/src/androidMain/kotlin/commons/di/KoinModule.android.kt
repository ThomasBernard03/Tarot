package commons.di

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import data.local.ApplicationDatabase
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

actual fun platformModule() = module {
    Napier.base(DebugAntilog())
    single<ApplicationDatabase> { getDatabaseBuilder(get()) }
}

private fun getDatabaseBuilder(context: Context): ApplicationDatabase {
    val dbFile = context.getDatabasePath("tarot.db")
    return Room.databaseBuilder<ApplicationDatabase>(context, dbFile.absolutePath)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}