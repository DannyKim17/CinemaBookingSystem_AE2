interface DiscountStrategy {
    fun apply(price: Double): Double
}