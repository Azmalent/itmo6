package tpo1.tests

import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import tpo1.PresentationPage

class HiddenBarTests(page: PresentationPage) : AbstractTestSet("Hidden bar", page) {
    @FindBy(id = "canvas")
    lateinit var canvas: WebElement

    @FindBy(id = "textColorButton")
    lateinit var textColorButton: WebElement

    @FindBy(id = "fillColorMenuButton")
    lateinit var fillColorMenuButton: WebElement

    @FindBy(id = "docs-font-family")
    lateinit var docsFontFamilyButton: WebElement

    @FindBy(id = "fontSizeSelect")
    lateinit var fontSizeSelect: WebElement

    @FindBy(id = "lineColorMenuButton")
    lateinit var lineColorMenuButton: WebElement

    @FindBy(id = "lineDashingMenuButton")
    lateinit var lineDashingMenuButton: WebElement

    @FindBy(id = "lineWidthMenuButton")
    lateinit var lineWidthMenuButton: WebElement



    private fun showHiddenBar() {
        page.actions.moveToElement(canvas).click().perform()
    }

    override fun initializeTest() {
        showHiddenBar()
    }



    @Test("Text color") fun textColorTest() {
        basicEscapeTest(textColorButton, "//div[@style='user-select: none; visibility: visible; left: 817.6px; top: 97.3667px;']")
    }

    @Test("Fill color") fun fillColorTest() {
        basicEscapeTest(fillColorMenuButton, "//div[text()='Один цвет']")
    }

    @Test("Docs font family") fun docsFontFamilyTest() {
        basicEscapeTest(docsFontFamilyButton, "//div[text()='Другие шрифты']")
    }

    @Test("Font size selection") fun fontSizeTest() {
        basicEscapeTest(fontSizeSelect, "//div[text()='96']")
    }

    @Test("Line color") fun lineColorTest() {
        basicEscapeTest(lineColorMenuButton, "//div[@style='user-select: none; visibility: visible; left: 450.4px; top: 97.3667px;']")
    }

    @Test("Line dashing") fun lineDashingTest() {
        basicEscapeTest(lineDashingMenuButton, "//div[@aria-label='Пунктир: Сплошная']")
    }

    @Test("Line width") fun lineWidthTest() {
        basicEscapeTest(lineWidthMenuButton, "//div[text()='1 пикс.']")
    }
}