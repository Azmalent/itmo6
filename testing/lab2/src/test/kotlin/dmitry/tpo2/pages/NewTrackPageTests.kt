package dmitry.tpo2.pages

import com.nhaarman.mockitokotlin2.doNothing
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.whenever
import io.kotlintest.specs.StringSpec
import org.junit.Assert
import org.mockito.ArgumentMatchers.anyString
import org.mockito.ArgumentMatchers.anyInt

class NewTrackPageTests : StringSpec({
    val spy = spy(NewTrackPage)
    doNothing().whenever(spy).addTrack(anyString(), anyString(), anyInt())
    doNothing().whenever(spy).anykey()

    whenever(spy.readUserInput())
            .thenReturn("Test track")
            .thenReturn("Test artist")

    "Returns MenuPage on success" {
        whenever(spy.readInt()).doReturn(2007)
        val page = spy.runInteractionLogic()
        Assert.assertTrue("Page was ${page::class.simpleName}", page is MenuPage)
    }

    "Returns itself on failure" {
        whenever(spy.readInt()).doReturn(-2007)
        val page = spy.runInteractionLogic()
        Assert.assertTrue("Page was ${page::class.simpleName}", page is NewTrackPage)
    }
})