import javax.swing.JOptionPane

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
    val loginScreen = LoginScreen()
    val adminScreen = AdminScreen()
    val customerScreen = CustomerScreen()

    val films =
        databaseManager.getAllFilms().toMutableList()

    val screenings =
        databaseManager.getAllScreenings().toMutableList()

    val offers =
        databaseManager.getAllOffers().toMutableList()

    val bookings =
        databaseManager.getAllBookings().toMutableList()

    println("Cinema System Ready with Trending 2026 Movies")
    println("Films Loaded: ${films.size}")
    println("Screenings Loaded: ${screenings.size}")

    val loggedInUser =
        loginScreen.login(loginManager)

    if (loggedInUser == null) {
        println("Access denied. Invalid credentials.")
        return
    }

    if (loggedInUser.role == "Admin") {

        var running = true

        while (running) {

            val choice = JOptionPane.showInputDialog(
                """
            ADMIN MENU
            
            1. View Films & Screenings
            2. Add Film & Screening
            3. Modify Ticket Pricing
            4. Search Films by Genre
            5. Manage Special Offers
            0. Logout
            """.trimIndent()
            )

            when (choice) {

                "1" -> adminScreen.viewFilmsAndScreenings(
                    films,
                    screenings
                )

                "2" -> adminScreen.addFilmAndScreening(
                    films,
                    screenings
                )

                "3" -> adminScreen.modifyTicketPricing(
                    films
                )

                "4" -> adminScreen.searchFilmsByGenre(
                    films,
                    screenings
                )

                "5" -> adminScreen.manageSpecialOffers(
                    offers
                )

                "0" -> {
                    JOptionPane.showMessageDialog(
                        null,
                        "Logged out"
                    )
                    running = false
                }

                else -> {
                    JOptionPane.showMessageDialog(
                        null,
                        "Invalid option"
                    )
                }
            }
        }
    } else if (loggedInUser.role == "Customer") {

        var running = true

        while (running) {

            val choice = JOptionPane.showInputDialog(
                """
            CUSTOMER MENU
            
            1. View Films
            2. View Screenings
            3. Book Ticket
            4. View Bookings
            0. Logout
            """.trimIndent()
            )

            when (choice) {

                "1" -> customerScreen.viewFilms(
                    films
                )

                "2" -> customerScreen.viewScreenings(
                    screenings,
                    films
                )

                "3" -> bookTicket(
                    loggedInUser,
                    films,
                    screenings,
                    offers,
                    bookings,
                    databaseManager
                )

                "4" -> viewBookings(
                    loggedInUser,
                    bookings
                )

                "0" -> {

                    JOptionPane.showMessageDialog(
                        null,
                        "Logged out"
                    )

                    running = false
                }

                else -> {

                    JOptionPane.showMessageDialog(
                        null,
                        "Invalid option"
                    )
                }
            }
        }
    }
}