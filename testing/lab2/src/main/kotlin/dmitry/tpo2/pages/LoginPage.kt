package dmitry.tpo2.pages

import dmitry.tpo2.entity.User
import dmitry.tpo2.entity.Users
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.sql.transactions.transaction

object LoginPage : AbstractPage() {
    override val pageTitle = "Вход"

    fun getUser(): User {
        println("Введите логин: ")
        var user: User?
        do {
            user = transaction {
                val username = readUserInput()
                User.find { Users.username eq username }.firstOrNull()
            }
            if (user == null) {
                println("Пользователь с таким логином не найден. Попробуйте ещё раз: ")
            }
        } while(user == null)

        return user
    }

    fun tryEnterPassword(user: User): Boolean {
        val password = readUserInput()
        return user.hashedPassword == password.hashCode()
    }

    fun enterPassword(user: User) {
        println("Введите пароль: ")
        do {
            val success = tryEnterPassword(user)
            if (!success) {
                println("Неверный пароль. Попробуйте ещё раз: ")
            }
        } while(!success)
    }

    override fun runInteractionLogic(): AbstractPage {
        val user = getUser()
        enterPassword(user)

        return MenuPage
    }
}
