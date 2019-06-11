package tpo1

import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.WebDriverWait
import tpo1.tests.FileMenuTests
import java.util.concurrent.TimeUnit

class TestRunner {
    private val driver = FirefoxDriver()
    private val wait = WebDriverWait(driver, 10)
    private var page: PresentationPage

    init {
        try {
            System.setProperty("webdriver.gecko.driver", "@DRIVER_LOCATION@")
            driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS)
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
            driver.manage().window().maximize()

            page = PresentationPage(driver, wait)
        } catch (ex: Exception) {
            driver.quit()
            throw ex
        }
    }

    fun runTests() {
        FileMenuTests(page).run()
    }

    fun cleanup() {
        page.deletePresentation()
        driver.quit()
    }
}

fun main(args: Array<String>) {
    var runner: TestRunner? = null
    try {
        runner = TestRunner()
        runner.runTests()
    } finally {
        runner?.cleanup()
    }
}