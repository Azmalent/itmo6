package dmitry.tpo3.tests

import dmitry.tpo3.CommonContext
import dmitry.tpo3.CommonContext.PASSWORD
import dmitry.tpo3.CommonContext.driver
import dmitry.tpo3.PlatformTouchAction
import dmitry.tpo3.Utils.clickAt
import dmitry.tpo3.Utils.findById
import dmitry.tpo3.Utils.waitById
import io.appium.java_client.MobileBy
import io.appium.java_client.touch.offset.PointOption.point
import io.kotlintest.IsolationMode
import io.kotlintest.Spec
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.WordSpec
import org.openqa.selenium.support.ui.ExpectedConditions


class RegistrationAndLoginTests : WordSpec() {
    override fun isolationMode(): IsolationMode? = IsolationMode.InstancePerLeaf

    override fun afterSpec(spec: Spec) {
        CommonContext.driver.resetApp()
    }

    init {
        "Registration" should {
            val registerButton = waitById("com.twitter.android:id/primary_action")
            registerButton.click()

            val textField = waitById("com.twitter.android:id/phone_or_email_field")
            val nextButton = waitById("com.twitter.android:id/cta_button")

            "fail with a non-existing email" {
                textField.click()

                val useEmail = findById("com.twitter.android:id/secondary_button")
                useEmail.click()

                textField.sendKeys("invalidmail@domain.null")
                nextButton.isEnabled shouldBe false
                textField.clear()

                useEmail.click()
            }

            "fail with a non-existing phone number" {
                textField.sendKeys("+1-202-555-0180")
                nextButton.isEnabled shouldBe false
                textField.clear()
            }
        }

        "Sign in" should {
            val detailText = waitById("com.twitter.android:id/detail_text")
            with(detailText.location) {
                x += 500
                clickAt(this)
            }

            val usernameInput = findById("com.twitter.android:id/login_identifier")
            val passwordInput = findById("com.twitter.android:id/login_password")
            val loginButton = findById("com.twitter.android:id/login_login")

            val progressBarLocator = MobileBy.id("android:id/progress")

            "fail with invalid username" {
                usernameInput.sendKeys(CommonContext.USERNAME.reversed())
                passwordInput.sendKeys(CommonContext.PASSWORD)
                loginButton.click()

                CommonContext.wait.until {
                    ExpectedConditions.invisibilityOfElementLocated(progressBarLocator)
                }

                findById("com.twitter.android:id/login_identifier").isDisplayed shouldBe true
            }

            "fail with invalid password" {
                usernameInput.sendKeys(CommonContext.USERNAME)
                passwordInput.sendKeys(CommonContext.PASSWORD.reversed())
                loginButton.click()

                CommonContext.wait.until {
                    ExpectedConditions.invisibilityOfElementLocated(progressBarLocator)
                }

                findById("com.twitter.android:id/login_identifier").isDisplayed shouldBe true
            }

            "show/hide password" {
                val buttonLocation = passwordInput.location
                buttonLocation.x += 950

                passwordInput.sendKeys(PASSWORD)

                clickAt(buttonLocation)
                passwordInput.text shouldBe PASSWORD
                clickAt(buttonLocation)
                passwordInput.text shouldNotBe PASSWORD
            }

/*            "succeed with valid credentials" {
                usernameInput.sendKeys(CommonContext.USERNAME)
                passwordInput.sendKeys(CommonContext.PASSWORD)
                loginButton.click()

                CommonContext.wait.until {
                    ExpectedConditions.invisibilityOfElementLocated(progressBarLocator)
                }

                shouldThrow<NoSuchElementException> {
                    findById("com.twitter.android:id/login_identifier")
                }
            }*/
        }
    }
}