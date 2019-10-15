package dmitry.tpo3

import io.appium.java_client.MobileBy
import io.appium.java_client.MobileElement
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.remote.AndroidMobileCapabilityType
import io.appium.java_client.remote.MobileCapabilityType
import io.appium.java_client.touch.offset.PointOption
import org.openqa.selenium.NoSuchElementException
import org.openqa.selenium.Point
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.net.URL
import java.util.concurrent.TimeUnit


object CommonContext {
    const val USERNAME = "Tester1234519"
    const val PASSWORD = "qwerty123456"

    val driver: AndroidDriver<MobileElement>
    val wait: WebDriverWait

    init {
        val url = URL("http://127.0.0.1:4723/wd/hub")
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

        driver = AndroidDriver<MobileElement>(url, capabilities)
        wait = WebDriverWait(driver, 30)

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS)
    }
}