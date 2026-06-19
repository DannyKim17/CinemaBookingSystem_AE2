import javax.swing.JOptionPane

class LoginScreen {

    fun login(
        loginManager: LoginManager
    ): User? {

        val username =
            JOptionPane.showInputDialog(
                null,
                "Enter Username"
            ) ?: return null

        val password =
            JOptionPane.showInputDialog(
                null,
                "Enter Password"
            ) ?: return null

        return loginManager.login(
            username,
            password
        )
    }
}