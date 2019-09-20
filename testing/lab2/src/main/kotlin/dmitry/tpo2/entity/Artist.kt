package dmitry.tpo2.entity

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.transactions.transaction

object Artists : IntIdTable() {
    val name = varchar("name", 20)
}

class Artist(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Artist>(Artists) {
        fun findOrCreate(name: String): Artist {
            return transaction {
                Artist.find { Artists.name eq name }.firstOrNull() ?: Artist.new {
                    this.name = name
                }
            }
        }
    }

    var name by Artists.name
    val tracks by MusicTrack optionalReferrersOn MusicTracks.artist

    override fun toString(): String {
        return name
    }
}

