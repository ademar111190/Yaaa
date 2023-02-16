package ademar.yaaa.usecase

import ademar.yaaa.R
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.Intent.*
import android.content.res.Resources
import android.net.Uri
import androidx.core.content.FileProvider.getUriForFile
import dagger.Reusable
import org.slf4j.LoggerFactory
import java.io.File
import javax.inject.Inject

@Reusable
class ShareLogs @Inject constructor(
    private val resources: Resources,
) {

    private val log = LoggerFactory.getLogger("ShareLogs")

    @SuppressLint("SdCardPath")
    fun shareLogs(context: Activity) {
        val logFilePath = "/data/data/ademar.yaaa/files/log.txt"

        val fileUri: Uri
        try {
            fileUri = getUriForFile(context, "ademar.yaaa.fileprovider", File(logFilePath))
        } catch (e: IllegalArgumentException) {
            log.error("The selected file can't be shared:", e)
            return
        }

        val shareTitle = resources.getString(R.string.share_logs_title)
        val shareMessage = resources.getString(R.string.share_logs_message)

        context.startActivity(createChooser(Intent(ACTION_SEND).apply {
            type = "text/plain"
            putExtra(EXTRA_STREAM, fileUri)
            putExtra(EXTRA_SUBJECT, shareTitle)
            putExtra(EXTRA_TEXT, shareMessage)
        }, shareTitle))
    }

}
