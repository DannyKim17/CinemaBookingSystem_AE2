fun applyOffers(
    basePrice: Double,
    screening: Screening,
    numberOfTickets: Int,
    isChild: Boolean,
    offers: List<SpecialOffer>
): Double {

    var finalPrice = basePrice

    offers.forEach { offer ->

        if (offer.name == "Morning Discount" && offer.isEnabled && screening.startTime < "12:00") {
            finalPrice = MorningDiscount().apply(finalPrice)
        }

        if (offer.name == "Group Discount" && offer.isEnabled && numberOfTickets >= 4) {
            finalPrice = GroupDiscount().apply(finalPrice)
        }

        if (offer.name == "Kids Discount" && offer.isEnabled && isChild) {
            finalPrice = KidsDiscount().apply(finalPrice)
        }
    }

    return finalPrice
}