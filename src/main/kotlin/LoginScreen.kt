class LoginScreen {

    fun login(
        loginManager: LoginManager
    ): User? {

        println("===== Please Login =====")

        print("Username: ")
        val username = readLine() ?: ""

        print("Password: ")
        val password = readLine() ?: ""

        return loginManager.login(
            username,
            password
        )
    }
}