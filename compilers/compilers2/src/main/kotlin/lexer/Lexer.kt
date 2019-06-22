package lexer

import common.TokenType

internal typealias Token = LexerResult.Token
internal typealias Error = LexerResult.Error

class Lexer (private val programText: String) {
    companion object {
        const val EOF = '\u0000'

        private val keywords = mapOf(
            "Begin" to TokenType.KEYWORD_BEGIN,
            "End"   to TokenType.KEYWORD_END,
            "Int"   to TokenType.KEYWORD_INT,
            "Bin"   to TokenType.KEYWORD_BIN
        )

        private val singleCharLexemes = mapOf(
            '&' to TokenType.OP_AND,
            '|' to TokenType.OP_OR,
            '^' to TokenType.OP_XOR,
            '(' to TokenType.L_BRACKET,
            ')' to TokenType.R_BRACKET,
            '+' to TokenType.OP_ADD,
            '*' to TokenType.OP_MUL,
            ',' to TokenType.COMMA,
            ';' to TokenType.SEMICOLON
        )
    }

    private var lineNum = 1
    private var linePos = 0
    private var textPos = 0
    private var char = programText[0]
    val tokens = mutableListOf<LexerResult>()
    val lastToken
        get() = tokens.last()

    private fun nextToken(): LexerResult {
            val line = lineNum
            val pos = linePos
            while (char.isWhitespace()) nextChar()

            return when (char) {
                EOF -> Token(TokenType.END_OF_INPUT, "", this.lineNum, this.linePos)
                '/' -> divOrComment(line, pos)
                ':' -> assignOrError(line, pos)
                '-' -> subOrNegate(line, pos)
                else -> singleCharLexemes[char]?.let {
                    nextChar()
                    Token(it, "", line, pos)
                } ?: identifierOrLiteral(line, pos)
            }
        }

    private fun nextChar(): Char {
            linePos++
            textPos++
            if (textPos >= programText.length) {
                char = EOF
            }
            else {
                char = programText[textPos]
                if (char == '\n') {
                    lineNum++
                    linePos = 0
                }
            }
            return char
        }

    private fun error(msg: String, line: Int, pos: Int): Error {
        nextChar()
        return Error(msg, line, pos)
    }

    private fun assignOrError(line: Int, pos: Int): LexerResult {
        if (nextChar() == '=') {
            nextChar()
            return Token(TokenType.OP_ASSIGN, "", line, pos)
        }
        return error(String.format("assignOrError: unrecognized character: (%d) '%c'", char.toInt(), char), line, pos)
    }

    private fun divOrComment(line: Int, pos: Int): LexerResult {
        if (nextChar() != '/') {
            return Token(TokenType.OP_DIV, "", line, pos)
        }
        while (char != EOF && char != '\n') nextChar()
        return nextToken()
    }

    private fun subOrNegate(line: Int, pos: Int): LexerResult {
        val type: TokenType = when {
            tokens.isEmpty() || lastToken is Error -> TokenType.OP_NEGATE
            (lastToken as Token).type.isOperator -> TokenType.OP_NEGATE
            else -> TokenType.OP_SUB
        }
        nextChar()
        return Token(type, "", line, pos)
    }

    private fun identifierOrLiteral(line: Int, pos: Int): LexerResult {
        var allLetters = true
        var allDigits = true
        var text = ""

        while (char.isLetterOrDigit()) {
            text += char
            allLetters = allLetters && char.isLetter()
            allDigits = allDigits && char.isDigit()
            nextChar()
        }

        return when (text) {
            "" -> error(String.format("identifierOrLiteral: unrecognized character: (%d) %c", char.toInt(), char), line, pos)
            "0" -> Token(TokenType.BINARY, "0", line, pos)
            "1" -> Token(TokenType.BINARY, "1", line, pos)
            else -> when {
                    allDigits -> Token(TokenType.INTEGER, text, line, pos)
                    allLetters -> keywords[text]?.let {
                        Token(it, "", line, pos)
                    } ?: Token(TokenType.IDENTIFIER, text, line, pos)
                    text[0].isDigit() && !allDigits -> error(String.format("identifierOrLiteral: invalid number: %s", text), line, pos)
                    else -> error(String.format("identifierOrLiteral: invalid identifier: %s", text), line, pos)
                }
            }
    }

    fun scan(): List<LexerResult> {
        do {
            val t = nextToken()
            tokens.add(t)
        } while (t is Error || (t as Token).type != TokenType.END_OF_INPUT)
        return tokens
    }
}