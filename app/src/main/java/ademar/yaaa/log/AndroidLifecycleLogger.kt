package ademar.yaaa.log

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AndroidLifecycleLogger @Inject constructor() : ActivityLifecycleCallbacks,
    FragmentLifecycleCallbacks() {

    private val log = LoggerFactory.getLogger("Lifecycle")

    override fun onActivityPreCreated(activity: Activity, savedInstanceState: Bundle?) {
        super.onActivityPreCreated(activity, savedInstanceState)
        log.debug("onActivityPreCreated $activity $savedInstanceState")
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        log.debug("onActivityCreated $activity $savedInstanceState")
        if (activity is FragmentActivity) {
            activity.supportFragmentManager.registerFragmentLifecycleCallbacks(this, true)
        }
    }

    override fun onActivityPostCreated(activity: Activity, savedInstanceState: Bundle?) {
        super.onActivityPostCreated(activity, savedInstanceState)
        log.debug("onActivityPostCreated $activity $savedInstanceState")
    }

    override fun onActivityPostStarted(activity: Activity) {
        super.onActivityPostStarted(activity)
        log.debug("onActivityPostStarted $activity")
    }

    override fun onActivityStarted(activity: Activity) {
        log.debug("onActivityStarted $activity")
    }

    override fun onActivityPreResumed(activity: Activity) {
        super.onActivityPreResumed(activity)
        log.debug("onActivityPreResumed $activity")
    }

    override fun onActivityResumed(activity: Activity) {
        log.debug("onActivityResumed $activity")
    }

    override fun onActivityPostResumed(activity: Activity) {
        super.onActivityPostResumed(activity)
        log.debug("onActivityPostResumed $activity")
    }

    override fun onActivityPrePaused(activity: Activity) {
        super.onActivityPrePaused(activity)
        log.debug("onActivityPrePaused $activity")
    }

    override fun onActivityPaused(activity: Activity) {
        log.debug("onActivityPaused $activity")
    }

    override fun onActivityPostPaused(activity: Activity) {
        super.onActivityPostPaused(activity)
        log.debug("onActivityPostPaused $activity")
    }

    override fun onActivityPreStopped(activity: Activity) {
        super.onActivityPreStopped(activity)
        log.debug("onActivityPreStopped $activity")
    }

    override fun onActivityStopped(activity: Activity) {
        log.debug("onActivityStopped $activity")
    }

    override fun onActivityPostStopped(activity: Activity) {
        super.onActivityPostStopped(activity)
        log.debug("onActivityPostStopped $activity")
    }

    override fun onActivityPreSaveInstanceState(activity: Activity, outState: Bundle) {
        super.onActivityPreSaveInstanceState(activity, outState)
        log.debug("onActivityPreSaveInstanceState $activity $outState")
    }

    override fun onActivitySaveInstanceState(activity: Activity, savedInstanceState: Bundle) {
        log.debug("onActivitySaveInstanceState $activity $savedInstanceState")
    }

    override fun onActivityPostSaveInstanceState(activity: Activity, outState: Bundle) {
        super.onActivityPostSaveInstanceState(activity, outState)
        log.debug("onActivityPostSaveInstanceState $activity $outState")
    }

    override fun onActivityPreDestroyed(activity: Activity) {
        super.onActivityPreDestroyed(activity)
        log.debug("onActivityPreDestroyed $activity")
    }

    override fun onActivityDestroyed(activity: Activity) {
        log.debug("onActivityDestroyed $activity")
        if (activity is FragmentActivity) {
            activity.supportFragmentManager.unregisterFragmentLifecycleCallbacks(this)
        }
    }

    override fun onActivityPostDestroyed(activity: Activity) {
        super.onActivityPostDestroyed(activity)
        log.debug("onActivityPostDestroyed $activity")
    }

    override fun onFragmentPreAttached(fm: FragmentManager, f: Fragment, context: Context) {
        super.onFragmentPreAttached(fm, f, context)
        log.debug("onFragmentPreAttached $fm $f $context")
    }

    override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
        super.onFragmentAttached(fm, f, context)
        log.debug("onFragmentAttached $fm $f $context")
    }

    override fun onFragmentPreCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        super.onFragmentPreCreated(fm, f, savedInstanceState)
        log.debug("onFragmentPreCreated $fm $f $savedInstanceState")
    }

    override fun onFragmentCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        super.onFragmentCreated(fm, f, savedInstanceState)
        log.debug("onFragmentCreated $fm $f $savedInstanceState")
    }

    override fun onFragmentActivityCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        super.onFragmentActivityCreated(fm, f, savedInstanceState)
        log.debug("onFragmentActivityCreated $fm $f $savedInstanceState")
    }

    override fun onFragmentViewCreated(
        fm: FragmentManager,
        f: Fragment,
        v: View,
        savedInstanceState: Bundle?,
    ) {
        super.onFragmentViewCreated(fm, f, v, savedInstanceState)
        log.debug("onFragmentViewCreated $fm $f $v $savedInstanceState")
    }

    override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
        super.onFragmentStarted(fm, f)
        log.debug("onFragmentStarted $fm $f")
    }

    override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
        super.onFragmentResumed(fm, f)
        log.debug("onFragmentResumed $fm $f")
    }

    override fun onFragmentPaused(fm: FragmentManager, f: Fragment) {
        super.onFragmentPaused(fm, f)
        log.debug("onFragmentPaused $fm $f")
    }

    override fun onFragmentStopped(fm: FragmentManager, f: Fragment) {
        super.onFragmentStopped(fm, f)
        log.debug("onFragmentStopped $fm $f")
    }

    override fun onFragmentSaveInstanceState(fm: FragmentManager, f: Fragment, outState: Bundle) {
        super.onFragmentSaveInstanceState(fm, f, outState)
        log.debug("onFragmentSaveInstanceState $fm $f $outState")
    }

    override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
        super.onFragmentViewDestroyed(fm, f)
        log.debug("onFragmentViewDestroyed $fm $f")
    }

    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
        super.onFragmentDestroyed(fm, f)
        log.debug("onFragmentDestroyed $fm $f")
    }

    override fun onFragmentDetached(fm: FragmentManager, f: Fragment) {
        super.onFragmentDetached(fm, f)
        log.debug("onFragmentDetached $fm $f")
    }

}
