package dmitry.tpo2.entity

import org.jetbrains.exposed.dao.*

object Users : IntIdTable() {
    val username= varchar("username", 20).uniqueIndex()
    val hashedPassword = integer("hashed_password")
}

class User(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<User>(Users)

    var username by Users.username
    var hashedPassword by Users.hashedPassword
}