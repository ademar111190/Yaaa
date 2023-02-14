package ademar.yaaa.log

import androidx.room.RoomDatabase
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomQueryLogger @Inject constructor() : RoomDatabase.QueryCallback {

    private val log = LoggerFactory.getLogger("RoomQueryLogger")

    override fun onQuery(sqlQuery: String, bindArgs: List<Any?>) {
        log.debug(sqlQuery)
        if (bindArgs.isNotEmpty()) {
            log.debug("BindArgs: $bindArgs")
        }
    }

}
