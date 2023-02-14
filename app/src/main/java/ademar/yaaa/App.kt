package ademar.yaaa

import ademar.yaaa.log.AndroidLifecycleLogger
import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject lateinit var lifecycleCallbacks: AndroidLifecycleLogger

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(lifecycleCallbacks)
    }

}
