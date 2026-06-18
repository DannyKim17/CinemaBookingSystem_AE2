data class Screening(
    val id: Int = 0,
    val filmId: Int,
    val hallNumber: Int,
    val date: String,
    val startTime: String,
    var totalTakings: Double = 0.0
)