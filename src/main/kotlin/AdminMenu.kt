// admin functions for member B - tasks 4, 5 and 6

fun viewFilmsAndScreenings(films: List<Film>, screenings: List<Screening>) {
    println("===== Film and Screening Info =====")
    films.forEachIndexed { index, film ->
        println("Film ${index + 1}:")
        println("Title: ${film.title}")
        println("Genre: ${film.genre}")
        println("Price: £${film.basePrice}")
        println("Tickets Sold: ${film.totalTicketsSold}")
        println("Screenings:")

        // filtering only screenings that belong to this film
        val filmScreenings = screenings.filter { it.film == film }
        for (screening in filmScreenings) {
            println("  - Hall ${screening.hallNumber} | ${screening.date} | ${screening.startTime}")
        }
        println("-----------------------------------")
    }
}

fun addFilmAndScreening(films: MutableList<Film>, screenings: MutableList<Screening>) {
    println("===== Add New Film =====")
    print("Enter film title: ")
    val title = readLine() ?: ""
    // added this just in case someone hits enter without typing then they get a warning
    if (title.isEmpty()) {
        println("Warning: film title is empty!")
    }
    print("Enter genre: ")
    val genre = readLine() ?: ""
    print("Enter base ticket price: ")
    val price = readLine()?.toDoubleOrNull() ?: 0.0

    val newFilm = Film(title, genre, price)
    films.add(newFilm)

    print("Enter screening date (e.g. 2026-04-01): ")
    val date = readLine() ?: ""
    print("Enter screening time (e.g. 14:00): ")
    val time = readLine() ?: ""
    print("Enter hall number: ")
    val hall = readLine()?.toIntOrNull() ?: 1

    val newScreening = Screening(newFilm, hall, date, time, 0.0, makeSeats())
    screenings.add(newScreening)

    // a summary so admin can confirm what was added
    println("Film and screening added successfully!")
    println("--- Added Film Details ---")
    println("Title: ${newFilm.title}")
    println("Genre: ${newFilm.genre}")
    println("Price: £${newFilm.basePrice}")
    println("Screening: Hall $hall | $date | $time")
}

fun modifyTicketPricing(films: MutableList<Film>) {
    println("===== Modify Ticket Pricing =====")
    println("Enter a percentage to adjust all prices (e.g. 10 for +10%, -10 for -10%): ")
    val percentage = readLine()?.toDoubleOrNull()

    if (percentage == null) {
        println("Invalid input, please enter a number.")
        return
    }

    // calculates  the new price factor and applies to all films
    val factor = 1 + (percentage / 100)
    for (film in films) {
        film.basePrice = film.basePrice * factor
        println("${film.title} new price: £${"%.2f".format(film.basePrice)}")
    }
    println("All prices updated successfully!")
}