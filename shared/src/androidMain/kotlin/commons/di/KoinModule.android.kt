package commons.di

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import data.local.ApplicationDatabase
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

actual fun platformModule() = module {
    single<ApplicationDatabase> { getDatabaseBuilder(get()) }
}

fun getDatabaseBuilder(context: Context): ApplicationDatabase {
    val dbFile = context.getDatabasePath("tarot.db")
    return Room.databaseBuilder<ApplicationDatabase>(context, dbFile.absolutePath)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}