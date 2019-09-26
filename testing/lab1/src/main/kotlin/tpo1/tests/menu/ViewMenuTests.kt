package tpo1.tests.menu

import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import tpo1.PresentationPage

class ViewMenuTests(page: PresentationPage) : AbstractMenuTestSet("View menu", page) {
    @FindBy(id = "docs-view-menu")
    override lateinit var menu: WebElement

    @FindBy(xpath = "//span[@aria-label='Анимация a']")
    lateinit var itemAnimation: WebElement

    @FindBy(xpath = "//span[@aria-label='Привязка x']")
    lateinit var itemBind: WebElement

    @FindBy(xpath = "//span[@aria-label='В виде сетки g']")
    lateinit var itemGrid: WebElement

    @FindBy(xpath = "//span[@aria-label='Направляющие d']")
    lateinit var itemDirections: WebElement

    @FindBy(xpath = "//span[@aria-label='Показать линейку r']")
    lateinit var itemShowRuler: WebElement

    @FindBy(xpath = "//span[contains(@aria-label,'Масштаб z')]")
    lateinit var itemScale: WebElement

    @Test("Animation") fun animationTest() {
        basicWindowTest(itemAnimation, "//div[@class='punch-animation-sidebar-header']//div[@title='Закрыть']/div")
    }

    @Test("Bind") fun bindTest() {
        basicEscapeTest(itemBind, "//span[text()='Сетка']")
    }

    @Test("Directions") fun directionsTest() {
        basicEscapeTest(itemDirections, "//span[text()='Показать направляющие']")
    }

    @Test("Grid") fun gridTest() {
        menu.click()
        itemGrid.click()
        val grid = page.findById("grid-view-tab")
        if (grid == null || grid.getAttribute("aria-selected") != "true") {
            fail("Doesn't work")
        }

        menu.click()
        itemGrid.click()
    }

    @Test("Ruler") fun rulerTest() {
        val ruler = page.findById("sketchy-horizontal-ruler")!!

        menu.click()
        itemShowRuler.click()
        if (ruler.isDisplayed) fail("Ruler doesn't hide")

        menu.click()
        itemShowRuler.click()
        if (!ruler.isDisplayed) fail("Ruler doesn't show")
    }

    @Test("Scale") fun scaleTest() {
        basicEscapeTest(itemScale, "//span[text()='Увеличить']")
    }
}