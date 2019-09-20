package dmitry.tpo2.pages

import dmitry.tpo2.entity.MusicTrack
import org.jetbrains.exposed.sql.transactions.transaction

object DeletePage : AbstractPage() {
    override val pageTitle = "Удаление"

    fun tryDeleteTrack(id: Int): Boolean {
        return transaction {
            val track = MusicTrack.findById(id)
            if (track != null) {
                track.delete()
                true
            } else false
        }
    }

    override fun runInteractionLogic(): AbstractPage {
        println("Введите ID записи для удаления: ")

        val id = readInt()
        val deleted = tryDeleteTrack(id ?: 0)
        if (deleted) {
            println("Запись удалена.")
        } else {
            println("Запись с таким ID не найдена.")
        }
        anykey()
        return MenuPage
    }
}
