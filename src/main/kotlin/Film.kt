data class Film(
    val id: Int = 0,
    val title: String,
    val genre: String,
    var basePrice: Double,
    var totalTicketsSold: Int = 0
) {
    // printing film details
    fun displayInfo() {
        println("Title: $title | Genre: $genre | Price: £$basePrice | Tickets Sold: $totalTicketsSold")
    }
}