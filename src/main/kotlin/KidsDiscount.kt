class KidsDiscount : DiscountStrategy {
    override fun apply(price: Double): Double {
        return price * 0.7  // 30% discount
    }
}