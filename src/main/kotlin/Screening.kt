// single showing of a film and also the time in a specific hall
class Screening(
    val film: Film,
    val hallNumber: Int,
    val date: String,
    val startTime: String, // e.g., "10:30"
    var totalTakings: Double = 0.0,
    val seats: List<Seat> = mutableListOf()
)