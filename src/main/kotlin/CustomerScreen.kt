class CustomerScreen {

    fun showMenu() {
        println("\n===== Customer Menu =====")
        println("1. View Films")
        println("2. View Screenings")
        println("3. Book Ticket")
        println("4. View Bookings")
        println("0. Logout")
    }

    fun viewFilms(
        films: List<Film>
    ) {

        println("\n===== Films =====")

        films.forEach {
            println(
                "${it.id}. ${it.title} | " +
                        "${it.genre} | " +
                        "£${it.basePrice}"
            )
        }
    }

    fun viewScreenings(
        screenings: List<Screening>,
        films: List<Film>
    ) {

        println("\n===== Screenings =====")

        screenings.forEach { screening ->

            val film =
                films.find {
                    it.id == screening.filmId
                }

            println(
                "${screening.id}. " +
                        "${film?.title} | " +
                        "${screening.date} | " +
                        "${screening.startTime} | " +
                        "Hall ${screening.hallNumber}"
            )
        }
    }
}