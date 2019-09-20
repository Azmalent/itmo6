package dmitry.tpo2.pages

import dmitry.tpo2.entity.Artist
import dmitry.tpo2.entity.MusicTrack
import org.jetbrains.exposed.sql.transactions.transaction

object NewTrackPage : AbstractPage() {
    override val pageTitle = "Добавить аудиозапись"

    fun addTrack(name: String, artist: String, year: Int?) {
        transaction {
            MusicTrack.new {
                this.name = name
                this.artist = Artist.findOrCreate(artist)
                this.year = year
            }
        }
    }

    override fun runInteractionLogic(): AbstractPage {
        return try {
            println("Введите название: ")
            val name = readUserInput("Без названия")
            println("Введите исполнителя: ")
            val artist = readUserInput("Неизвестный исполнитель")
            println("Введите год: ")
            val year = readInt()
            if (year != null && year < 0) throw IllegalArgumentException("год не может быть отрицательным!")

            addTrack(name, artist, year)

            println("Композиция успешно добавлена")
            anykey()
            MenuPage
        } catch (e: Exception) {
            println("Ошибка при вводе: ${e.localizedMessage}")
            anykey()
            this
        }

    }
}
