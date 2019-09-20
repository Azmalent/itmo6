package dmitry.tpo2.entity

import org.jetbrains.exposed.dao.*

object MusicTracks : IntIdTable() {
    val name = varchar("name", 20)
    val artist = reference("artistID",  Artists).nullable()
    val year = integer("year").check { it.greater(0) }.nullable()
}

class MusicTrack(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<MusicTrack>(MusicTracks)

    var name by MusicTracks.name
    var artist by Artist optionalReferencedOn MusicTracks.artist
    var year by MusicTracks.year

    override fun toString(): String {
        return "#$id\t${artist ?: "Неизвестный исполнитель"} - $name ($year)"
    }
}