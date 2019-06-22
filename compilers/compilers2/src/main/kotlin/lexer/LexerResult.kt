package lexer

import common.TokenType

sealed class LexerResult(val line: Int, val pos: Int) {
    class Token(val type: TokenType, val value: String, line: Int, pos: Int) : LexerResult(line, pos) {
        override fun toString(): String {
            var result = String.format("%5d  %5d %-15s", line, pos, type)
            if (type == TokenType.INTEGER) result += String.format("  %4s", value)
            else if (type == TokenType.IDENTIFIER) result += String.format(" %s", value)
            return result
        }
    }

    class Error(val message: String, line: Int, pos: Int) : LexerResult(line, pos) {
        override fun toString() : String {
            return if (line > 0 && pos > 0) {
                String.format("%s in line %d, character %d", message, line, pos)
            } else {
                message
            }
        }
    }
}


