fun viewFilmsAndScreenings(films: List<Film>, screenings: List<Screening>) {
    println("===== Film and Screening Info =====")
    for (film in films) {
        println("Title: ${film.title}")
        println("Genre: ${film.genre}")
        println("Price: £${film.basePrice}")
        println("Tickets Sold: ${film.totalTicketsSold}")
        println("Screenings:")
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

    println("Film and screening added successfully!")
}