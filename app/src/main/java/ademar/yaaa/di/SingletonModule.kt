package ademar.yaaa.di

import ademar.yaaa.db.AppDatabase
import ademar.yaaa.db.AppDatabaseCreator
import ademar.yaaa.log.RoomQueryLogger
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.Executors.newSingleThreadExecutor
import javax.inject.Singleton

@[Module InstallIn(SingletonComponent::class)]
object SingletonModule {

    @[Provides Singleton]
    fun providesDatabase(
        @ApplicationContext context: Context,
        appDatabaseCreator: AppDatabaseCreator,
        roomQueryLogger: RoomQueryLogger,
    ): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "yaaadb",
    ).setQueryCallback(roomQueryLogger, newSingleThreadExecutor())
        .addCallback(appDatabaseCreator)
        .build()

}
