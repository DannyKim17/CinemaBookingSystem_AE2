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