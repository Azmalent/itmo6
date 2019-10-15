package dmitry.tpo3

import dmitry.tpo3.CommonContext
import dmitry.tpo3.PlatformTouchAction
import io.appium.java_client.MobileBy
import io.appium.java_client.MobileElement
import io.appium.java_client.touch.offset.PointOption
import org.openqa.selenium.NoSuchElementException
import org.openqa.selenium.Point
import org.openqa.selenium.support.ui.ExpectedConditions

object Utils {
    fun findById(id: String): MobileElement {
        return CommonContext.driver.findElementById(id)
    }

    fun tryFindById(id: String): MobileElement? {
        return try {
            CommonContext.driver.findElementById(id)
        } catch (e: NoSuchElementException) {
            null
        }
    }

    fun waitById(id: String): MobileElement {
        val by = MobileBy.id(id)
        CommonContext.wait.until {
            ExpectedConditions.presenceOfElementLocated(by)
        }
        return CommonContext.driver.findElement(by)
    }

    fun clickAt(p: Point) {
        PlatformTouchAction(CommonContext.driver).tap(PointOption.point(p)).perform()
    }
}