package dmitry.tpo3.tests

import dmitry.tpo3.PlatformTouchAction
import dmitry.tpo3.Matchers.beStale
import io.appium.java_client.android.nativekey.AndroidKey
import io.appium.java_client.android.nativekey.KeyEvent
import io.appium.java_client.touch.offset.PointOption.point
import io.kotlintest.*
import io.kotlintest.extensions.TopLevelTest
import io.kotlintest.matchers.collections.shouldBeEmpty
import io.kotlintest.matchers.numerics.shouldBeGreaterThan
import io.kotlintest.matchers.string.shouldStartWith
import org.openqa.selenium.NoSuchElementException
import org.openqa.selenium.Point

class AppTests : AbstractTwitterTestSet() {
    companion object {
        const val MENU_BUTTON_XPATH = "//*[@resource-id='com.twitter.android:id/toolbar']/*[1]"
        const val SETTINGS_XPATH = "//android.widget.TextView[@text='Settings and privacy']"
        const val ACCOUNT_SETTINGS_XPATH = "//android.widget.TextView[@text='Account']"

        const val SEARCH_ID = "com.twitter.android:id/moments"
        const val SEARCH_QUERY_VIEW_ID = "com.twitter.android:id/query_view"
        const val SEARCH_QUERY_ID = "com.twitter.android:id/query"
        const val SEARCH_PEOPLE_XPATH = "//*[@resource-id='com.twitter.android:id/search_activity_tabs']/*[1]/*[3]"

        const val NEW_TWEET_BUTTON_ID = "com.twitter.android:id/composer_write"
        const val TWEET_BUTTON_ID = "com.twitter.android:id/button_tweet"
        const val TWEET_TEXT_ID = "com.twitter.android:id/tweet_text"
        const val TWEET_CURATION_ID = "com.twitter.android:id/tweet_curation_action"

        const val FOLLOW_BUTTON_ID = "com.twitter.android:id/button_bar_follow"
        const val FOLLOWING_BUTTON_ID = "com.twitter.android:id/button_bar_following"
        const val UNBLOCK_BUTTON_ID = "com.twitter.android:id/button_bar_blocked"

        const val CONFIRM_BUTTON_ID = "android:id/button1"
    }

    override fun beforeSpecClass(spec: Spec, testsll: List<TopLevelTest>) {
        val detailText = findById(LOGIN_TEXT_ID)
        with(detailText.location) {
            x += 600
            clickAt(this)
        }

        findById(USERNAME_INPUT_ID).sendKeys(USERNAME)
        findById(PASSWORD_INPUT_ID).sendKeys(PASSWORD)
        findById(LOGIN_BUTTON_ID).click()

        Thread.sleep(5000)
    }

    private fun search(query: String) {
        findById(SEARCH_ID).click()
        findById(SEARCH_QUERY_VIEW_ID).click()
        findById(SEARCH_QUERY_ID).sendKeys(query)
        driver.pressKey(KeyEvent(AndroidKey.ENTER))
    }

    init {
        "Tweets" should {
            "be writable" {
                var success = false
                while (!success) {
                    success = try {
                        driver.findElementById(NEW_TWEET_BUTTON_ID).click()
                        false
                    } catch (e: Exception) {
                        true
                    }
                }

                findById(TWEET_TEXT_ID).sendKeys("Hello, world!")
                findById(TWEET_BUTTON_ID).click()

                driver.findElementsById("com.twitter.android:id/row").size shouldBe 1
            }

            "be deletable" {
                Thread.sleep(5000)
                findById(TWEET_CURATION_ID).click()
                findByXpath("//*[@text='Delete Tweet']").click()
                findById(CONFIRM_BUTTON_ID).click()

                Thread.sleep(5000)
                driver.findElementsById("com.twitter.android:id/row").size shouldBe 0
            }

            "be searchable" {
                search("#итмо")

                driver.findElementsById("com.twitter.android:id/row").size shouldBeGreaterThan 0
            }

            goBack(3)
        }

        "People" should {
            val menuButton = Point(1000, 150)

            "be searchable" {
                search("@realdonaldtrump")

                findByXpath(SEARCH_PEOPLE_XPATH).click()
                findById("com.twitter.android:id/timeline_user_social_row_view").click()
            }

            "be followable" {
                val button = findById(FOLLOW_BUTTON_ID)
                button.click()

                button should beStale()
            }

            "be unfollowable" {
                val button = findById(FOLLOWING_BUTTON_ID)
                button.click()

                findById(CONFIRM_BUTTON_ID).click()

                button should beStale()
            }

            "be mutable" {
                clickAt(menuButton)
                findByXpath("//*[@text='Mute']").click()

                val mutedText = findById("com.twitter.android:id/profile_muted")
                mutedText.text shouldStartWith "You have muted Tweets from this account"
            }

            "be unmutable" {
                clickAt(menuButton)
                findByXpath("//*[@text='Unmute']").click()
            }

            "be blockable" {
                clickAt(menuButton)
                findByXpath("//*[@text='Block']").click()
                findById(CONFIRM_BUTTON_ID).click()

                driver.findElementsById(UNBLOCK_BUTTON_ID).size shouldBe 1
            }

            "be unblockable" {
                findById(UNBLOCK_BUTTON_ID).click()
                findById(CONFIRM_BUTTON_ID).click()
            }

            goBack(3)
        }
    }

    override fun afterSpecClass(spec: Spec, results: Map<TestCase, TestResult>) {
        findByXpath(MENU_BUTTON_XPATH).click()
        findByXpath(SETTINGS_XPATH).click()
        findByXpath(ACCOUNT_SETTINGS_XPATH).click()

        val logoutButtonPosition = Point(500, 1500)
        val p1 = logoutButtonPosition
        val p2 = Point(p1.x, p1.y - 800)

        PlatformTouchAction(driver).press(point(p1)).moveTo(point(p2)).release().perform()

        clickAt(logoutButtonPosition)

        val okButton = findById("android:id/button1")
        okButton.click()
    }
}