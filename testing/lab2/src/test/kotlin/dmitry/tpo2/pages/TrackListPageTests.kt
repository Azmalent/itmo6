package dmitry.tpo2.pages

import com.nhaarman.mockitokotlin2.*
import dmitry.tpo2.client.DatabaseManager
import dmitry.tpo2.entity.MusicTrack
import io.kotlintest.specs.StringSpec
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.emptySized
import org.junit.Assert.assertTrue
import org.mockito.ArgumentMatchers
import org.mockito.Matchers



class TrackListPageTests : StringSpec({
    DatabaseManager.initDatabase()

    val spy = spy(TrackListPage) {
        on { getTracks() } doReturn emptySized()
    }
    doNothing().whenever(spy).anykey()

    "Returns MenuPage" {
        val page = spy.runInteractionLogic()
        assertTrue("Page was ${page::class.simpleName}", page is MenuPage)
    }
})