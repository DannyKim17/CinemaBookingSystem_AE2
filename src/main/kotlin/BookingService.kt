fun bookTicket(
    user: User,
    films: List<Film>,
    screenings: List<Screening>,
    offers: List<SpecialOffer>,
    bookings: MutableList<Booking>,
    databaseManager: DatabaseManager
)
{
    println("\n===== Book a Ticket =====")

    films.forEachIndexed { index, film ->
        println("${index + 1}. ${film.title} (${film.genre}) - £${film.basePrice}")
    }

    print("Choose a film (1-${films.size}): ")
    val filmChoice = readLine()?.toIntOrNull()

    if (filmChoice == null || filmChoice !in 1..films.size) {
        println("Invalid film choice.")
        return
    }

    val selectedFilm = films[filmChoice - 1]

    val filmScreenings = screenings.filter {
        it.filmId == selectedFilm.id
    }

    if (filmScreenings.isEmpty()) {
        println("No screenings available.")
        return
    }

    println("\nAvailable screenings:")
    filmScreenings.forEachIndexed { index, screening ->
        println(
            "${index + 1}. Hall ${screening.hallNumber} | " +
                    "${screening.date} | ${screening.startTime}"
        )
    }

    print("Choose screening: ")
    val screeningChoice = readLine()?.toIntOrNull()

    if (screeningChoice == null || screeningChoice !in 1..filmScreenings.size) {
        println("Invalid screening.")
        return
    }

    val selectedScreening = filmScreenings[screeningChoice - 1]

    val availableSeats =
        databaseManager
            .getSeatsForScreening(selectedScreening.id)
            .filter { it.isAvailable }

    if (availableSeats.isEmpty()) {
        println("No seats available.")
        return
    }

    println("\nAvailable Seats:")

    availableSeats.forEachIndexed { index, seat ->
        println("${index + 1}. ${seat.seatNumber}")
    }

    print("Choose seat: ")

    val seatChoice =
        readLine()?.toIntOrNull()

    if (
        seatChoice == null ||
        seatChoice !in 1..availableSeats.size
    ) {
        println("Invalid seat.")
        return
    }

    val selectedSeat =
        availableSeats[seatChoice - 1]

    print("Number of tickets: ")
    val numberOfTickets = readLine()?.toIntOrNull() ?: 1

    print("Child ticket? (yes/no): ")
    val isChild = readLine()?.trim()?.lowercase() == "yes"

    val totalPrice = applyOffers(
        selectedFilm.basePrice,
        selectedScreening,
        numberOfTickets,
        isChild,
        offers
    )

    println("Total price: £${"%.2f".format(totalPrice)}")

    print("Enter payment amount: £")

    val payment =
        readLine()?.toDoubleOrNull() ?: 0.0

    if (payment < totalPrice) {
        println("Insufficient funds.")
        return
    }

    val booking = Booking(
        id = bookings.size + 1,
        userId = user.id,
        screeningId = selectedScreening.id,
        totalPrice = totalPrice
    )

    bookings.add(booking)

    databaseManager.saveBooking(
        user.id,
        selectedScreening.id,
        totalPrice
    )

    databaseManager.updateScreeningTakings(
        selectedScreening.id,
        totalPrice
    )

    databaseManager.updateSeatAvailability(
        selectedSeat.id,
        false
    )

    println("Booking successful!")
}

fun viewBookings(
    user: User,
    bookings: List<Booking>
) {
    println("\n===== Your Bookings =====")

    val userBookings = bookings.filter {
        it.userId == user.id
    }

    if (userBookings.isEmpty()) {
        println("You have no bookings yet.")
        return
    }

    userBookings.forEach {
        println(
            "Booking ID: ${it.id} | " +
                    "Screening ID: ${it.screeningId} | " +
                    "Price: £${"%.2f".format(it.totalPrice)}"
        )
    }
}