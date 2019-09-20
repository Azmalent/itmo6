package dmitry.tpo2.pages

import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.whenever
import io.kotlintest.specs.StringSpec
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue

class MenuPageTests : StringSpec({
    val spy = spy(MenuPage)

    fun testMenuItem(id: Int, requiredPage: AbstractPage) {
        whenever(spy.readInt()).thenReturn(id)
        val page = spy.runInteractionLogic()
        assertTrue("Page was ${page!!::class.simpleName}", page::class == requiredPage::class)
    }

    "Track list page" {
        testMenuItem(1, TrackListPage)
    }

    "Search page" {
        testMenuItem(2, SearchPage)
    }

    "New track page" {
        testMenuItem(3, NewTrackPage)
    }

    "Delete page" {
        testMenuItem(4, DeletePage)
    }

    "Music track info" {
        testMenuItem(5, MusicTrackInfoPage)
    }

    "About page" {
        testMenuItem(6, AboutPage)
    }

    "Exit" {
        whenever(spy.readInt()).thenReturn(100)
        val page = spy.runInteractionLogic()
        assertNull(page)
    }
})