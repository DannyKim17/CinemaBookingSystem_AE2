fun main() {
    val admin = User(1, "admin", "Admin123", "Admin")
    val customer = User(2, "user", "User123", "Customer")
    val users = listOf(admin, customer)
    val loginManager = LoginManager(users)
    val adminScreen = AdminScreen()
    val customerScreen = CustomerScreen()

    val f1 = Film(1, "The Bride!", "Horror", 12.0)
    val f2 = Film(2, "Reminders of Him", "Drama", 10.5)
    val f3 = Film(3, "Project Hail Mary", "Sci-Fi", 13.0)
    val f4 = Film(4, "Ready or Not 2", "Horror", 11.0)

    val films = mutableListOf(f1, f2, f3, f4)
    val screenings = mutableListOf<Screening>()

    // horror
    screenings.add(Screening(1, 1, 1, "2026-03-23", "09:30"))
    screenings.add(Screening(2, 1, 1, "2026-03-23", "15:00"))
    screenings.add(Screening(3, 1, 2, "2026-03-24", "20:00"))

    // drama
    screenings.add(Screening(4, 2, 1, "2026-03-23", "13:00"))
    screenings.add(Screening(5, 2, 2, "2026-03-24", "17:30"))
    screenings.add(Screening(6, 2, 1, "2026-03-25", "19:00"))

    // sci-fi
    screenings.add(Screening(7, 3, 3, "2026-03-23", "11:00"))
    screenings.add(Screening(8, 3, 3, "2026-03-24", "16:00"))
    screenings.add(Screening(9, 3, 3, "2026-03-25", "21:00"))

    // late night horror
    screenings.add(Screening(10, 4, 2, "2026-03-23", "18:30"))
    screenings.add(Screening(11, 4, 1, "2026-03-24", "21:30"))
    screenings.add(Screening(12, 4, 2, "2026-03-25", "23:00"))

    val offers = mutableListOf(
        SpecialOffer(1, "Morning Discount", "25% off weekday screenings before 12:00", true),
        SpecialOffer(2, "Group Discount", "First 2 tickets full price, additional tickets 30% off", true),
        SpecialOffer(3, "Kids Discount", "30% off for children", true)
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

// small error i fixed it took so long
            when (readLine()?.trim()) {
                "1" -> adminScreen.viewFilmsAndScreenings(films, screenings)
                "2" -> adminScreen.addFilmAndScreening(films, screenings)
                "3" -> adminScreen.modifyTicketPricing(films)
                "4" -> adminScreen.searchFilmsByGenre(films, screenings)
                "5" -> adminScreen.manageSpecialOffers(offers)
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
            customerScreen.showMenu()
            print("Choose an option: ")
            when (readLine()?.trim()) {
                "1" -> adminScreen.viewFilmsAndScreenings(films, screenings)
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