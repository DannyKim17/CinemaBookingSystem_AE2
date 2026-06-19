import javax.swing.JOptionPane

fun bookTicket(
    user: User,
    films: List<Film>,
    screenings: List<Screening>,
    offers: List<SpecialOffer>,
    bookings: MutableList<Booking>,
    databaseManager: DatabaseManager
) {

    val filmList = buildString {
        films.forEachIndexed { index, film ->
            append("${index + 1}. ${film.title} (${film.genre}) - £${film.basePrice}\n")
        }
    }

    val filmChoice = JOptionPane.showInputDialog(
        null,
        "Choose a Film:\n\n$filmList"
    )?.toIntOrNull()

    if (filmChoice == null || filmChoice !in 1..films.size) {
        JOptionPane.showMessageDialog(null, "Invalid film choice")
        return
    }

    val selectedFilm = films[filmChoice - 1]

    val filmScreenings = screenings.filter {
        it.filmId == selectedFilm.id
    }

    if (filmScreenings.isEmpty()) {
        JOptionPane.showMessageDialog(null, "No screenings available")
        return
    }

    val screeningList = buildString {
        filmScreenings.forEachIndexed { index, screening ->
            append(
                "${index + 1}. Hall ${screening.hallNumber} | " +
                        "${screening.date} | ${screening.startTime}\n"
            )
        }
    }

    val screeningChoice = JOptionPane.showInputDialog(
        null,
        "Choose Screening:\n\n$screeningList"
    )?.toIntOrNull()

    if (screeningChoice == null ||
        screeningChoice !in 1..filmScreenings.size
    ) {
        JOptionPane.showMessageDialog(null, "Invalid screening")
        return
    }

    val selectedScreening =
        filmScreenings[screeningChoice - 1]

    val availableSeats =
        databaseManager
            .getSeatsForScreening(selectedScreening.id)
            .filter { it.isAvailable }

    if (availableSeats.isEmpty()) {
        JOptionPane.showMessageDialog(null, "No seats available")
        return
    }
    val seatList = buildString {

        availableSeats.chunked(5).forEach { row ->

            row.forEach { seat ->

                append(
                    seat.seatNumber.padEnd(6)
                )
            }

            append("\n")
        }
    }
    val seatChoice = JOptionPane.showInputDialog(
        null,
        "Available Seats:\n\n$seatList\n\nEnter seat number:"
    )

    val selectedSeat =
        availableSeats.find {
            it.seatNumber.equals(
                seatChoice,
                ignoreCase = true
            )
        }

    if (selectedSeat == null) {

        JOptionPane.showMessageDialog(
            null,
            "Invalid seat"
        )
        return
    }

    val numberOfTickets =
        JOptionPane.showInputDialog(
            null,
            "Number of tickets:"
        )?.toIntOrNull() ?: 1

    val childAnswer =
        JOptionPane.showInputDialog(
            null,
            "Child ticket? (yes/no)"
        )

    val isChild =
        childAnswer?.lowercase() == "yes"

    val totalPrice = applyOffers(
        selectedFilm.basePrice,
        selectedScreening,
        numberOfTickets,
        isChild,
        offers
    )

    JOptionPane.showMessageDialog(
        null,
        "Total Price: £${"%.2f".format(totalPrice)}"
    )

    val payment =
        JOptionPane.showInputDialog(
            null,
            "Enter payment amount:"
        )?.toDoubleOrNull() ?: 0.0

    if (payment < totalPrice) {
        JOptionPane.showMessageDialog(
            null,
            "Insufficient funds"
        )
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

    JOptionPane.showMessageDialog(
        null,
        "Booking Successful!"
    )
}

fun viewBookings(
    user: User,
    bookings: List<Booking>
) {

    val userBookings =
        bookings.filter {
            it.userId == user.id
        }

    if (userBookings.isEmpty()) {

        JOptionPane.showMessageDialog(
            null,
            "You have no bookings yet."
        )
        return
    }

    val bookingText = buildString {

        userBookings.forEach {

            append(
                "Booking ID: ${it.id} | " +
                        "Screening ID: ${it.screeningId} | " +
                        "Price: £${"%.2f".format(it.totalPrice)}\n"
            )
        }
    }

    JOptionPane.showMessageDialog(
        null,
        bookingText
    )
}