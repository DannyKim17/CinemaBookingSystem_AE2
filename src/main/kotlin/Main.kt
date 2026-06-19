fun main() {

    val databaseManager = DatabaseManager()

    databaseManager.createTables()
    databaseManager.insertSampleFilms()
    databaseManager.insertSampleScreenings()
    databaseManager.insertSampleOffers()
    databaseManager.insertSampleUsers()
    databaseManager.insertSampleSeats()

    val users =
        databaseManager.getAllUsers()

    val loginManager = LoginManager(users)
    val adminScreen = AdminScreen()
    val customerScreen = CustomerScreen()

    val films =
        databaseManager.getAllFilms().toMutableList()

    val screenings =
        databaseManager.getAllScreenings().toMutableList()

    val offers =
        databaseManager.getAllOffers().toMutableList()

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