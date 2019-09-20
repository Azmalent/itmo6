package dmitry.tpo2.pages

import com.nhaarman.mockitokotlin2.doNothing
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.whenever
import io.kotlintest.specs.StringSpec
import org.junit.Assert.assertTrue
import org.mockito.ArgumentMatchers.anyInt

class MusicTrackInfoPageTests : StringSpec({
    val spy = spy(MusicTrackInfoPage) {
        on { readInt() } doReturn 10
        on { getTrack(anyInt()) } doReturn null
    }
    doNothing().whenever(spy).anykey()

    "Returns MenuPage" {
        val page = spy.runInteractionLogic()
        assertTrue("Page was ${page::class.simpleName}", page is MenuPage)
    }
})