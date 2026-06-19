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

        statement.execute("""
    CREATE TABLE IF NOT EXISTS screenings(
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        film_id INTEGER NOT NULL,
        hall_number INTEGER NOT NULL,
        date TEXT NOT NULL,
        start_time TEXT NOT NULL,
        total_takings REAL DEFAULT 0,
        FOREIGN KEY (film_id) REFERENCES films(id)
    )
""")

        statement.execute("""
    CREATE TABLE IF NOT EXISTS seats(
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        screening_id INTEGER NOT NULL,
        seat_number TEXT NOT NULL,
        is_available INTEGER DEFAULT 1,
        FOREIGN KEY (screening_id) REFERENCES screenings(id)
    )
""")

        statement.execute("""
    CREATE TABLE IF NOT EXISTS bookings(
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        user_id INTEGER NOT NULL,
        screening_id INTEGER NOT NULL,
        total_price REAL NOT NULL,
        FOREIGN KEY (user_id) REFERENCES users(id),
        FOREIGN KEY (screening_id) REFERENCES screenings(id)
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

    fun insertSampleUsers() {

        val connection = connect()
        val statement = connection.createStatement()

        statement.execute("""
        INSERT OR IGNORE INTO users
        (id, username, password, role)
        VALUES
        (1, 'admin', 'Admin123', 'Admin'),
        (2, 'user', 'User123', 'Customer')
    """)

        connection.close()
    }

    fun getAllUsers(): List<User> {

        val users = mutableListOf<User>()

        val connection = connect()

        val statement = connection.createStatement()

        val resultSet = statement.executeQuery(
            "SELECT * FROM users"
        )

        while (resultSet.next()) {

            users.add(
                User(
                    id = resultSet.getInt("id"),
                    username = resultSet.getString("username"),
                    password = resultSet.getString("password"),
                    role = resultSet.getString("role")
                )
            )
        }

        connection.close()

        return users
    }

    fun insertSampleOffers() {

        val connection = connect()
        val statement = connection.createStatement()

        statement.execute("""
        INSERT OR IGNORE INTO special_offers
        (id, name, description, is_enabled)
        VALUES
        (1, 'Morning Discount', '25% off weekday screenings before 12:00', 1),
        (2, 'Group Discount', 'First 2 tickets full price, additional tickets 30% off', 1),
        (3, 'Kids Discount', '30% off for children', 1)
    """)

        connection.close()
    }

    fun insertSampleScreenings() {

        val connection = connect()

        val statement = connection.createStatement()

        statement.execute("""
        INSERT OR IGNORE INTO screenings
        (id, film_id, hall_number, date, start_time, total_takings)
        VALUES
        (1, 1, 1, '2026-03-23', '09:30', 0),
        (2, 1, 1, '2026-03-23', '15:00', 0),
        (3, 1, 2, '2026-03-24', '20:00', 0),

        (4, 2, 1, '2026-03-23', '13:00', 0),
        (5, 2, 2, '2026-03-24', '17:30', 0),
        (6, 2, 1, '2026-03-25', '19:00', 0),

        (7, 3, 3, '2026-03-23', '11:00', 0),
        (8, 3, 3, '2026-03-24', '16:00', 0),
        (9, 3, 3, '2026-03-25', '21:00', 0),

        (10, 4, 2, '2026-03-23', '18:30', 0),
        (11, 4, 1, '2026-03-24', '21:30', 0),
        (12, 4, 2, '2026-03-25', '23:00', 0)
    """)

        connection.close()
    }

    fun getAllScreenings(): List<Screening> {

        val screenings = mutableListOf<Screening>()

        val connection = connect()

        val statement = connection.createStatement()

        val resultSet = statement.executeQuery(
            "SELECT * FROM screenings"
        )

        while (resultSet.next()) {

            screenings.add(
                Screening(
                    id = resultSet.getInt("id"),
                    filmId = resultSet.getInt("film_id"),
                    hallNumber = resultSet.getInt("hall_number"),
                    date = resultSet.getString("date"),
                    startTime = resultSet.getString("start_time"),
                    totalTakings = resultSet.getDouble("total_takings")
                )
            )
        }

        connection.close()

        return screenings
    }

    fun getAllOffers(): List<SpecialOffer> {

        val offers = mutableListOf<SpecialOffer>()

        val connection = connect()

        val statement = connection.createStatement()

        val resultSet = statement.executeQuery(
            "SELECT * FROM special_offers"
        )

        while (resultSet.next()) {

            offers.add(
                SpecialOffer(
                    id = resultSet.getInt("id"),
                    name = resultSet.getString("name"),
                    description = resultSet.getString("description"),
                    isEnabled = resultSet.getInt("is_enabled") == 1
                )
            )
        }

        connection.close()

        return offers
    }


}