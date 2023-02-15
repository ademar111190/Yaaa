package ademar.yaaa.page.appointments

import ademar.yaaa.R
import ademar.yaaa.databinding.AppointmentsActivityBinding
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import org.slf4j.LoggerFactory

@AndroidEntryPoint
class AppointmentsActivity : AppCompatActivity() {

    private val log = LoggerFactory.getLogger("AppointmentsActivity")
    private val viewModel by viewModels<AppointmentsViewModel>()
    private lateinit var binding: AppointmentsActivityBinding

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AppointmentsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.retryButton.setOnClickListener {
            viewModel.load()
        }

        viewModel.model.observe(this) { model ->
            log.debug("onChanged: $model")
            when (model) {
                is Initial -> onLoading()
                is Loading -> onLoading()
                is Error -> onError(model)
                is Success -> onSuccess(model)
            }
        }

        viewModel.load()
    }

    private fun onLoading() {
        binding.errorGroup.visibility = GONE
        binding.contentGroup.visibility = GONE
        binding.placeholderGroup.visibility = GONE
        binding.loadGroup.visibility = VISIBLE
    }

    private fun onError(model: Error) {
        binding.loadGroup.visibility = GONE
        binding.contentGroup.visibility = GONE
        binding.placeholderGroup.visibility = GONE
        binding.errorGroup.visibility = VISIBLE
        binding.message.text = model.message
    }

    private fun onSuccess(model: Success) {
        binding.loadGroup.visibility = GONE
        binding.errorGroup.visibility = GONE
        if (model.appointments.isEmpty()) {
            binding.contentGroup.visibility = GONE
            binding.placeholderGroup.visibility = VISIBLE
            binding.message.setText(R.string.appointments_placeholder)
        } else {
            binding.placeholderGroup.visibility = GONE
            binding.contentGroup.visibility = VISIBLE
        }
    }

}
