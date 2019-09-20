package dmitry.tpo2.pages

import dmitry.tpo2.entity.Artists
import dmitry.tpo2.entity.MusicTrack
import dmitry.tpo2.entity.MusicTracks
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.isNotNull
import org.jetbrains.exposed.sql.SqlExpressionBuilder.like
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.lowerCase
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object SearchPage : AbstractPage() {
    override val pageTitle = "Поиск"

    fun searchByName(name: String): SizedIterable<MusicTrack> {
        val lowercaseName = name.toLowerCase()
        return transaction {
            MusicTrack.find { MusicTracks.name.lowerCase() like "%$lowercaseName%" }
        }
    }

    private fun searchByArtist(artist: String): SizedIterable<MusicTrack> {
        val lowercaseArtist = artist.toLowerCase()
        return transaction {
            val query = (MusicTracks innerJoin Artists).slice(MusicTracks.columns).select {
                MusicTracks.artist.isNotNull() and (Artists.name.lowerCase() like "%$lowercaseArtist%")
            }

            MusicTrack.wrapRows(query)
        }
    }

    private fun searchByYear(year: Int): SizedIterable<MusicTrack> {
        return transaction {
            MusicTrack.find { MusicTracks.year.isNotNull() and (MusicTracks.year eq year) }
        }
    }

    private fun printList(list: SizedIterable<MusicTrack>) {
        transaction {
            if (list.count() > 0) {
                list.forEach(::println)
            } else {
                println("По данному запросу ничего не найдено!")
            }
        }
    }

    override fun runInteractionLogic(): AbstractPage {
        println("Выберите способ поиска: ")
        println("[1] По названию")
        println("[2] По исполнителю")
        println("[3] По году")

        val item = readInt()
        val list: SizedIterable<MusicTrack>? = when (item) {
            1 -> {
                println("Введите название: ")
                val name = readUserInput().trim()
                searchByName(name)
            }
            2 -> {
                println("Введите исполнителя: ")
                val artist = readUserInput().trim()
                searchByArtist(artist)
            }
            3 -> {
                println("Введите год: ")
                val year = readInt()
                if (year == null || year < 0) {
                    println("Ошибка: год должен быть корректным положительным числом.")
                    null
                } else {
                    searchByYear(year)
                }
            }
            else -> {
                println("Ошибка: некорректный пункт меню.")
                null
            }
        }

        if (list != null) {
            printList(list)
        }

        anykey()
        return MenuPage
    }
}
