class AdminScreen {

    fun viewFilmsAndScreenings(
        films: List<Film>,
        screenings: List<Screening>
    ) {
        println("\n===== Films and Screenings =====")

        films.forEach { film ->
            println("\n${film.title} (${film.genre}) - £${film.basePrice}")

            screenings.filter { it.filmId == film.id }
                .forEach {
                    println(
                        "Hall ${it.hallNumber} | " +
                                "${it.date} | ${it.startTime}"
                    )
                }
        }
    }
}