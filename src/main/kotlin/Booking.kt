class Booking(
    val user: User,
    val screening: Screening,
    val seats: List<Seat>,
    var totalPrice: Double = 0.0
)