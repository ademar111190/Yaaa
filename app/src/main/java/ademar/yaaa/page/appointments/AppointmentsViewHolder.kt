package ademar.yaaa.page.appointments

import ademar.yaaa.databinding.AppointmentViewHolderBinding
import androidx.recyclerview.widget.RecyclerView

class AppointmentsViewHolder(
    private val binding: AppointmentViewHolderBinding,
    private val onClick: (Long) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    private var id: Long? = null

    init {
        binding.root.setOnClickListener {
            id?.let(onClick)
        }
    }

    fun bind(appointment: AppointmentItem) {
        id = appointment.id
        binding.hour.text = appointment.hour
        binding.date.text = appointment.date
        binding.location.text = appointment.location
        binding.description.text = appointment.description
    }

}
