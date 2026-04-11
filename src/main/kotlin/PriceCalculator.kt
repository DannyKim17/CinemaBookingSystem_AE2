import java.time.LocalDate
import java.time.DayOfWeek

fun applyOffers(
    basePrice: Double,
    screening: Screening,
    numberOfTickets: Int,
    isChild: Boolean,
    offers: List<SpecialOffer>
): Double {
    var pricePerTicket = basePrice

    // Task 9: Morning Discount applied first - 25 % off, weekdays only before 12:00
    val morningOffer = offers.find { it.name == "Morning Discount" }
    val isBefore12 = screening.startTime < "12:00"
    val date = LocalDate.parse(screening.date)
    val isWeekday = date.dayOfWeek != DayOfWeek.SATURDAY && date.dayOfWeek != DayOfWeek.SUNDAY

    if (morningOffer != null && morningOffer.isEnabled && isBefore12 && isWeekday) {
        pricePerTicket = MorningDiscount().apply(pricePerTicket)
    }

    // Kids Discount
    val kidsOffer = offers.find { it.name == "Kids Discount" }
    if (kidsOffer != null && kidsOffer.isEnabled && isChild) {
        pricePerTicket = KidsDiscount().apply(pricePerTicket)
    }

    // Task 9: Group Discount applied second
    // first 2 tickets full price, each additional ticket 30% off :D
    val groupOffer = offers.find { it.name == "Group Discount" }
    if (groupOffer != null && groupOffer.isEnabled && numberOfTickets > 2) {
        val additionalTickets = numberOfTickets - 2
        return (2 * pricePerTicket) + (additionalTickets * GroupDiscount().apply(pricePerTicket))
    }

    return pricePerTicket * numberOfTickets
}