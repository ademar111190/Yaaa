package ademar.yaaa

import ademar.yaaa.page.appointments.AppointmentsActivity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, AppointmentsActivity::class.java))
        finish()
    }

}
