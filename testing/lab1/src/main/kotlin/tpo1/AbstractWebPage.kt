package tpo1

import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.WebDriverWait

abstract class AbstractWebPage(val driver: WebDriver, val wait: WebDriverWait) {
    val actions = Actions(driver)

    fun find(locator: By): WebElement? {
        return try {
            val element = driver.findElement(locator)
            wait.until { element.isDisplayed }
            element
        } catch (e: NoSuchElementException) {
            null
        }
    }

    fun findById(id: String): WebElement? {
        return find(By.id(id))
    }

    fun findByName(name: String): WebElement? {
        return find(By.name(name))
    }

    fun findByCss(selector: String): WebElement? {
        return find(By.cssSelector(selector))
    }

    fun findByXpath(xpath: String): WebElement? {
        return find(By.xpath(xpath))
    }

    fun findByTag(tag: String): WebElement? {
        return find(By.tagName(tag))
    }

    fun findByClass(klass: String): WebElement? {
        return find(By.className(klass))
    }

    fun reload() {
        driver.navigate().refresh()
        wait.until { webDriver ->
            (webDriver as JavascriptExecutor).executeScript("return document.readyState") == "complete"
        }
    }

    protected open fun onLoad() { }
}