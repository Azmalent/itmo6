package tpo1.tests.menu

import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import tpo1.PresentationPage

class ToolsMenuTests(page: PresentationPage) : AbstractMenuTestSet("Tools menu", page) {
    @FindBy(id = "docs-tools-menu")
    override lateinit var menu: WebElement

    @FindBy(xpath = "//span[contains(@aria-label,'Настройки специальных возможностей c')]")
    lateinit var itemAccessibility: WebElement

    @FindBy(xpath = "//span[@aria-label='Настройки p']")
    lateinit var itemSettings: WebElement

    @FindBy(xpath = "//span[@aria-label='Расширенный поиск r']")
    lateinit var itemSearch: WebElement

    @FindBy(xpath = "//span[@aria-label='Проверка правописания s']")
    lateinit var itemSpelling: WebElement

    @FindBy(xpath = "//span[@aria-label='Словарь d']")
    lateinit var itemDictionary: WebElement

    @Test("Accessibility menu") fun accessibilityTest() {
        basicWindowTest(itemAccessibility, "//div[@class='docs-material-gm-dialog-buttons']//span[text()='Отмена']")
    }

    @Test("Settings") fun settingsTest() {
        basicWindowTest(itemSettings, "//span[@data-tooltip='Закрыть']")
    }

    @Test("Search") fun searchTest() {
        basicWindowTest(itemSearch, "//div[@class='docs-explore-sidebar-title']//div[@aria-label='Закрыть']/div")
    }

    @Test("Spelling") fun spellingTest() {
        basicEscapeTest(itemSpelling, "//span[@aria-label='Подчеркивать ошибки h']")
    }

    @Test("Dictionary") fun dictTest() {
        basicWindowTest(itemDictionary, "//div[@class='docs-dictionary-sidebar docs-material']//div[@aria-label='Закрыть словарь']")
    }
}