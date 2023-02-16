package ademar.yaaa.page.locations

import ademar.yaaa.R
import ademar.yaaa.databinding.AddLocationDialogBinding
import ademar.yaaa.databinding.LocationsActivityBinding
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.LENGTH_LONG
import dagger.hilt.android.AndroidEntryPoint
import org.slf4j.LoggerFactory

@AndroidEntryPoint
class LocationsActivity : AppCompatActivity() {

    private val log = LoggerFactory.getLogger("LocationsActivity")
    private val viewModel by viewModels<LocationsViewModel>()
    private lateinit var binding: LocationsActivityBinding

    private val adapter = LocationsAdapter({ id ->
        viewModel.locationEditionTapped(id)
    }, { id ->
        viewModel.locationDeleteTapped(id)
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LocationsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener {
            viewModel.back()
        }
        binding.retryButton.setOnClickListener {
            viewModel.load()
        }
        binding.addButton.setOnClickListener {
            viewModel.add()
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this, VERTICAL))

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
                is NavigateToAddLocation -> openAddLocationDialog(command)
                is NavigateToEditLocation -> openEditLocationDialog(command)
                is AnnounceSaveError -> onAnnounceSaveError(command)
                is AnnounceSaveSuccess -> onAnnounceSaveSuccess(command)
                is AnnounceDeleteError -> simpleFeedback(command.message)
                is AnnounceDeleteSuccess -> onAnnounceDeleteSuccess(command)
                is AnnounceUndoResult -> simpleFeedback(command.message)
                is AnnounceEditionSuccess -> simpleFeedback(command.message)
                is AnnounceEditionError -> simpleFeedback(command.message)
            }
        }

        viewModel.load()
    }

    private fun openAddLocationDialog(command: NavigateToAddLocation) {
        val inflater = LayoutInflater.from(this)
        val dialogBinding = AddLocationDialogBinding.inflate(inflater)
        dialogBinding.name.setText(command.name)

        AlertDialog.Builder(this)
            .setTitle(R.string.add_location_title)
            .setView(dialogBinding.root)
            .setPositiveButton(R.string.add_location_save) { _, _ ->
                val name = dialogBinding.name.text.toString()
                viewModel.addLocation(name)
            }
            .setNegativeButton(R.string.add_location_cancel) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun openEditLocationDialog(command: NavigateToEditLocation) {
        val inflater = LayoutInflater.from(this)
        val dialogBinding = AddLocationDialogBinding.inflate(inflater)
        dialogBinding.name.setText(command.name)

        AlertDialog.Builder(this)
            .setTitle(R.string.add_location_title)
            .setView(dialogBinding.root)
            .setPositiveButton(R.string.add_location_save) { _, _ ->
                val name = dialogBinding.name.text.toString()
                viewModel.locationEdited(name)
            }
            .setNegativeButton(R.string.add_location_cancel) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun onAnnounceSaveError(command: AnnounceSaveError) {
        val snackbar = Snackbar.make(binding.root, command.message, LENGTH_LONG)
        snackbar.setAction(command.action) {
            viewModel.errorAction()
        }
        snackbar.show()
    }

    private fun onAnnounceSaveSuccess(command: AnnounceSaveSuccess) {
        val snackbar = Snackbar.make(binding.root, command.message, LENGTH_LONG)
        snackbar.setAction(command.action) {
            viewModel.savedAction()
        }
        snackbar.show()
    }

    private fun onAnnounceDeleteSuccess(command: AnnounceDeleteSuccess) {
        val snackbar = Snackbar.make(binding.root, command.message, LENGTH_LONG)
        snackbar.setAction(command.action) {
            viewModel.deletedAction()
        }
        snackbar.show()
    }

    private fun simpleFeedback(message: String) {
        Snackbar.make(binding.root, message, LENGTH_LONG).show()
    }

    private fun onLoading() {
        binding.errorGroup.visibility = View.GONE
        binding.contentGroup.visibility = View.GONE
        binding.placeholderGroup.visibility = View.GONE
        binding.loadGroup.visibility = View.VISIBLE
    }

    private fun onError(model: Error) {
        binding.loadGroup.visibility = View.GONE
        binding.contentGroup.visibility = View.GONE
        binding.placeholderGroup.visibility = View.GONE
        binding.errorGroup.visibility = View.VISIBLE
        binding.message.text = model.message
    }

    private fun onSuccess(model: Success) {
        binding.loadGroup.visibility = View.GONE
        binding.errorGroup.visibility = View.GONE
        if (model.locations.isEmpty()) {
            binding.contentGroup.visibility = View.GONE
            binding.placeholderGroup.visibility = View.VISIBLE
            binding.message.setText(R.string.locations_placeholder)
        } else {
            binding.placeholderGroup.visibility = View.GONE
            binding.contentGroup.visibility = View.VISIBLE
        }
        adapter.update(model.locations)
    }

    companion object {

        fun newIntent(
            context: Context,
        ) = Intent(context, LocationsActivity::class.java)

    }

}
