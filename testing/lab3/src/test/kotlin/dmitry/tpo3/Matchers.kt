package dmitry.tpo3

import io.appium.java_client.MobileElement
import io.kotlintest.Matcher
import io.kotlintest.MatcherResult
import io.kotlintest.should
import org.openqa.selenium.StaleElementReferenceException

object Matchers {
    fun beStale() = object : Matcher<MobileElement> {
        override fun test(element: MobileElement): MatcherResult {
            val isStale = try {
                element.isDisplayed
                false
            } catch(e: StaleElementReferenceException) {
                true
            }

            return MatcherResult(isStale,
                "Element $element should be stale",
                "Element $element should not be stale")
        }
    }
}