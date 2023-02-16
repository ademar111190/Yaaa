package ademar.yaaa.page.locations

import ademar.yaaa.databinding.LocationsActivityBinding
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationsActivity : AppCompatActivity() {

    private lateinit var binding: LocationsActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LocationsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    companion object {

        fun newIntent(
            context: Context,
        ) = Intent(context, LocationsActivity::class.java)

    }

}
