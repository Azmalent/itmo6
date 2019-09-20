package dmitry.tpo2.pages

import dmitry.tpo2.entity.MusicTrack
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.transactions.transaction

object TrackListPage : AbstractPage() {
    override val pageTitle = "Каталог"

    fun getTracks(): SizedIterable<MusicTrack> {
        return transaction {
            MusicTrack.all()
        }
    }

    override fun runInteractionLogic(): AbstractPage {
        val list = getTracks()

        if (list.count() > 0) {
            list.forEach(::println)
        } else {
            println("Ничего не найдено!")
        }

        anykey()
        return MenuPage
    }
}
