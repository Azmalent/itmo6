package dmitry.tpo2.pages

import dmitry.tpo2.entity.User
import dmitry.tpo2.entity.Users
import org.jetbrains.exposed.sql.transactions.transaction

object SignUpPage : AbstractPage() {
    override val pageTitle = "Регистрация"

    fun inputUsername(): String {
        var username: String
        do {
            username = readUserInput().trim()
            if (username.isBlank()) {
                println("Имя пользователя не должно быть пустым. Попробуйте ещё раз:")
            }
        } while (username.isBlank())
        return username
    }

    fun isUsernameTaken(username: String): Boolean {
        val user = transaction {
            User.find { Users.username eq username }.firstOrNull()
        }
        return user != null
    }

    fun validatePassword(password: String): Boolean {
        val longEnough = password.length >= 6
        val hasLetters = password.any { it.isLetter() }
        val hasDigits  = password.any { it.isDigit() }
        return longEnough && hasLetters && hasDigits
    }

    fun createUser(username: String, password: String) {
        transaction {
            User.new {
                this.username = username
                this.hashedPassword = password.hashCode()
            }
        }
    }

    override fun runInteractionLogic(): AbstractPage {
        lateinit var username: String
        lateinit var password: String

        println("Введите имя пользователя:")
        do {
            username = inputUsername()
            val taken = isUsernameTaken(username)
            if (taken) {
                println("Пользователь с таким логином уже существует. Попробуйте ещё раз: ")
            }
        } while(taken)

        println("Введите пароль:")
        do {
            password = readUserInput()
            val invalid = validatePassword(password)
            if (invalid) {
                println("Пароль должен содержать минимум 6 символов, включая хотя бы одну букву и одну цифру. \nПопробуйте ещё раз: ")
            }
        } while(invalid)

        createUser(username, password)

        return MenuPage
    }
}
