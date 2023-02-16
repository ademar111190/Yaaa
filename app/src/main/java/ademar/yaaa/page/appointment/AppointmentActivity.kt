package ademar.yaaa.page.appointment

import ademar.yaaa.R
import ademar.yaaa.databinding.AppointmentActivityBinding
import android.content.Context
import android.content.Intent
import android.text.format.DateFormat.is24HourFormat
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import org.slf4j.LoggerFactory

@AndroidEntryPoint
class AppointmentActivity : AppCompatActivity() {

    private val log = LoggerFactory.getLogger("AppointmentActivity")
    private val viewModel by viewModels<AppointmentViewModel>()
    private lateinit var binding: AppointmentActivityBinding
    private var descriptionInEdition = false

    private val spinnerAdapter by lazy {
        AppointmentLocationsSpinnerAdapter(this) { newLocation ->
            viewModel.updateLocation(newLocation)
        }
    }

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AppointmentActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener {
            viewModel.back()
        }
        binding.description.addTextChangedListener(
            beforeTextChanged = { _, _, _, _ -> descriptionInEdition = true },
            onTextChanged = { text, _, _, _ -> viewModel.updateDescription(text.toString()) },
            afterTextChanged = { descriptionInEdition = false },
        )
        binding.locations.apply {
            adapter = spinnerAdapter
            onItemSelectedListener = spinnerAdapter
        }
        binding.date.setOnClickListener {
            viewModel.changeDate()
        }
        binding.hour.setOnClickListener {
            viewModel.changeHour()
        }
        binding.save.setOnClickListener {
            viewModel.save()
        }

        viewModel.model.observe(this) { model ->
            log.debug("onModel: $model")
            when (model) {
                is Initial -> onLoading()
                is Loading -> onLoading()
                is Error -> onError(model)
                is Success -> onSuccess(model)
            }
        }

        viewModel.command.observe(this) { command ->
            log.debug("onCommand: $command")
            when (command) {
                is NavigateBack -> finish()
                is NavigateToDatePicker -> onDatePicker(command)
                is NavigateToTimePicker -> onTimePicker(command)
                is AnnounceSaveSuccess -> onAnnounceSaveSuccess(command)
            }
        }

        viewModel.load(
            appointmentId = intent.getLongExtra(APPOINTMENT_ID, -1L).takeIf { it != -1L },
        )
    }

    private fun onDatePicker(command: NavigateToDatePicker) {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(R.string.appointment_select_date)
            .setSelection(command.date.time)
            .build()
        datePicker.addOnPositiveButtonClickListener { date ->
            viewModel.dateChanged(date)
        }
        datePicker.show(supportFragmentManager, "datePicker")
    }

    private fun onTimePicker(command: NavigateToTimePicker) {
        val timePicker = MaterialTimePicker.Builder()
            .setTimeFormat(if (is24HourFormat(this)) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H)
            .setHour(command.hour)
            .setMinute(command.minute)
            .setTitleText(R.string.appointment_select_time)
            .build()
        timePicker.addOnPositiveButtonClickListener {
            viewModel.timeChanged(timePicker.hour, timePicker.minute)
        }
        timePicker.show(supportFragmentManager, "timePicker")
    }

    private fun onAnnounceSaveSuccess(command: AnnounceSaveSuccess) {
        val snackbar = Snackbar.make(
            binding.root,
            command.message,
            Snackbar.LENGTH_LONG,
        )
        snackbar.setAction(command.action) {
            viewModel.back()
        }
        snackbar.show()
    }

    private fun onLoading() {
        binding.contentGroup.visibility = GONE
        binding.errorGroup.visibility = GONE
        binding.loadGroup.visibility = VISIBLE
    }

    private fun onError(model: Error) {
        binding.loadGroup.visibility = GONE
        binding.contentGroup.visibility = GONE
        binding.errorGroup.visibility = VISIBLE
        binding.message.text = model.message
    }

    private fun onSuccess(model: Success) {
        binding.loadGroup.visibility = GONE
        binding.errorGroup.visibility = GONE
        binding.contentGroup.visibility = VISIBLE

        if (!descriptionInEdition) {
            binding.description.setText(model.description)
        }
        spinnerAdapter.updateLocations(model.locationOptions)
        if (model.locationIndex >= 0) {
            binding.locations.setSelection(model.locationIndex)
        }
        binding.date.text = model.date
        binding.hour.text = model.hour
        binding.save.text = model.saveLabel

        when (model.saveStatus) {
            SaveStatus.SAVED -> {
                binding.save.apply {
                    isEnabled = false
                    visibility = VISIBLE
                }
                binding.saveLoad.visibility = GONE
                binding.description.isEnabled = true
                binding.locations.isEnabled = true
                binding.date.isEnabled = true
                binding.hour.isEnabled = true
            }
            SaveStatus.SAVING -> {
                binding.save.apply {
                    isEnabled = false
                    visibility = GONE
                }
                binding.saveLoad.visibility = VISIBLE
                binding.description.isEnabled = false
                binding.locations.isEnabled = false
                binding.date.isEnabled = false
                binding.hour.isEnabled = false
            }
            SaveStatus.NOT_SAVED -> {
                binding.save.apply {
                    isEnabled = true
                    visibility = VISIBLE
                }
                binding.saveLoad.visibility = GONE
                binding.description.isEnabled = true
                binding.locations.isEnabled = true
                binding.date.isEnabled = true
                binding.hour.isEnabled = true
            }
        }
    }

    companion object {

        private const val APPOINTMENT_ID = "appointment_id"

        fun newIntent(
            context: Context,
            appointmentId: Long? = null,
        ) = Intent(context, AppointmentActivity::class.java).apply {
            if (appointmentId != null) {
                putExtra(APPOINTMENT_ID, appointmentId)
            }
        }

    }

}
