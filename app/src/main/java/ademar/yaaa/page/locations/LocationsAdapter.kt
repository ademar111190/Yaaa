package ademar.yaaa.page.locations

import ademar.yaaa.databinding.LocationViewHolderBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class LocationsAdapter(
    private val onItemEditionClick: (Long) -> Unit,
    private val onItemDeleteClick: (Long) -> Unit,
) : RecyclerView.Adapter<LocationsViewHolder>() {

    private var locations = mutableListOf<LocationModel>()
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
    ): LocationsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = LocationViewHolderBinding.inflate(inflater, parent, false)
        return LocationsViewHolder(itemBinding, onItemEditionClick, onItemDeleteClick)
    }

    override fun getItemCount(): Int = locations.size

    override fun getItemId(position: Int): Long = locations[position].id

    override fun onBindViewHolder(holder: LocationsViewHolder, position: Int) {
        holder.bind(locations[position])
    }

    fun update(locations: Iterable<LocationModel>) {
        this.locations = locations.toMutableList()
    }

    private inner class DiffCallback(
        private val oldList: List<LocationModel>,
        private val newList: List<LocationModel>,
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
