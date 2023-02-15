package ademar.yaaa.page.appointment

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter

class AppointmentLocationsSpinnerAdapter(
    context: Context,
    private val onItemSelected: (String?) -> Unit,
) : ArrayAdapter<String>(context, android.R.layout.simple_spinner_item), AdapterView.OnItemSelectedListener {

    private val locations = mutableSetOf<String>()

    init {
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    }

    fun updateLocations(newLocations: Set<String>) {
        val addedItems = newLocations - locations
        val removedItems = locations - newLocations

        for (item in removedItems) {
            remove(item)
        }

        locations.clear()
        locations.addAll(newLocations)

        for (item in addedItems) {
            insert(item, locations.indexOf(item))
        }

        notifyDataSetChanged()
    }

    override fun onItemSelected(adapter: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        onItemSelected(getItem(pos))
    }

    override fun onNothingSelected(adapter: AdapterView<*>?) {
        onItemSelected(null)
    }

}
