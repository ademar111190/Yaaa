package ademar.yaaa

import ademar.yaaa.page.appointment.AppointmentActivity
import ademar.yaaa.page.appointments.AppointmentsActivity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when (intent?.action) {
            "ademar.yaaa.create_appointment" -> startCreateAppointmentActivity()
            "ademar.yaaa.appointments" -> startAppointmentsActivity()
            else -> startAppointmentsActivity()
        }

        finish()
    }

    private fun startCreateAppointmentActivity() {
        startAppointmentsActivity()
        startActivity(AppointmentActivity.newIntent(this))
    }

    private fun startAppointmentsActivity() {
        startActivity(Intent(this, AppointmentsActivity::class.java))
    }

}
