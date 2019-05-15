package dmitry.tpo2.entity

import org.jetbrains.exposed.sql.Table

object Artists : Table() {
    val id = integer("id").autoIncrement().primaryKey()
    val name = varchar("name", 20)
}