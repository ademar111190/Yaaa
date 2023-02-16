package ademar.yaaa.page.locations

sealed class LocationsModel

object Initial : LocationsModel()

object Loading : LocationsModel()

data class Error(
    val message: String,
) : LocationsModel()

data class Success(
    val locations: List<LocationModel>,
) : LocationsModel()

data class LocationModel(
    val id: Long,
    val name: String,
)
