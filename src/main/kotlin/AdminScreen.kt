import javax.swing.JOptionPane

class AdminScreen {

    fun viewFilmsAndScreenings(
        films: List<Film>,
        screenings: List<Screening>
    ) {

        val output = buildString {

            films.forEach { film ->

                append(
                    "\n${film.title} (${film.genre}) - £${film.basePrice}\n"
                )

                screenings.filter {
                    it.filmId == film.id
                }.forEach {

                    append(
                        "Hall ${it.hallNumber} | " +
                                "${it.date} | ${it.startTime}\n"
                    )
                }
            }
        }

        JOptionPane.showMessageDialog(
            null,
            output,
            "Films & Screenings",
            JOptionPane.INFORMATION_MESSAGE
        )
    }

    fun addFilmAndScreening(
        films: MutableList<Film>,
        screenings: MutableList<Screening>
    ) {

        val title =
            JOptionPane.showInputDialog(
                "Enter film title:"
            ) ?: return

        if (title.isBlank()) {

            JOptionPane.showMessageDialog(
                null,
                "Film title cannot be empty."
            )
            return
        }

        val genre =
            JOptionPane.showInputDialog(
                "Enter genre:"
            ) ?: return

        val price =
            JOptionPane.showInputDialog(
                "Enter base ticket price:"
            )?.toDoubleOrNull() ?: 0.0

        val newFilm = Film(
            id = films.size + 1,
            title = title,
            genre = genre,
            basePrice = price
        )

        films.add(newFilm)

        val date =
            JOptionPane.showInputDialog(
                "Enter screening date (2026-04-01):"
            ) ?: return

        val time =
            JOptionPane.showInputDialog(
                "Enter screening time (14:00):"
            ) ?: return

        val hall =
            JOptionPane.showInputDialog(
                "Enter hall number:"
            )?.toIntOrNull() ?: 1

        val newScreening = Screening(
            id = screenings.size + 1,
            filmId = newFilm.id,
            hallNumber = hall,
            date = date,
            startTime = time
        )

        screenings.add(newScreening)

        JOptionPane.showMessageDialog(
            null,
            "Film and screening added successfully!"
        )
    }

    fun modifyTicketPricing(
        films: MutableList<Film>
    ) {

        val percentage =
            JOptionPane.showInputDialog(
                "Enter percentage adjustment\n(10 = +10%, -10 = -10%)"
            )?.toDoubleOrNull()

        if (percentage == null) {

            JOptionPane.showMessageDialog(
                null,
                "Invalid input."
            )
            return
        }

        val factor = 1 + (percentage / 100)

        val output = buildString {

            for (film in films) {

                film.basePrice *= factor

                append(
                    "${film.title}: £${"%.2f".format(film.basePrice)}\n"
                )
            }
        }

        JOptionPane.showMessageDialog(
            null,
            output,
            "Updated Prices",
            JOptionPane.INFORMATION_MESSAGE
        )
    }

    fun searchFilmsByGenre(
        films: List<Film>,
        screenings: List<Screening>
    ) {

        val genre =
            JOptionPane.showInputDialog(
                "Enter genre:"
            ) ?: return

        val results =
            films.filter {
                it.genre.equals(
                    genre,
                    ignoreCase = true
                )
            }

        if (results.isEmpty()) {

            JOptionPane.showMessageDialog(
                null,
                "No films found."
            )
            return
        }

        val output = buildString {

            results.forEach { film ->

                append(
                    "${film.title} (${film.genre})\n"
                )

                screenings.filter {
                    it.filmId == film.id
                }.forEach {

                    append(
                        "Hall ${it.hallNumber} | " +
                                "${it.date} | ${it.startTime}\n"
                    )
                }

                append("\n")
            }
        }

        JOptionPane.showMessageDialog(
            null,
            output,
            "Search Results",
            JOptionPane.INFORMATION_MESSAGE
        )
    }

    fun manageSpecialOffers(
        offers: MutableList<SpecialOffer>
    ) {

        val offerList = buildString {

            offers.forEachIndexed { index, offer ->

                append(
                    "${index + 1}. ${offer.name} " +
                            "[${if (offer.isEnabled) "ON" else "OFF"}]\n"
                )
            }
        }

        val choice =
            JOptionPane.showInputDialog(
                null,
                "Select offer to toggle:\n\n$offerList"
            )?.toIntOrNull()

        if (choice == null ||
            choice !in 1..offers.size
        ) {
            return
        }

        val selectedOffer =
            offers[choice - 1]

        selectedOffer.isEnabled =
            !selectedOffer.isEnabled

        JOptionPane.showMessageDialog(
            null,
            "${selectedOffer.name} is now ${
                if (selectedOffer.isEnabled)
                    "ENABLED"
                else
                    "DISABLED"
            }"
        )
    }
}