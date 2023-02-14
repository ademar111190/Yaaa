package ademar.yaaa.db

import android.content.Context
import androidx.room.Room

object FakeAppDatabase {

    fun create(
        context: Context,
    ) = Room.inMemoryDatabaseBuilder(
        context,
        AppDatabase::class.java
    ).addCallback(AppDatabaseCreator()).build()

}
