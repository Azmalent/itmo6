package tpo1.tests

import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import tpo1.PresentationPage

class RightClickTests(page: PresentationPage) : AbstractTestSet("Right click", page) {
    @FindBy(id = "filmstrip")
    lateinit var filmstrip: WebElement

    @FindBy(xpath = "//span[text()='Изменить фон']")
    lateinit var itemChangeBackground: WebElement

    @FindBy(xpath = "//span[text()='Выбрать макет']")
    lateinit var itemLayout: WebElement

    @FindBy(xpath = "//span[text()='Изменить тему']")
    lateinit var itemTheme: WebElement

    @FindBy(xpath = "//span[text()='Изменить переход']")
    lateinit var itemTransition: WebElement

    @FindBy(id = "newSlideButton")
    lateinit var itemNewSlide: WebElement



    private fun openContextMenu() {
        page.actions.contextClick(filmstrip).perform()
    }

    override fun initializeTest() {
        openContextMenu()
    }



    @Test("Background") fun backgroundTest() {
        basicWindowTest(itemChangeBackground, "//span[@aria-label='Закрыть']")
    }

    @Test("Layout") fun layoutTest() {
        basicEscapeTest(itemLayout, "//div[text()='Заголовок и текст']")
    }

    @Test("Theme") fun themeTest() {
        basicEscapeTest(itemTheme, "//div[@class='punch-theme-sidebar-header']//div[@title='Закрыть']/div")
    }

    @Test("Transition") fun transitionTest() {
        basicWindowTest(itemTransition, "//div[@class='punch-animation-sidebar-header']//div[@title='Закрыть']/div")
    }

    @Test("New slide delete") fun newSlideDeleteTest() {
        val firstSlide = page.findByXpath("//*[@id='filmstrip']//*[contains(@class, 'punch-filmstrip-thumbnail')][1]")
        page.actions.contextClick(firstSlide).perform()
        page.findByXpath("//span[text()='Новый слайд']")?.click() ?: fail("Failed to create new slide")
        if (filmstrip.text != "12") fail("Incorrect filmstrip text (expected: \"12\", actual: \"${filmstrip.text}\"")

        val secondSlide = page.findByXpath("//*[@id='filmstrip']//*[contains(@class, 'punch-filmstrip-thumbnail')][2]")
        page.actions.contextClick(secondSlide).perform()
        page.findByXpath("//span[text()='Удалить']")?.click() ?: fail("Failed to delete slide")
        if (filmstrip.text != "1") fail("Incorrect filmstrip text (expected: \"1\", actual: \"${filmstrip.text}\"")
    }

    @Test("Duplicated slide delete") fun duplicatedSlideDeleteTest() {
        val firstSlide = page.findByXpath("//*[@id='filmstrip']//*[contains(@class, 'punch-filmstrip-thumbnail')][1]")
        page.actions.contextClick(firstSlide).perform()
        page.findByXpath("//span[text()='Дублировать слайд']")?.click() ?: fail("Failed to duplicate slide")
        if (filmstrip.text != "12") fail("Incorrect filmstrip text (expected: \"12\", actual: \"${filmstrip.text}\"")

        val secondSlide = page.findByXpath("//*[@id='filmstrip']//*[contains(@class, 'punch-filmstrip-thumbnail')][2]")
        page.actions.contextClick(secondSlide).perform()
        page.findByXpath("//span[text()='Удалить']")?.click() ?: fail("Failed to delete slide")
        if (filmstrip.text != "1") fail("Incorrect filmstrip text (expected: \"1\", actual: \"${filmstrip.text}\"")
    }
}