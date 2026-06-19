import java.sql.Connection
import java.sql.DriverManager

class DatabaseManager {

    private val url = "jdbc:sqlite:cinema.db"

    fun connect(): Connection {
        return DriverManager.getConnection(url)
    }

    fun createTables() {

        val connection = connect()

        val statement = connection.createStatement()

        statement.execute("""
            CREATE TABLE IF NOT EXISTS films(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                title TEXT NOT NULL,
                genre TEXT NOT NULL,
                base_price REAL NOT NULL,
                total_tickets_sold INTEGER DEFAULT 0
            )
        """)

        statement.execute("""
            CREATE TABLE IF NOT EXISTS users(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT NOT NULL,
                password TEXT NOT NULL,
                role TEXT NOT NULL
            )
        """)

        statement.execute("""
            CREATE TABLE IF NOT EXISTS special_offers(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                description TEXT NOT NULL,
                is_enabled INTEGER NOT NULL
            )
        """)

        connection.close()
    }
    fun insertSampleFilms() {

        val connection = connect()

        val statement = connection.createStatement()

        statement.execute("""
        INSERT OR IGNORE INTO films
        (id, title, genre, base_price, total_tickets_sold)
        VALUES
        (1, 'The Bride!', 'Horror', 12.0, 0),
        (2, 'Reminders of Him', 'Drama', 10.5, 0),
        (3, 'Project Hail Mary', 'Sci-Fi', 13.0, 0),
        (4, 'Ready or Not 2', 'Horror', 11.0, 0)
    """)

        connection.close()
    }
    fun getAllFilms(): List<Film> {

        val films = mutableListOf<Film>()

        val connection = connect()

        val statement = connection.createStatement()

        val resultSet = statement.executeQuery(
            "SELECT * FROM films"
        )

        while (resultSet.next()) {

            films.add(
                Film(
                    id = resultSet.getInt("id"),
                    title = resultSet.getString("title"),
                    genre = resultSet.getString("genre"),
                    basePrice = resultSet.getDouble("base_price")
                )
            )
        }

        connection.close()

        return films
    }
}