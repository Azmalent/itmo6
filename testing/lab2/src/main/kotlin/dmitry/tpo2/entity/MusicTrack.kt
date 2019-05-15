package dmitry.tpo2.entity

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.sql.Table

object MusicTracks : Table() {
    val id = integer("id").autoIncrement().primaryKey()
    val artistId = (integer("artistID") references Artists.id).nullable()
    val name = varchar("name", 20)
}

class MusicTrack(id: EntityID<Int>) : Entity<Int>(id) {
    companion object : EntityClass<Int, MusicTrack>(MusicTracks)
}