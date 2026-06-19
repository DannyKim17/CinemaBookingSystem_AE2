class LoginManager(private val users: List<User>) {

    fun login(): User? {

        println("Username:")
        val username = readLine() ?: ""

        println("Password:")
        val password = readLine() ?: ""

        val user = users.find {
            it.username == username &&
                    it.password == password
        }

        if (user != null) {
            println("Login successful. Welcome ${user.username}")
        } else {
            println("Invalid username or password")
        }

        return user
    }
}