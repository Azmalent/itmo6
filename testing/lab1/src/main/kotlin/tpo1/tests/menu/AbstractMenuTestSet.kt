package tpo1.tests.menu

import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import tpo1.PresentationPage
import tpo1.tests.AbstractTestSet

abstract class AbstractMenuTestSet(name: String, page: PresentationPage) : AbstractTestSet(name, page) {
    protected abstract val menu: WebElement

    protected fun basicDropdownMenuTest(item: WebElement, expectedXpath: String) {
        menu.click()
        page.wait.until { ExpectedConditions.visibilityOf(item) }
        item.click()
        assertExists(expectedXpath)

        menu.click()
    }

    override fun initializeTest() {
        menu.click()
    }

    override fun run() {
        Thread.sleep(2500)
        super.run()
    }
}