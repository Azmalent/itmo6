package tpo1

import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.ui.WebDriverWait

class PresentationPage(driver: WebDriver, wait: WebDriverWait) : AbstractWebPage(driver, wait) {
    companion object {
        const val URL       = """https://docs.google.com/presentation/"""
        const val LOGIN     = "@LOGIN@"
        const val PASSWORD  = "@PASSWORD@"
        const val IMAGE_URL = """https://ssl.gstatic.com/docs/templates/thumbnails/slides-blank-googlecolors.png"""
        const val TITLE     = "Лабораторная работа №1"
    }

    init {
        driver.get(URL)
        login()
        createPresentation()
        do try {
            PageFactory.initElements(driver, this)
            onLoad()
            enterPresentationTitle()
            break
        } catch (e: Exception) {
            reload()
        } while (true)
    }

    @FindBy(css="input.docs-title-input")
    lateinit var nameInput: WebElement

    private fun login() {
        val loginField = findById("identifierId")!!
        val loginNextButton = findById("identifierNext")!!
        loginField.sendKeys(LOGIN)
        loginNextButton.click()

        val passwordField = findByName("password")!!
        val passwordNextButton = findById("passwordNext")!!
        passwordField.sendKeys(PASSWORD)
        passwordNextButton.click()
    }

    private fun createPresentation() {
        val newPresentation = findByCss("div > img[src=\"$IMAGE_URL\"]")!!
        wait.until { newPresentation.isEnabled }
        newPresentation.click()
    }

    override fun onLoad() {
        val locator = By.xpath("//div[@class='punch-theme-sidebar-header']//div[@title='Закрыть']/div")
        find(locator)?.click()
    }

    private fun enterPresentationTitle() {
        wait.until { nameInput.isEnabled }
        Thread.sleep(250)
        nameInput.clear()
        Thread.sleep(250)
        nameInput.sendKeys(TITLE, Keys.ENTER)
    }

    fun deletePresentation() {
        driver.get("https://docs.google.com/presentation/u/0/")
        findByXpath("//div[@class='docs-homescreen-grid-item-metadata-container'][contains(., '$TITLE')]")?.let {
            val button = it.findElement(By.cssSelector("div[aria-label=\"Ещё (раскрывающееся меню)\"]"))
            button.click()

            val deleteButton = findByXpath("//div[@class='goog-menuitem'][contains(., 'Удалить')]")!!
            deleteButton.click()
        }
    }
}