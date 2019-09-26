package tpo1

import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.WebDriverWait
import tpo1.tests.BarTests
import tpo1.tests.HiddenBarTests
import tpo1.tests.RightClickTests
import tpo1.tests.menu.*
import java.util.concurrent.TimeUnit


class TestRunner {
    private val driver = initDriver()
    private val wait = WebDriverWait(driver, 10)
    private var page: PresentationPage

    init {
        try {
            driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS)
            driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS)
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS)
            driver.manage().window().maximize()

            page = PresentationPage(driver, wait)
        } catch (ex: Exception) {
            driver.quit()
            throw ex
        }
    }

    private fun initDriver(): FirefoxDriver {
        System.setProperty("webdriver.gecko.driver", "@DRIVER_LOCATION@")
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null")

        return FirefoxDriver()
    }

    fun runTests() {
        FileMenuTests(page).run()
        page.reload()
        ViewMenuTests(page).run()
        page.reload()
        InsertMenuTests(page).run()
        page.reload()
        SlideMenuTests(page).run()
        page.reload()
        ToolsMenuTests(page).run()
        page.reload()

        BarTests(page).run()
        HiddenBarTests(page).run()
        RightClickTests(page).run()
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