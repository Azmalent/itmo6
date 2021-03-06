package dmitry.tpo2.pages

import com.nhaarman.mockitokotlin2.doNothing
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.whenever
import io.kotlintest.specs.StringSpec
import org.junit.Assert.assertTrue
import org.mockito.ArgumentMatchers.anyInt

class DeletePageTests : StringSpec({
    val spy = spy<DeletePage> {
        on { readInt() } doReturn 20
    }
    doReturn(true).whenever(spy).tryDeleteTrack(anyInt())
    doNothing().whenever(spy).anykey()

    "Returns to menu" {
        val page = spy.runInteractionLogic()
        assertTrue("Page was ${page::class.simpleName}", page is MenuPage)
    }
})