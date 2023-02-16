package ademar.yaaa.page.appointments

import ademar.yaaa.databinding.AppointmentViewHolderBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class AppointmentsAdapter(
    private val onItemClick: (Long) -> Unit,
) : RecyclerView.Adapter<AppointmentsViewHolder>() {

    private var appointments = mutableListOf<AppointmentItem>()
        set(value) {
            val diffCallback = DiffCallback(field, value)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field.clear()
            field.addAll(value)
            diffResult.dispatchUpdatesTo(this)
        }

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): AppointmentsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = AppointmentViewHolderBinding.inflate(inflater, parent, false)
        return AppointmentsViewHolder(itemBinding, onItemClick)
    }

    override fun getItemCount(): Int = appointments.size

    override fun getItemId(position: Int): Long = appointments[position].id

    override fun onBindViewHolder(holder: AppointmentsViewHolder, position: Int) {
        holder.bind(appointments[position])
    }

    fun update(appointments: Iterable<AppointmentItem>) {
        this.appointments = appointments.toMutableList()
    }

    private inner class DiffCallback(
        private val oldList: List<AppointmentItem>,
        private val newList: List<AppointmentItem>,
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(
            oldItemPosition: Int,
            newItemPosition: Int,
        ): Boolean = oldList[oldItemPosition].id == newList[newItemPosition].id

        override fun areContentsTheSame(
            oldItemPosition: Int,
            newItemPosition: Int,
        ): Boolean = oldList[oldItemPosition] == newList[newItemPosition]

    }

}
