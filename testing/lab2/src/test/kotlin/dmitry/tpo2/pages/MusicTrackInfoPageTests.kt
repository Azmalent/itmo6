package dmitry.tpo2.pages

import com.nhaarman.mockitokotlin2.*
import dmitry.tpo2.entity.MusicTrack
import io.kotlintest.specs.StringSpec
import org.jetbrains.exposed.sql.SizedIterable
import org.junit.Assert.assertTrue
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.anyInt

class MusicTrackInfoPageTests : StringSpec({
    val spy = spy(MusicTrackInfoPage) {
        on { readInt() } doReturn 10
    }
    doReturn(null).whenever(spy).getTrack(anyInt())
    doNothing().whenever(spy).anykey()

    "Returns MenuPage" {
        val page = spy.runInteractionLogic()
        assertTrue("Page was ${page::class.simpleName}", page is MenuPage)
    }
})