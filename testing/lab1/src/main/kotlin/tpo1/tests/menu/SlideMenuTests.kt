package tpo1.tests.menu

import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import tpo1.PresentationPage

class SlideMenuTests(page: PresentationPage) : AbstractMenuTestSet("Slide menu", page) {
    @FindBy(id = "punch-slide-menu")
    override lateinit var menu: WebElement

    @FindBy(xpath = "//span[@aria-label='Новый слайд n']")
    lateinit var itemNewSlide: WebElement

    @FindBy(xpath = "//span[@aria-label='Дублировать слайд d']")
    lateinit var itemDuplicateSlide: WebElement

    @FindBy(xpath = "//span[@aria-label='Удалить слайд e']")
    lateinit var itemDeleteSlide: WebElement

    @FindBy(xpath = "//span[@aria-label='Пропустить слайд s']")
    lateinit var itemSkipSlide: WebElement

    @FindBy(xpath = "//span[@aria-label='Изменить фон b']")
    lateinit var itemBackgroundMenu: WebElement

    @FindBy(xpath = "//span[@aria-label='Изменить тему t']")
    lateinit var itemTheme: WebElement

    @FindBy(xpath = "//span[@aria-label='Изменить переход r']")
    lateinit var itemTransition: WebElement

    @Test("Skip slide") fun slideSkipTest() {
        menu.click()
        itemSkipSlide.click()
        page.driver.findElement(By.tagName("image")) ?: fail("Image not found")
        menu.click()
        itemSkipSlide.click()
    }

    @Test("Create and delete") fun createDeleteTest() {
        menu.click()
        itemNewSlide.click()

        menu.click()
        itemDuplicateSlide.click()

        val slideCount = page.driver.findElements(By.className("punch-filmstrip-thumbnail")).count()
        if (slideCount != 3) fail("Incorrect slide count")

        for (i in 1..2) {
            menu.click()
            itemDeleteSlide.click()
        }
    }

    @Test("Background menu") fun backgroundTest() {
        basicWindowTest(itemBackgroundMenu, "//button[contains(@name,'punch-bg-close')]")
    }

    @Test("Theme menu") fun themeTest() {
        basicWindowTest(itemTheme, "//div[@class='punch-theme-sidebar-header']//div[@title='Закрыть']/div")
    }

    @Test("Transition menu") fun transitionTest() {
        basicWindowTest(itemTransition, "//div[@class='punch-animation-sidebar-header']//div[@title='Закрыть']/div")
    }

}