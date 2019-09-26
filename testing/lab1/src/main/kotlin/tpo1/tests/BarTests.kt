package tpo1.tests

import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import tpo1.PresentationPage

class BarTests(page: PresentationPage) : AbstractTestSet("Bar", page) {
    @FindBy(id = "insertImageMenuButton")
    lateinit var insertImageMenuButton: WebElement

    @FindBy(id = "lineMenuButton")
    lateinit var lineMenuButton: WebElement

    @FindBy(id = "zoomButtonDropdown")
    lateinit var zoomButtonDropdown: WebElement

    @FindBy(id = "shapeButton")
    lateinit var shapeButton: WebElement

    @FindBy(id = "slideThemeButton")
    lateinit var slideThemeButton: WebElement

    @FindBy(id = "slideTransitionButton")
    lateinit var slideTransitionButton: WebElement


    override fun initializeTest() { }


    @Test("Insert image") fun insertImagesest() {
        basicEscapeTest(insertImageMenuButton, "//span[text()='Загрузить с компьютера']")
    }

    @Test("Line") fun lineTest() {
        basicEscapeTest(lineMenuButton, "//span[text()='Линия']")
    }

    @Test("Zoom dropdown") fun zoomButtonDropdownTest() {
        basicEscapeTest(zoomButtonDropdown, "//span[@aria-label='50% 5']")
    }

    @Test("Shape") fun shapeTest() {
        basicEscapeTest(shapeButton, "//span[text()='Фигуры']")
    }

    @Test("Theme") fun themeTest() {
        basicWindowTest(slideThemeButton, "//div[@class='punch-theme-sidebar-header']//div[@title='Закрыть']/div")
    }

    @Test("Transition") fun transotionTest() {
        basicWindowTest(slideTransitionButton, "//div[@class='punch-animation-sidebar-header']//div[@title='Закрыть']/div")
    }
}