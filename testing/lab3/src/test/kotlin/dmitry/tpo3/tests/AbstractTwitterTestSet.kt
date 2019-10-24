package dmitry.tpo3.tests

import dmitry.tpo3.PlatformTouchAction
import io.appium.java_client.MobileBy
import io.appium.java_client.MobileElement
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.android.nativekey.AndroidKey
import io.appium.java_client.android.nativekey.KeyEvent
import io.appium.java_client.remote.AndroidMobileCapabilityType
import io.appium.java_client.remote.MobileCapabilityType
import io.appium.java_client.touch.offset.PointOption
import io.appium.java_client.windows.PressesKeyCode
import io.kotlintest.IsolationMode
import io.kotlintest.Spec
import io.kotlintest.TestCaseOrder
import io.kotlintest.shouldThrow
import io.kotlintest.specs.WordSpec
import org.openqa.selenium.By
import org.openqa.selenium.NoSuchElementException
import org.openqa.selenium.Point
import org.openqa.selenium.StaleElementReferenceException
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.net.URL
import java.util.concurrent.TimeUnit

abstract class AbstractTwitterTestSet() : WordSpec() {
    companion object {
        const val USERNAME = "Tester1234519"
        const val PASSWORD = "qwerty123456"

        const val LOGIN_TEXT_ID = "com.twitter.android:id/detail_text"
        const val USERNAME_INPUT_ID = "com.twitter.android:id/login_identifier"
        const val PASSWORD_INPUT_ID = "com.twitter.android:id/login_password"
        const val LOGIN_BUTTON_ID = "com.twitter.android:id/login_login"

        @JvmStatic protected val driver: AndroidDriver<MobileElement>
        @JvmStatic protected val wait: WebDriverWait

        init {
            val capabilityDict = mapOf(
                MobileCapabilityType.DEVICE_NAME            to "emulator-5554",
                MobileCapabilityType.PLATFORM_NAME          to "Android",
                MobileCapabilityType.PLATFORM_VERSION       to "10",
                MobileCapabilityType.NO_RESET               to "true",
                MobileCapabilityType.AUTOMATION_NAME        to "UIAutomator2",
                AndroidMobileCapabilityType.APP_PACKAGE     to "com.twitter.android",
                AndroidMobileCapabilityType.APP_ACTIVITY    to "com.twitter.android.StartActivity"
            )

            val capabilities = DesiredCapabilities()
            capabilityDict.forEach { (name, value) ->
                capabilities.setCapability(name, value)
            }

            val url = URL("http://127.0.0.1:4723/wd/hub")

            driver = AndroidDriver<MobileElement>(url, capabilities)
            wait = WebDriverWait(driver, 30)

            driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS)
        }
    }

    override fun testCaseOrder(): TestCaseOrder? = TestCaseOrder.Random

    private fun find(locator: By): MobileElement {
        wait.until {
            ExpectedConditions.visibilityOfElementLocated(locator)
        }
        return driver.findElement(locator)
    }

    protected fun findById(id: String): MobileElement = find(MobileBy.id(id))
    protected fun findByXpath(xpath: String): MobileElement = find(MobileBy.xpath(xpath))

    protected fun goBack() {
        driver.pressKey( KeyEvent(AndroidKey.BACK) )
    }

    protected fun goBack(n: Int) {
        for (i in 0 until n) {
            driver.pressKey( KeyEvent(AndroidKey.BACK) )
        }
    }

    protected fun clickAt(p: Point) {
        PlatformTouchAction(driver).tap(PointOption.point(p)).perform()
    }
}