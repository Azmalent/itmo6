package dmitry.tpo2.pages

import com.nhaarman.mockitokotlin2.doNothing
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.whenever
import dmitry.tpo2.entity.MusicTrack
import io.kotlintest.specs.StringSpec
import org.jetbrains.exposed.sql.emptySized
import org.junit.Assert.assertTrue
import org.mockito.ArgumentMatchers.anyInt

class TrackListPageTests : StringSpec({
    val spy = spy(TrackListPage) {
        on { getTracks() } doReturn emptySized()
    }
    doNothing().whenever(spy).anykey()

    "Returns MenuPage" {
        val page = spy.runInteractionLogic()
        assertTrue("Page was ${page::class.simpleName}", page is MenuPage)
    }
})