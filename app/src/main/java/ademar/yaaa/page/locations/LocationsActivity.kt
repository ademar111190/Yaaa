package ademar.yaaa.page.locations

import ademar.yaaa.R
import ademar.yaaa.databinding.LocationsActivityBinding
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import org.slf4j.LoggerFactory

@AndroidEntryPoint
class LocationsActivity : AppCompatActivity() {

    private val log = LoggerFactory.getLogger("LocationsActivity")
    private val viewModel by viewModels<LocationsViewModel>()
    private lateinit var binding: LocationsActivityBinding

    private val adapter = LocationsAdapter { id ->
        viewModel.locationDeleteTapped(id)
    }

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
            }
        }

        viewModel.load()
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
