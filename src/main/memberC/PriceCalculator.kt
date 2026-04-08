fun applyOffers(
    basePrice: Double,
    screening: Screening,
    numberOfTickets: Int,
    isChild: Boolean,
    offers: List<SpecialOffer>
): Double {

    var finalPrice = basePrice

    offers.forEach { offer ->

        if (offer.name == "Morning Discount" && screening.startTime < "12:00") {
            finalPrice = offer.applyDiscount(finalPrice)
        }

        if (offer.name == "Group Discount" && numberOfTickets >= 4) {
            finalPrice = offer.applyDiscount(finalPrice)
        }

        if (offer.name == "Kids Discount" && isChild) {
            finalPrice = offer.applyDiscount(finalPrice)
        }
    }

    return finalPrice
}