package ademar.yaaa.db

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDatabaseCreator @Inject constructor() : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        db.execSQL("INSERT INTO locations (pk, name, deleted) VALUES (1, 'San Diego', 0)")
        db.execSQL("INSERT INTO locations (pk, name, deleted) VALUES (2, 'St. George', 0)")
        db.execSQL("INSERT INTO locations (pk, name, deleted) VALUES (3, 'Park City', 0)")
        db.execSQL("INSERT INTO locations (pk, name, deleted) VALUES (4, 'Dallas', 0)")
        db.execSQL("INSERT INTO locations (pk, name, deleted) VALUES (5, 'Memphis', 0)")
        db.execSQL("INSERT INTO locations (pk, name, deleted) VALUES (6, 'Orlando', 0)")
    }

}
