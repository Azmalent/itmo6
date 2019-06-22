package lexer

import java.io.File
import java.io.FileNotFoundException

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("lexer.Error: missing command line args")
        System.exit(1)
    }

    args.forEach {
        try {
            val file = File(it)
            println('\n' + file.name)
            println("-----------------------------------------------")
            val lexer = Lexer(file.readText())
            for (token in lexer.scan()) {
                println(token)
            }
        } catch (e: FileNotFoundException) {
            println("Exception: " + e.message)
            System.exit(1)
        }
    }
}