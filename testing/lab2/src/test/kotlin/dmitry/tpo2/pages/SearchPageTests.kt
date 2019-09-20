package dmitry.tpo2.pages

import com.nhaarman.mockitokotlin2.*
import io.kotlintest.specs.StringSpec
import org.jetbrains.exposed.sql.emptySized
import org.junit.Assert.assertTrue
import org.mockito.ArgumentMatchers.anyString

class SearchPageTests : StringSpec({
    val spy = spy(SearchPage) {
        on { search(anyString(), anyString(), anyOrNull()) } doReturn emptySized()
    }
    doNothing().whenever(spy).anykey()

    "Returns MenuPage" {
        whenever(spy.readUserInput())
                .thenReturn("Track #1")
                .thenReturn("Unknown artist")
                .thenReturn("2007")

        val page = spy.runInteractionLogic()
        assertTrue("Page was ${page::class.simpleName}", page is MenuPage)
    }
})