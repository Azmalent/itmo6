package tpo1.tests

import org.openqa.selenium.ElementNotInteractableException
import org.openqa.selenium.Keys
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.ui.ExpectedConditions
import tpo1.PresentationPage
import kotlin.reflect.full.memberFunctions

abstract class AbstractTestSet(private val name: String, protected val page: PresentationPage) {
    init {
        PageFactory.initElements(page.driver, this)
    }

    @Target(AnnotationTarget.FUNCTION)
    protected annotation class Test(val spec: String)

    protected sealed class TestResult {
        object Success : TestResult()
        data class Failure(val message: String?) : TestResult()
    }

    abstract fun initializeTest()

    protected fun basicWindowTest(item: WebElement, expectedXpath: String) {
        initializeTest()
        item.click()
        page.findByXpath(expectedXpath)?.click() ?: fail("Element with xpath \"$expectedXpath\" not found.")
    }

    protected fun basicEscapeTest(item: WebElement, expectedXpath: String) {
        initializeTest()
        item.click()
        assertExists(expectedXpath)
        page.actions.sendKeys(Keys.ESCAPE).perform()
    }



    protected fun fail(msg: String) {
        throw AssertionError(msg)
    }

    protected fun assertExists(xpath: String) {
        val item = page.findByXpath(xpath)
        page.wait.until { ExpectedConditions.visibilityOf(item) }
        if (item == null) fail("Element with xpath \"$xpath\" not found.")
    }

    private fun runTest(test: () -> Unit): TestResult {
        try {
            while (true) try {
                test()
                Thread.sleep(200)
                return TestResult.Success
            } catch(e: ElementNotInteractableException) { }
        } catch (e: Exception) {
            val cause = e.cause ?: e
            page.reload()
            return TestResult.Failure(when (cause) {
                is AssertionError -> cause.message
                else -> "${cause::class.simpleName}: ${cause.localizedMessage.substringBefore('\n')}"
            })
        }
    }

    open fun run() {
        val tests = this::class.memberFunctions.filter { func ->
            func.annotations.any { it is Test } && func.parameters.count() == 1
        }.map { testFunc ->
            val annotation = testFunc.annotations.filterIsInstance(Test::class.java).single()
            Pair(annotation.spec, testFunc)
        }.toMap().toSortedMap()

        println("----------  ${this.name.toUpperCase()} ----------")
        var successfulTests = 0
        tests.forEach { spec, test ->
            val result = runTest { test.call(this) }
            if (result is TestResult.Success) successfulTests++
            val resultText = when (result) {
                is TestResult.Failure -> "✗ (${result.message ?: "no message"})"
                else -> "✔"
            }
            println("$spec: $resultText")
        }
        println("$successfulTests/${tests.count()} succeeded\n")
    }
}