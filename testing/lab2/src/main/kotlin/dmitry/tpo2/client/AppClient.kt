package dmitry.tpo2.client

import dmitry.tpo2.entity.User
import dmitry.tpo2.pages.AbstractPage
import dmitry.tpo2.pages.LoginPage
import dmitry.tpo2.pages.SignUpPage
import org.jetbrains.exposed.sql.transactions.transaction

class AppClient {
    var page: AbstractPage? = null
        private set

    val hasUsers : Boolean
        get() {
            return transaction {
                User.count() > 0
            }
        }

    fun start() {
        try {
            DatabaseManager.initDatabase()
            page = if (hasUsers) LoginPage else SignUpPage
            interact()
        } catch (e: Exception) {
            println("Ошибка при инициализации клиента: ")
            e.printStackTrace()
        }
    }

    fun interact() {
        while (page != null) {
            page = page!!.interact()
        }
    }

}
