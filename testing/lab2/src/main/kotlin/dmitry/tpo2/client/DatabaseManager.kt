package dmitry.tpo2.client

import dmitry.tpo2.entity.Artists
import dmitry.tpo2.entity.MusicTracks
import dmitry.tpo2.entity.Users
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseManager {
    const val URL = "jdbc:postgresql://balarama.db.elephantsql.com:5432/yavpbler"
    const val DRIVER = "org.postgresql.Driver"
    const val USER = "yavpbler"
    const val PASSWORD = "mk9TrGVmf9BvwhxhGFOYr1VDJ1v3ot7S"

    lateinit var database: Database
        private set

    fun initDatabase() {
        database = Database.connect(URL, DRIVER, USER, PASSWORD)
        transaction {
            create(Users)
            create(Artists)
            create(MusicTracks)
        }
    }
}