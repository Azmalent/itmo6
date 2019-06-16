import java.io.File
import java.io.FileNotFoundException

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("Error: missing command line args")
        System.exit(1)
    }

    try {
        val text = File(args[0]).readText()
        val lexer = Lexer(text)
        lexer.printTokens()
    } catch (e: FileNotFoundException) {
        println("Exception: " + e.message)
        System.exit(1)
    }
}