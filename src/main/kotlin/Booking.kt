import javax.swing.JOptionPane

data class Booking(
    val id: Int = 0,
    val userId: Int,
    val screeningId: Int,
    val totalPrice: Double
)