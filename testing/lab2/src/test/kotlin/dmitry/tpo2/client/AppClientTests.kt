package dmitry.tpo2.client

import com.nhaarman.mockitokotlin2.doNothing
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.whenever
import dmitry.tpo2.pages.LoginPage
import dmitry.tpo2.pages.SignUpPage
import io.kotlintest.specs.StringSpec
import org.junit.Assert.assertTrue

class AppClientTests : StringSpec({
    val spy = spy(AppClient())
    doNothing().whenever(spy).interact()

    "Returns SignUpPage when there are no users" {
        doReturn(false).whenever(spy).hasUsers
        spy.start()
        assertTrue("Page was ${spy.page!!::class.simpleName}", spy.page is SignUpPage)
    }

    "Returns LoginPage when there is at least one user" {
        doReturn(true).whenever(spy).hasUsers
        spy.start()
        assertTrue("Page was ${spy.page!!::class.simpleName}", spy.page is LoginPage)
    }
})