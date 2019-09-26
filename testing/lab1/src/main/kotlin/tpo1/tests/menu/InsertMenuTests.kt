package tpo1.tests.menu

import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import tpo1.PresentationPage

class InsertMenuTests(page: PresentationPage) : AbstractMenuTestSet("Insert menu", page) {
    @FindBy(id = "docs-insert-menu")
    override lateinit var menu: WebElement

    @FindBy(xpath = "//span[@aria-label='Анимация a']")
    lateinit var itemAnimation: WebElement

    @FindBy(xpath = "//span[contains(@aria-label,'Оставить комментарий m')]")
    lateinit var itemComment: WebElement

    @FindBy(xpath = "//span[@aria-label='Номера слайдов e']")
    lateinit var itemEnum: WebElement

    @FindBy(xpath = "//span[@aria-label='Диаграмма h']")
    lateinit var itemDiagram: WebElement

    @FindBy(xpath = "//span[@aria-label='Таблица b']")
    lateinit var itemTable: WebElement

    @FindBy(xpath = "//span[@aria-label='Текстовое поле t']")
    lateinit var itemTextField: WebElement

    @FindBy(xpath = "//span[@aria-label='Word Art w']")
    lateinit var itemWordArt: WebElement

    @Test("Animation") fun animationTest() {
        basicWindowTest(itemAnimation, "//div[@class='punch-animation-sidebar-header']//div[@title='Закрыть']/div")
    }

    @Test("Comment") fun commentTest() {
        basicWindowTest(itemComment, "//div[@data-tooltip='Отмена отправки комментария']")
    }

    @Test("Enum") fun enumTest() {
        basicEscapeTest(itemEnum, "//span[@class='modal-dialog-title-text'][contains(.,'Номера слайдов')]")
    }

    @Test("Diagram") fun diagramTest() {
        basicEscapeTest(itemDiagram, "//span[text()='Линейчатая']")
    }

    @Test("Table") fun tableTest() {
        basicEscapeTest(itemTable, "//div[text()='\u202A1 x 1\u202C']")
    }

    @Test("Text field") fun textFieldTest() {
        menu.click()
        itemTextField.click()
        page.findById("textboxButton")?.let { button ->
            if (button.getAttribute("aria-pressed") != "true") fail("Button doesn't press first time")
            menu.click()
            itemTextField.click()
            if (button.getAttribute("aria-pressed") != "false") fail("Button doesn't press second time")
        } ?: fail ("Doesn't open")
    }

    @Test("WordArt") fun wordArtTest() {
        basicEscapeTest(itemWordArt, "//textarea[@class='sketchy-bubble-textarea']")
    }
}