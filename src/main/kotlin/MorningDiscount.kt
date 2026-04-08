class MorningDiscount : DiscountStrategy {
    override fun apply(price: Double): Double {
        return price * 0.8
    }
}