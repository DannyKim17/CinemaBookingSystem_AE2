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
    fun addFilmAndScreening(
        films: MutableList<Film>,
        screenings: MutableList<Screening>
    ) {
        println("===== Add New Film =====")

        print("Enter film title: ")
        val title = readLine() ?: ""

        if (title.isEmpty()) {
            println("Film title cannot be empty.")
            return
        }

        print("Enter genre: ")
        val genre = readLine() ?: ""

        print("Enter base ticket price: ")
        val price = readLine()?.toDoubleOrNull() ?: 0.0

        val newFilm = Film(
            id = films.size + 1,
            title = title,
            genre = genre,
            basePrice = price
        )

        films.add(newFilm)

        print("Enter screening date (e.g. 2026-04-01): ")
        val date = readLine() ?: ""

        print("Enter screening time (e.g. 14:00): ")
        val time = readLine() ?: ""

        print("Enter hall number: ")
        val hall = readLine()?.toIntOrNull() ?: 1

        val newScreening = Screening(
            id = screenings.size + 1,
            filmId = newFilm.id,
            hallNumber = hall,
            date = date,
            startTime = time
        )

        screenings.add(newScreening)

        println("Film and screening added successfully!")
    }

    fun modifyTicketPricing(
        films: MutableList<Film>
    ) {
        println("===== Modify Ticket Pricing =====")
        println("Enter a percentage to adjust all prices (e.g. 10 for +10%, -10 for -10%): ")
        val percentage = readLine()?.toDoubleOrNull()

        if (percentage == null) {
            println("Invalid input, please enter a number.")
            return
        }

        // this wil calculate the new price factor and apply to all films
        val factor = 1 + (percentage / 100)
        for (film in films) {
            film.basePrice = film.basePrice * factor
            println("${film.title} new price: £${"%.2f".format(film.basePrice)}")
        }
        println("All prices updated successfully!")
    }

    fun searchFilmsByGenre(
        films: List<Film>,
        screenings: List<Screening>
    ) {
        print("Enter genre to search for: ")
        val genre = readLine() ?: ""
        val results = films.filter { it.genre.lowercase() == genre.lowercase() }
        if (results.isEmpty()) {
            println("No films found for genre: $genre")
        } else {
            println("===== Films in genre: $genre =====")
            results.forEachIndexed { index, film ->
                println("Film ${index + 1}:")
                film.displayInfo()
                val filmScreenings = screenings.filter {
                    it.filmId == film.id
                }
                for (screening in filmScreenings) {
                    println("  - Hall ${screening.hallNumber} | ${screening.date} | ${screening.startTime}")
                }
                println("-----------------------------------")
            }
        }
    }

    fun manageSpecialOffers(
        offers: MutableList<SpecialOffer>
    ) {
        println("===== Manage Special Offers =====")

        offers.forEachIndexed { index, offer ->
            val status =
                if (offer.isEnabled) "ENABLED"
                else "DISABLED"

            println("${index + 1}. ${offer.name} [$status]")
            println("   ${offer.description}")
        }

        print(
            "Choose offer to toggle (1-${offers.size}), " +
                    "or 0 to go back: "
        )

        val choice = readLine()?.toIntOrNull()

        if (choice == null) {
            println("Invalid choice.")
            return
        }

        if (choice == 0) {
            return
        }

        if (choice < 1 || choice > offers.size) {
            println("Invalid choice.")
            return
        }

        val selectedOffer = offers[choice - 1]

        selectedOffer.isEnabled = !selectedOffer.isEnabled

        println(
            "${selectedOffer.name} is now ${
                if (selectedOffer.isEnabled)
                    "ENABLED"
                else
                    "DISABLED"
            }"
        )
    }
}