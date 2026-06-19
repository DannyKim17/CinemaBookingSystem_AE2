import javax.swing.JOptionPane

class CustomerScreen {

    fun showMenu() {
        JOptionPane.showMessageDialog(
            null,
            """
            CUSTOMER MENU
            
            1. View Films
            2. View Screenings
            3. Book Ticket
            4. View Bookings
            0. Logout
            """.trimIndent()
        )
    }

    fun viewFilms(
        films: List<Film>
    ) {

        val filmText = buildString {

            films.forEach {

                append(
                    "${it.id}. ${it.title} | " +
                            "${it.genre} | " +
                            "£${it.basePrice}\n"
                )
            }
        }

        JOptionPane.showMessageDialog(
            null,
            filmText,
            "Films",
            JOptionPane.INFORMATION_MESSAGE
        )
    }

    fun viewScreenings(
        screenings: List<Screening>,
        films: List<Film>
    ) {

        val screeningText = buildString {

            screenings.forEach { screening ->

                val film =
                    films.find {
                        it.id == screening.filmId
                    }

                append(
                    "${screening.id}. " +
                            "${film?.title} | " +
                            "${screening.date} | " +
                            "${screening.startTime} | " +
                            "Hall ${screening.hallNumber}\n"
                )
            }
        }

        JOptionPane.showMessageDialog(
            null,
            screeningText,
            "Screenings",
            JOptionPane.INFORMATION_MESSAGE
        )
    }
}