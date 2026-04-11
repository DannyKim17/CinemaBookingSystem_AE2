fun makeSeats() = List(10) { i -> Seat("S${i + 1}") }

fun main() {
    val admin = User("admin", "Admin123", "Admin")
    val customer = User("user", "User123", "Customer")
    val users = listOf(admin, customer)
    val loginManager = LoginManager(users)

    val f1 = Film("The Bride!", "Horror", 12.0)
    val f2 = Film("Reminders of Him", "Drama", 10.5)
    val f3 = Film("Project Hail Mary", "Sci-Fi", 13.0)
    val f4 = Film("Ready or Not 2", "Horror", 11.0)

    val films = mutableListOf(f1, f2, f3, f4)
    val screenings = mutableListOf<Screening>()

    // horror
    screenings.add(Screening(f1, 1, "2026-03-23", "09:30", 0.0, makeSeats()))
    screenings.add(Screening(f1, 1, "2026-03-23", "15:00", 0.0, makeSeats()))
    screenings.add(Screening(f1, 2, "2026-03-24", "20:00", 0.0, makeSeats()))

    // drama
    screenings.add(Screening(f2, 1, "2026-03-23", "13:00", 0.0, makeSeats()))
    screenings.add(Screening(f2, 2, "2026-03-24", "17:30", 0.0, makeSeats()))
    screenings.add(Screening(f2, 1, "2026-03-25", "19:00", 0.0, makeSeats()))

    // sci-fi
    screenings.add(Screening(f3, 3, "2026-03-23", "11:00", 0.0, makeSeats()))
    screenings.add(Screening(f3, 3, "2026-03-24", "16:00", 0.0, makeSeats()))
    screenings.add(Screening(f3, 3, "2026-03-25", "21:00", 0.0, makeSeats()))

    // late night horror
    screenings.add(Screening(f4, 2, "2026-03-23", "18:30", 0.0, makeSeats()))
    screenings.add(Screening(f4, 1, "2026-03-24", "21:30", 0.0, makeSeats()))
    screenings.add(Screening(f4, 2, "2026-03-25", "23:00", 0.0, makeSeats()))

    val offers = mutableListOf(
        SpecialOffer("Morning Discount", "25% off weekday screenings before 12:00", true),
        SpecialOffer("Group Discount", "First 2 tickets full price, additional tickets 30% off", true),
        SpecialOffer("Kids Discount", "30% off for children", true)
    )

    val bookings = mutableListOf<Booking>()

    println("Cinema System Ready with Trending 2026 Movies")
    println("Films Loaded: ${films.size}")
    println("Screenings Loaded: ${screenings.size}")

    println("\n===== Please Login =====")
    val loggedInUser = loginManager.login()

    if (loggedInUser == null) {
        println("Access denied. Invalid credentials.")
        return
    }

    if (loggedInUser.role == "Admin") {
        var running = true
        while (running) {
            println("\n===== Admin Menu =====")
            println("1. View Films and Screenings")
            println("2. Add New Film and Screening")
            println("3. Modify Ticket Pricing")
            println("4. Search Films by Genre")
            println("5. Manage Special Offers")
            println("6. Exit")
            print("Choose an option (1-6): ")

            when (readLine()?.trim()) {
                "1" -> viewFilmsAndScreenings(films, screenings)
                "2" -> addFilmAndScreening(films, screenings)
                "3" -> modifyTicketPricing(films)
                "4" -> searchFilmsByGenre(films, screenings)
                "5" -> manageSpecialOffers(offers)
                "6" -> {
                    println("Exiting admin menu, goodbye!")
                    running = false
                }
                else -> println("Invalid option, please choose 1 to 6")
            }
        }
    } else if (loggedInUser.role == "Customer") {
        var running = true
        while (running) {
            showCustomerMenu()
            print("Choose an option: ")
            when (readLine()?.trim()) {
                "1" -> viewFilmsAndScreenings(films, screenings)
                "2" -> bookTicket(loggedInUser, films, screenings, offers, bookings)
                "3" -> viewBookings(loggedInUser, bookings)
                "0" -> {
                    println("Logging out...")
                    running = false
                }
                else -> println("Invalid option, please try again.")
            }
        }
    }
}