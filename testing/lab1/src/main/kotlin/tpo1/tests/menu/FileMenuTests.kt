package tpo1.tests.menu

import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import tpo1.PresentationPage

class FileMenuTests(page: PresentationPage) : AbstractMenuTestSet("File menu", page) {
    @FindBy(id = "docs-file-menu")
    override lateinit var menu: WebElement

    @FindBy(xpath = "//span[contains(@aria-label,'Создать n')]")
    lateinit var itemNew: WebElement

    @FindBy(xpath = "//span[@aria-label='Создать копию c']")
    lateinit var itemCreateCopy: WebElement

    @FindBy(xpath = "//span[@aria-label='Скачать d']")
    lateinit var itemDownloadAs: WebElement

    @FindBy(xpath = "//span[contains(@aria-label,'Информация о файле b')]")
    lateinit var itemInfo: WebElement

    @FindBy(xpath = "//span[@aria-label='Язык l']")
    lateinit var itemLanguage: WebElement

    @FindBy(xpath = "//span[@aria-label='Настройки страницы g']")
    lateinit var itemSettings: WebElement

    @Test("New") fun newTest() {
        basicEscapeTest(itemNew, "//span[contains(.,'Создать документ по шаблону')]")
    }

    @Test("Copy") fun copyTest() {
        basicEscapeTest(itemCreateCopy, "//span[contains(.,'Копировать документ')]")
    }

    @Test("Download") fun downloadTest() {
        basicDropdownMenuTest(itemDownloadAs, "//span[contains(.,'Microsoft PowerPoint (.pptx)')]")
    }

    @Test("Document info") fun docInfoTest() {
        basicEscapeTest(itemInfo, "//td[contains(.,'Местоположение')]")
    }

    @Test("Language") fun langTest() {
        basicDropdownMenuTest(itemLanguage, "//span[@lang='de']")
    }

    @Test("Settings") fun settingsTest() {
        basicEscapeTest(itemSettings, "//div[contains(.,'Широкоэкранный (16:9)')]")
    }
}