package ademar.yaaa

import ademar.yaaa.page.appointment.AppointmentActivity
import ademar.yaaa.page.appointments.AppointmentsActivity
import ademar.yaaa.page.locations.LocationsActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when (intent?.action) {
            "ademar.yaaa.appointments" -> startAppointmentsActivity()
            "ademar.yaaa.create_appointment" -> startCreateAppointmentActivity()
            "ademar.yaaa.edit_locations" -> startEditLocationsActivity()
            else -> startAppointmentsActivity()
        }

        finish()
    }

    private fun startAppointmentsActivity() {
        startActivity(AppointmentsActivity.newIntent(this))
    }

    private fun startCreateAppointmentActivity() {
        startAppointmentsActivity()
        startActivity(AppointmentActivity.newIntent(this))
    }

    private fun startEditLocationsActivity() {
        startAppointmentsActivity()
        startActivity(LocationsActivity.newIntent(this))
    }

}
