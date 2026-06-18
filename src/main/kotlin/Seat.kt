data class Seat(
    val id: Int = 0,
    val screeningId: Int,
    val seatNumber: String,
    var isAvailable: Boolean = true
)