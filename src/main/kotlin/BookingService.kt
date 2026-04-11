fun bookTicket(
    user: User,
    films: List<Film>,
    screenings: List<Screening>,
    offers: List<SpecialOffer>,
    bookings: MutableList<Booking>
) {
    println("\n===== Book a Ticket =====")

    films.forEachIndexed { index, film ->
        println("${index + 1}. ${film.title} (${film.genre}) - £${"%.2f".format(film.basePrice)}")
    }
    print("Choose a film (1-${films.size}): ")
    val filmChoice = readLine()?.toIntOrNull()

    if (filmChoice == null || filmChoice < 1 || filmChoice > films.size) {
        println("Invalid film choice.")
        return
    }

    val selectedFilm = films[filmChoice - 1]

    val filmScreenings = screenings.filter { it.film == selectedFilm }
    if (filmScreenings.isEmpty()) {
        println("No screenings available for this film.")
        return
    }

    println("\nAvailable screenings for ${selectedFilm.title}:")
    filmScreenings.forEachIndexed { index, screening ->
        println("${index + 1}. Hall ${screening.hallNumber} | ${screening.date} | ${screening.startTime}")
    }
    print("Choose a screening (1-${filmScreenings.size}): ")
    val screeningChoice = readLine()?.toIntOrNull()

    if (screeningChoice == null || screeningChoice < 1 || screeningChoice > filmScreenings.size) {
        println("Invalid screening choice.")
        return
    }

    val selectedScreening = filmScreenings[screeningChoice - 1]

    print("Enter number of tickets: ")
    val numberOfTickets = readLine()?.toIntOrNull() ?: 1

    if (numberOfTickets < 1) {
        println("Invalid number of tickets.")
        return
    }

    val availableSeats = selectedScreening.seats.filter { it.isAvailable }
    if (availableSeats.size < numberOfTickets) {
        println("Not enough seats available. Only ${availableSeats.size} seats left.")
        return
    }

    print("Is this a child ticket? (yes/no): ")
    val isChild = readLine()?.trim()?.lowercase() == "yes"

    // applyOffers now returns TOTAL price for all tickets
    val totalPrice = applyOffers(selectedFilm.basePrice, selectedScreening, numberOfTickets, isChild, offers)

    // Virtual payment as required by brief
    println("\nTotal amount due: £${"%.2f".format(totalPrice)}")
    print("Enter payment amount: £")
    val payment = readLine()?.toDoubleOrNull() ?: 0.0

    if (payment < totalPrice) {
        println("Insufficient funds. Payment of £${"%.2f".format(totalPrice)} required.")
        return
    }

    val change = payment - totalPrice

    // Complete the booking
    val bookedSeats = availableSeats.take(numberOfTickets)
    bookedSeats.forEach { it.isAvailable = false }
    selectedFilm.totalTicketsSold += numberOfTickets
    selectedScreening.totalTakings += totalPrice

    val booking = Booking(user, selectedScreening, bookedSeats, totalPrice)
    bookings.add(booking)

    // Ticket format exactly as specified in brief
    println("\n*******************************")
    println("SUNSHINE CINEMA")
    println("Film: ${selectedFilm.title}")
    println("Date: ${selectedScreening.date}")
    println("Time: ${selectedScreening.startTime}")
    println("Seats: ${bookedSeats.joinToString(", ") { it.seatNumber }}")
    println("Price: £${"%.2f".format(totalPrice)}")
    println("*******************************")

    if (change > 0) println("Change: £${"%.2f".format(change)}")
}

fun viewBookings(user: User, bookings: List<Booking>) {
    println("\n===== Your Bookings =====")
    val userBookings = bookings.filter { it.user == user }
    if (userBookings.isEmpty()) {
        println("You have no bookings yet.")
        return
    }
    userBookings.forEachIndexed { index, booking ->
        println("\nBooking ${index + 1}:")
        println("*******************************")
        println("SUNSHINE CINEMA")
        println("Film: ${booking.screening.film.title}")
        println("Date: ${booking.screening.date}")
        println("Time: ${booking.screening.startTime}")
        println("Hall: ${booking.screening.hallNumber}")
        println("Seats: ${booking.seats.joinToString(", ") { it.seatNumber }}")
        println("Price: £${"%.2f".format(booking.totalPrice)}")
        println("*******************************")
    }
}