package dmitry.tpo2.pages

import dmitry.tpo2.entity.MusicTrack
import org.jetbrains.exposed.sql.transactions.transaction

object MusicTrackInfoPage : AbstractPage() {
    override val pageTitle = "Информация об аудиозаписи"

    fun getTrack(id: Int?): MusicTrack? {
        if (id == null) return null
        return transaction {
            MusicTrack.findById(id)
        }
    }

    fun printTrack(track: MusicTrack?) {
        if (track != null) {
            transaction {
                println(track)
            }
        } else println("Ошибка: аудиозапись с таким ID не найдена.")
    }

    override fun runInteractionLogic(): AbstractPage {
        println("Введите ID: ")
        val id = readInt()
        val track = getTrack(id)
        printTrack(track)

        anykey()
        return MenuPage
    }
}
