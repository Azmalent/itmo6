class Lexer (private val programText: String) {
    companion object {
        const val EOF = '\u0000'

        private val keywords = mapOf(
            "Begin" to TokenType.KEYWORD_BEGIN,
            "End"   to TokenType.KEYWORD_END,
            "Int"   to TokenType.KEYWORD_INT,
            "Bin"   to TokenType.KEYWORD_BIN
        )

        private val singleCharLexems = mapOf(
            '&' to TokenType.OP_AND,
            '|' to TokenType.OP_OR,
            '^' to TokenType.OP_XOR,
            '(' to TokenType.L_BRACKET,
            ')' to TokenType.R_BRACKET,
            '+' to TokenType.OP_ADD,
            '-' to TokenType.OP_SUB,
            '*' to TokenType.OP_MUL,
            ',' to TokenType.COMMA,
            ';' to TokenType.SEMICOLON
        )
    }

    private var lineNum = 1
    private var linePos = 0
    private var textPos = 0
    private var char = programText[0]

    private val token: Token
        get() {
            val line = lineNum
            val pos = linePos
            while (char.isWhitespace()) nextChar

            return when (char) {
                EOF -> Token(TokenType.END_OF_INPUT, "", this.lineNum, this.linePos)
                '/' -> divOrComment(line, pos)
                ':' -> follow('=', TokenType.OP_ASSIGN, TokenType.END_OF_INPUT, line, pos)
                else -> singleCharLexems[char]?.let {
                    nextChar
                    Token(it, "", line, pos)
                } ?: identifierOrLiteral(line, pos)
            }
        }

    private val nextChar: Char
        get() {
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

    private fun error(line: Int, pos: Int, msg: String) {
        if (line > 0 && pos > 0) {
            System.out.printf("%s in lineNum %d, linePos %d\n", msg, line, pos)
        } else {
            println(msg)
        }
        nextChar
    }

    private fun follow(expectedChar: Char, ifYes: TokenType, ifNo: TokenType, line: Int, pos: Int): Token {
        if (nextChar == expectedChar) {
            nextChar
            return Token(ifYes, "", line, pos)
        }
        if (ifNo == TokenType.END_OF_INPUT) {
            error(line, pos, String.format("follow: unrecognized character: (%d) '%c'", char.toInt(), char))
        }
        return Token(ifNo, "", line, pos)
    }

    private fun divOrComment(line: Int, pos: Int): Token {
        if (nextChar != '/') {
            return Token(TokenType.OP_DIV, "", line, pos)
        }
        this.lineNum++
        return token
    }

    private fun identifierOrLiteral(line: Int, pos: Int): Token {
        var isNumber = true
        var text = ""

        while (char.isLetterOrDigit()) {
            text += char
            isNumber = isNumber && char.isDigit()
            nextChar
        }

        when (text) {
            "" -> error(line, pos, String.format("identifierOrLiteral: unrecognized character: (%d) %c", char.toInt(), char))
            "0" -> return Token(TokenType.B_ZERO, "0", line, pos)
            "1" -> return Token(TokenType.B_ONE, "1", line, pos)
            else -> {
                if (text[0].isDigit()) {
                    if (isNumber) return Token(TokenType.INTEGER, text, line, pos)
                    error(line, pos, String.format("identifierOrLiteral: invalid number: %s", text))
                }
            }
        }

        return keywords[text]?.let {
                Token(it, "", line, pos)
        } ?: Token(TokenType.IDENTIFIER, text, line, pos)
    }

    internal fun printTokens() {
        do {
            val t = token
            println(t)
        } while (t.type != TokenType.END_OF_INPUT)
    }
}