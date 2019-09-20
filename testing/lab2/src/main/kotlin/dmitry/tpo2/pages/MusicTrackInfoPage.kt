package dmitry.tpo2.pages

import dmitry.tpo2.entity.MusicTrack
import org.jetbrains.exposed.sql.transactions.transaction

object MusicTrackInfoPage : AbstractPage() {
    override val pageTitle = "Информация об аудиозаписи"

    fun getTrack(id: Int): MusicTrack? {
        return transaction {
            MusicTrack.findById(id)
        }
    }

    override fun runInteractionLogic(): AbstractPage {
        println("Введите ID: ")
        val id = readInt()
        val track = getTrack(id)

        println(track ?: "Ошибка: аудиозапись с таким ID не найдена.")

        anykey()
        return MenuPage
    }
}
