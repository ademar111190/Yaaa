package ademar.yaaa.page.appointment

import ademar.yaaa.databinding.AppointmentActivityBinding
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import dagger.hilt.android.AndroidEntryPoint
import org.slf4j.LoggerFactory

@AndroidEntryPoint
class AppointmentActivity : AppCompatActivity() {

    private val log = LoggerFactory.getLogger("AppointmentActivity")
    private val viewModel by viewModels<AppointmentViewModel>()
    private lateinit var binding: AppointmentActivityBinding

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AppointmentActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener {
            viewModel.back()
        }
        binding.description.addTextChangedListener { text ->
            viewModel.updateDescription(text.toString())
        }

        viewModel.model.observe(this) { model ->
            log.debug("onModel: $model")
            when (model) {
                is Initial -> onLoading()
                is Success -> onSuccess(model)
            }
        }

        viewModel.command.observe(this) { command ->
            log.debug("onCommand: $command")
            when (command) {
                is NavigateBack -> finish()
            }
        }

        viewModel.load()
    }

    private fun onLoading() {
        binding.contentGroup.visibility = GONE
        binding.loadGroup.visibility = VISIBLE
    }

    private fun onSuccess(model: Success) {
        binding.loadGroup.visibility = GONE
        binding.contentGroup.visibility = VISIBLE
        binding.description.setText(model.description)
    }

}
