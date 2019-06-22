package parser

import lexer.Lexer
import java.io.File
import java.io.FileNotFoundException

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("lexer.Error: missing command line args")
        System.exit(1)
    }

    args.forEach {
        try {
            println('\n' + it)
            println("-----------------------------------------------")
            Parser.fromFile(it)?.let { parser ->
                val ast = parser.parse()
                ast.prettyPrint()
            }
        } catch (e: FileNotFoundException) {
            println("Exception: " + e.message)
            System.exit(1)
        }
    }

}