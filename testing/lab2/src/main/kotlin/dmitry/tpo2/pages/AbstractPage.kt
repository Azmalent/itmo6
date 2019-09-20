package dmitry.tpo2.pages

abstract class AbstractPage {
    protected abstract val pageTitle: String

    abstract fun runInteractionLogic(): AbstractPage?

    fun interact() : AbstractPage? {
        println("\n--------------$pageTitle--------------")
        return runInteractionLogic()
    }

    fun readUserInput(default: String = ""): String {
        return readLine() ?: default
    }

    fun readInt(): Int {
        val input = readLine()
        return Integer.parseInt(input ?: "0")
    }

    fun anykey() {
        println("\nНажмите Enter, чтобы продолжить")
        readLine()
    }
}
