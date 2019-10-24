package dmitry.tpo3.tests

import io.appium.java_client.MobileBy
import io.kotlintest.IsolationMode
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import org.openqa.selenium.support.ui.ExpectedConditions


class RegistrationAndLoginTests : AbstractTwitterTestSet() {
    companion object {
        const val REGISTER_BUTTON_ID = "com.twitter.android:id/primary_action"
        const val PHONE_EMAIL_INPUT_ID = "com.twitter.android:id/phone_or_email_field"
        const val USE_EMAIL_BUTTON_ID = "com.twitter.android:id/secondary_button"
        const val NEXT_BUTTON_ID = "com.twitter.android:id/cta_button"
    }

    override fun isolationMode(): IsolationMode? = IsolationMode.InstancePerLeaf

    init {
        "Registration" should {
            findById(REGISTER_BUTTON_ID).click()

            val textField = findById(PHONE_EMAIL_INPUT_ID)
            val nextButton = findById(NEXT_BUTTON_ID)

            "fail with a non-existing email" {
                textField.click()

                val useEmail = findById(USE_EMAIL_BUTTON_ID)
                useEmail.click()

                textField.sendKeys("invalidmail@domain.null")
                nextButton.isEnabled shouldBe false
                textField.clear()

                useEmail.click()

                goBack()
            }

            "fail with a non-existing phone number" {
                textField.sendKeys("+1-202-555-0180")
                nextButton.isEnabled shouldBe false
                textField.clear()

                goBack()
            }
        }

        "Log in" should {
            val loginText = findById(LOGIN_TEXT_ID)
            with(loginText.location) {
                x += 600
                clickAt(this)
            }

            val usernameInput = findById(USERNAME_INPUT_ID)
            val passwordInput = findById(PASSWORD_INPUT_ID)
            val loginButton = findById(LOGIN_BUTTON_ID)

            "fail with invalid username" {
                usernameInput.sendKeys(USERNAME.reversed())
                passwordInput.sendKeys(PASSWORD)
                loginButton.click()

                wait.until {
                    ExpectedConditions.invisibilityOfElementLocated(MobileBy.id("android:id/progress"))
                }

                findById(USERNAME_INPUT_ID).isDisplayed shouldBe true

                goBack()
            }

            "fail with invalid password" {
                usernameInput.sendKeys(USERNAME)
                passwordInput.sendKeys(PASSWORD.reversed())
                loginButton.click()

                wait.until {
                    ExpectedConditions.invisibilityOfElementLocated(MobileBy.id("android:id/progress"))
                }

                findById(USERNAME_INPUT_ID).isDisplayed shouldBe true

                goBack()
            }

            "show/hide password" {
                val buttonLocation = passwordInput.location
                buttonLocation.x += 950

                passwordInput.sendKeys(PASSWORD)

                clickAt(buttonLocation)
                passwordInput.text shouldBe PASSWORD
                clickAt(buttonLocation)
                passwordInput.text shouldNotBe PASSWORD

                goBack()
            }
        }
    }
}