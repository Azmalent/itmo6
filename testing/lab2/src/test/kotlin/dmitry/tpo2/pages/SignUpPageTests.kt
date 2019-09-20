package dmitry.tpo2.pages

import com.nhaarman.mockitokotlin2.doNothing
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.whenever
import io.kotlintest.specs.StringSpec
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.mockito.ArgumentMatchers.anyString

class SignUpPageTests : StringSpec({
    val spy = spy(SignUpPage)
    doNothing().whenever(spy).createUser(anyString(), anyString())

    "Password is too short" {
        val success = spy.validatePassword("q123")
        assertFalse(success)
    }

    "Password has no letters" {
        val success = spy.validatePassword("1234567890")
        assertFalse(success)
    }

    "Password has no digits" {
        val success = spy.validatePassword("qwertyuiop")
        assertFalse(success)
    }

    "Valid password" {
        val success = spy.validatePassword("qwerty123456")
        assertTrue(success)
    }
})