package ademar.yaaa.page.locations

import ademar.yaaa.databinding.LocationViewHolderBinding
import androidx.recyclerview.widget.RecyclerView

class LocationsViewHolder(
    private val binding: LocationViewHolderBinding,
    private val onDeleteClick: (Long) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    private var id: Long? = null

    init {
        binding.delete.setOnClickListener {
            id?.let(onDeleteClick)
        }
    }

    fun bind(location: LocationModel) {
        id = location.id
        binding.name.text = location.name
    }

}
