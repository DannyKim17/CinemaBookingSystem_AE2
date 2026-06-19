import java.time.DayOfWeek
import java.time.LocalDate

fun applyOffers(
    basePrice: Double,
    screening: Screening,
    numberOfTickets: Int,
    isChild: Boolean,
    offers: List<SpecialOffer>
): Double {

    var pricePerTicket = basePrice

    // Morning Discount (25% off before 12:00 on weekdays)
    val morningOffer = offers.find { it.name == "Morning Discount" }

    val isBefore12 = screening.startTime < "12:00"
    val date = LocalDate.parse(screening.date)
    val isWeekday =
        date.dayOfWeek != DayOfWeek.SATURDAY &&
                date.dayOfWeek != DayOfWeek.SUNDAY

    if (morningOffer != null &&
        morningOffer.isEnabled &&
        isBefore12 &&
        isWeekday) {

        pricePerTicket *= 0.75
    }

    return pricePerTicket * numberOfTickets
}