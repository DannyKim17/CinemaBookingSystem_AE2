fun makeSeats() = List(10) { i -> Seat("S${i + 1}") }

fun main() {
    // Admin login
    val admin = User("admin", "Admin123", "Admin")

    // Real trending movies for March 2026
    val f1 = Film("The Bride!", "Horror", 12.0)
    val f2 = Film("Reminders of Him", "Drama", 10.5)
    val f3 = Film("Project Hail Mary", "Sci-Fi", 13.0)
    val f4 = Film("Ready or Not 2", "Horror", 11.0)

    val screenings = mutableListOf<Screening>()

    // Adding 12 screenings
    // Monday morning show for "The Bride!" (perfect for testing the Morning Discount)
    screenings.add(Screening(f1, 1, "2026-03-23", "09:30", 0.0, makeSeats()))
    screenings.add(Screening(f1, 1, "2026-03-23", "15:00", 0.0, makeSeats()))
    screenings.add(Screening(f1, 2, "2026-03-24", "20:00", 0.0, makeSeats()))

    // Reminders of Him screenings
    screenings.add(Screening(f2, 1, "2026-03-23", "13:00", 0.0, makeSeats()))
    screenings.add(Screening(f2, 2, "2026-03-24", "17:30", 0.0, makeSeats()))
    screenings.add(Screening(f2, 1, "2026-03-25", "19:00", 0.0, makeSeats()))

    // Project Hail Mary screenings
    screenings.add(Screening(f3, 3, "2026-03-23", "11:00", 0.0, makeSeats()))
    screenings.add(Screening(f3, 3, "2026-03-24", "16:00", 0.0, makeSeats()))
    screenings.add(Screening(f3, 3, "2026-03-25", "21:00", 0.0, makeSeats()))

    // Ready or Not 2 screenings
    screenings.add(Screening(f4, 2, "2026-03-23", "18:30", 0.0, makeSeats()))
    screenings.add(Screening(f4, 1, "2026-03-24", "21:30", 0.0, makeSeats()))
    screenings.add(Screening(f4, 2, "2026-03-25", "23:00", 0.0, makeSeats()))

    println("Cinema System Ready with Trending 2026 Movies")
    println("Screenings Loaded: ${screenings.size}")
}