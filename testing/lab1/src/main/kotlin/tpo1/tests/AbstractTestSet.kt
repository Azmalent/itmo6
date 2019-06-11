package tpo1.tests

import org.openqa.selenium.support.PageFactory
import tpo1.PresentationPage
import java.lang.reflect.InvocationTargetException
import kotlin.reflect.full.functions
import kotlin.reflect.full.memberFunctions

abstract class AbstractTestSet(protected val page: PresentationPage) {
    init {
        PageFactory.initElements(page.driver, this)
    }

    private sealed class TestResult {
        object Success : TestResult()
        data class Failure(val message: String?) : TestResult()
    }

    protected abstract val name: String

    @Target(AnnotationTarget.FUNCTION)
    protected annotation class Test(val spec: String)

    protected fun fail(msg: String) {
        throw AssertionError(msg)
    }

    protected fun failIf(condition: Boolean, msg: String) {
        if (condition) fail(msg)
    }

    protected fun failUnless(condition: Boolean, msg: String) {
        if (!condition) fail(msg)
    }

    private fun runTest(test: () -> Unit): TestResult {
        return try {
            test()
            TestResult.Success
        } catch (e: InvocationTargetException) {
            val cause = e.cause as? AssertionError ?: throw e
            TestResult.Failure(cause.message)
        }
    }

    fun run() {
        val tests = this::class.memberFunctions.filter { func ->
            func.annotations.any { it is Test } && func.parameters.count() == 1
        }.map {test ->
            val spec = test.annotations.filterIsInstance(Test::class.java).single().spec
            Pair(spec, test)
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