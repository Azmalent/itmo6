package dmitry.tpo2.pages

import dmitry.tpo2.entity.Artists
import dmitry.tpo2.entity.MusicTrack
import dmitry.tpo2.entity.MusicTracks
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.lowerCase
import org.jetbrains.exposed.sql.transactions.transaction

object SearchPage : AbstractPage() {
    override val pageTitle = "Поиск"

    fun search(name: String, artist: String, year: Int?): SizedIterable<MusicTrack> {
        return transaction {
            MusicTrack.find {
                val lowercaseName = name.toLowerCase()
                var query = MusicTracks.name.lowerCase() like "%$lowercaseName%"

                if (artist.isNotBlank()) {
                    val lowercaseArtist = artist.toLowerCase()
                    query = query and MusicTracks.artist.isNotNull() and (Artists.name.lowerCase() like "%$lowercaseArtist%")
                }

                if (year != null) {
                    query = query and MusicTracks.year.isNotNull() and (MusicTracks.year eq year)
                }

                query
            }
        }
    }

    override fun runInteractionLogic(): AbstractPage {
        println("Введите название: ")
        val name = readUserInput().trim()
        println("Введите исполнителя (пустая строка для отключения поиска): ")
        val artist = readUserInput().trim()
        println("Введите год (отрицательное число для отключения поиска): ")
        val number = readInt()
        val year = if (number < 0) null else number

        val list = search(name, artist, year)

        if (list.count() > 0) {
            list.forEach(::println)
        } else {
            println("По данному запросу ничего не найдено!")
        }

        anykey()
        return MenuPage
    }
}
