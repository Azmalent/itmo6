package dmitry.tpo2.pages

import com.nhaarman.mockitokotlin2.*
import dmitry.tpo2.entity.User
import io.kotlintest.specs.StringSpec
import org.junit.Assert.assertTrue

class LoginPageTests : StringSpec({
    val spy = spy(LoginPage)

    val password = "qwerty123456"
    val fakeUser = mock<User> {
        on { ::hashedPassword.get() } doReturn password.hashCode()
    }

    "Returns MenuPage and validates password" {
        doReturn(fakeUser).whenever(spy).getUser()
        whenever(spy.readUserInput())
                .thenReturn("")
                .thenReturn("123456")
                .thenReturn("йцукен")
                .thenReturn("QwErTy1@3$5^")
                .thenReturn(password)

        val page = spy.runInteractionLogic()
        assertTrue("Page was ${page::class.simpleName}", page is MenuPage)

        verify(spy, times(5)).readUserInput()
    }
})