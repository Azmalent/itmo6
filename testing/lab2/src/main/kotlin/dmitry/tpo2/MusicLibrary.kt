package dmitry.tpo2

class MusicLibrary {
    private val _items = mutableListOf<MusicTrack>()

    val items: List<MusicTrack>
        get() = _items.toList()

    fun add(artist: String, name: String): Boolean {
        items.forEach {if (it.artist == artist && it.name == name) return false }

        val track = MusicTrack(artist, name)
        _items.add(track)
        return true
    }

    fun delete(artist: String, name: String): Boolean {
        val index = items.indexOfFirst { it.artist == artist && it.name == name }
        if (index == -1) return false

        _items.removeAt(index)
        return true
    }

    fun searchByArtist(artist: String, ignoreCase: Boolean = true): List<MusicTrack> {
        return items.filter { it.artist.contains(artist, ignoreCase) }
    }

    fun searchByName(name: String, ignoreCase: Boolean = true): List<MusicTrack> {
        return items.filter { it.name.contains(name, ignoreCase) }
    }
}