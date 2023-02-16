package ademar.yaaa.page.appointments

import ademar.yaaa.R
import ademar.yaaa.databinding.AppointmentsActivityBinding
import ademar.yaaa.page.appointment.AppointmentActivity
import ademar.yaaa.page.locations.LocationsActivity
import android.content.Intent
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import org.slf4j.LoggerFactory

@AndroidEntryPoint
class AppointmentsActivity : AppCompatActivity() {

    private val log = LoggerFactory.getLogger("AppointmentsActivity")
    private val viewModel by viewModels<AppointmentsViewModel>()
    private lateinit var binding: AppointmentsActivityBinding

    private val adapter = AppointmentsAdapter { id ->
        viewModel.appointmentTapped(id)
    }

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AppointmentsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.edit_locations -> {
                    viewModel.editLocations()
                    true
                }
                R.id.share_logs -> {
                    viewModel.shareLogs(this)
                    true
                }
                else -> false
            }
        }
        binding.retryButton.setOnClickListener {
            viewModel.load()
        }
        binding.addButton.setOnClickListener {
            viewModel.add()
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

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
                is NavigateToAppointmentCreation -> startActivity(AppointmentActivity.newIntent(this))
                is NavigateToAppointmentDetails -> startActivity(AppointmentActivity.newIntent(this, command.id))
                is NavigateToLocations -> startActivity(LocationsActivity.newIntent(this))
            }
        }

        viewModel.load()
    }

    override fun onResume() {
        super.onResume()
        viewModel.checkChanges()
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
        adapter.update(model.appointments)
    }

    companion object {

        fun newIntent(
            context: android.content.Context,
        ) = Intent(context, AppointmentsActivity::class.java)

    }

}
