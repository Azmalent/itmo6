package tpo1

import org.openqa.selenium.*
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.ui.WebDriverWait

class PresentationPage(val driver: WebDriver, val wait: WebDriverWait) {
    companion object {
        const val URL = """https://docs.google.com/presentation/"""
        const val LOGIN = "@LOGIN@"
        const val PASSWORD = "@PASSWORD@"
        const val IMAGE_URL = """https://ssl.gstatic.com/docs/templates/thumbnails/slides-blank-googlecolors.png"""
        const val TITLE = "Лабораторная работа №1"
    }

    init {
        driver.get(URL)
        login()
        createPresentation()
        PageFactory.initElements(driver, this)
        enterPresentationTitle()
    }

    private fun login() {
        val loginField = driver.findElement(By.id("identifierId"))
        val loginNextButton = driver.findElement(By.id("identifierNext"))
        loginField.sendKeys(LOGIN)
        loginNextButton.click()

        Thread.sleep(2000)  //todo: remove this shit code
        val passwordField = driver.findElement(By.cssSelector("input[type=\"password\"]"))
        val passwordNextButton = driver.findElement(By.id("passwordNext"))
        wait.until { passwordField.isDisplayed }
        passwordField.sendKeys(PASSWORD)
        passwordNextButton.click()
    }

    @FindBy(css="input.docs-title-input")
    lateinit var nameInput: WebElement

    @FindBy(id="docs-file-menu")
    lateinit var fileMenu: WebElement

    @FindBy(xpath="//div[@class='goog-menuitem apps-menuitem'][contains(.,'Совместный доступ')]")
    lateinit var itemSharedAccess: WebElement

    private fun createPresentation() {
        val newPresentation = driver.findElement(By.cssSelector("div > img[src=\"$IMAGE_URL\"]"))
        wait.until { newPresentation.isDisplayed }
        newPresentation.click()
    }

    private fun enterPresentationTitle() {
        wait.until { nameInput.isDisplayed }
        wait.until { nameInput.isEnabled }
        nameInput.clear()
        nameInput.sendKeys(TITLE)
        nameInput.sendKeys(Keys.ENTER)
    }

    fun deletePresentation() {
        driver.get("https://docs.google.com/presentation/u/0/")
        driver.findElements(
                By.xpath("//div[@class='docs-homescreen-grid-item-metadata-container'][contains(., '$TITLE')]"
        )).forEach {
            val button = it.findElement(By.cssSelector("div[aria-label=\"Ещё (раскрывающееся меню)\"]"))
            button.click()

            val deleteButton = driver.findElement(By.xpath("//div[@class='goog-menuitem'][contains(., 'Удалить')]"))
            wait.until { deleteButton.isDisplayed }
            deleteButton.click()
        }
    }
}