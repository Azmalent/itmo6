package dmitry.tpo2.pages

import com.nhaarman.mockitokotlin2.*
import dmitry.tpo2.client.DatabaseManager
import io.kotlintest.specs.StringSpec
import org.jetbrains.exposed.sql.emptySized
import org.junit.Assert.assertTrue
import org.mockito.ArgumentMatchers.anyString

class SearchPageTests : StringSpec({
    DatabaseManager.initDatabase()

    val spy = spy(SearchPage) {
        on { searchByName(anyString()) } doReturn emptySized()
    }
    doNothing().whenever(spy).anykey()

    "Returns MenuPage" {
        whenever(spy.readInt()).thenReturn(4)

        val page = spy.runInteractionLogic()
        assertTrue("Page was ${page::class.simpleName}", page is MenuPage)
    }
})