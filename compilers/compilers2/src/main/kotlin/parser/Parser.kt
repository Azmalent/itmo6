package parser

import common.NodeType
import common.TokenType
import lexer.Lexer
import lexer.LexerResult
import lexer.LexerResult.Token
import java.io.File

internal typealias Error = LexerResult.Error

internal class Parser(private val source: List<Token>) {
    companion object {
        fun error(msg: String, line: Int, pos: Int) {
            System.out.printf("%s in line %d, pos %d\n", msg, line, pos)
            System.exit(1)
        }

        fun fromFile(filename: String): Parser? {
            val text = File(filename).readText()
            val tokens = Lexer(text).scan()
            val errors = tokens.filterIsInstance<Error>()
            if (errors.isNotEmpty()) {
                println("Failed to create Parser: source contains lexical errors")
                println()
                errors.forEach(::println)
                return null
            }
            return Parser(tokens as List<Token>)
        }
    }

    private lateinit var token: Token
    private var tokenIndex = 0
    private fun getNextToken(): Token {
        token = source[tokenIndex]
        tokenIndex++
        return token
    }

    private fun parseExpression(p: Int): Node? {
        var result: Node? = null
        var node: Node?
        var op: TokenType

        when (token.type) {
            TokenType.L_BRACKET -> result = parseBrackets()
            TokenType.OP_NEGATE -> {
                getNextToken()
                node = parseExpression(TokenType.OP_NEGATE.precedence)
                result = Node.makeNode(NodeType.OP_NEGATE, node)
            }
            TokenType.IDENTIFIER, TokenType.INTEGER, TokenType.BINARY -> {
                val type = when (token.type) {
                    TokenType.IDENTIFIER -> NodeType.IDENTIFIER
                    TokenType.INTEGER -> NodeType.INTEGER
                    else -> NodeType.BINARY
                }
                result = Node.makeLeaf(type, token.value)
                getNextToken()
            }
            else -> error("Expecting a primary, found: " + token.type, token.line, token.pos)
        }

        while (token.type.isBinaryOp && token.type.precedence >= p) {
            op = token.type
            getNextToken()
            node = parseExpression(op.precedence)
            result = Node.makeNode(op.nodeType, result, node)
        }
        return result
    }

    private fun parseBrackets(): Node? {
        expect("parseBrackets", TokenType.L_BRACKET)
        val node = parseExpression(0)
        expect("parseBrackets", TokenType.R_BRACKET)
        return node
    }

    private fun expect(msg: String, expectedType: TokenType) {
        if (token.type != expectedType) {
            error(msg + ": Expecting '" + expectedType + "', found: '" + token.type + "'", token.line, token.pos)
        }
        getNextToken()
    }

    private fun parseVars(): Node? {
        var node: Node? = null
        while (token.type == TokenType.IDENTIFIER) {
            val ident = Node.makeLeaf(NodeType.IDENTIFIER, token.value)
            node = Node.makeNode(NodeType.SEQUENCE, node, ident)
            getNextToken()
            when (token.type) {
                TokenType.COMMA -> getNextToken()
                TokenType.SEMICOLON -> {
                    getNextToken()
                    return node
                }
                else -> error("Expecting comma or semicolon, found: " + token.type, token.line, token.pos)
            }
        }
        error("Expecting identifier, found: " + token.type, token.line, token.pos)
        return null
    }

    private fun parseDefinition(): Node? {
        var node: Node? = null
        while (true) {
            when (token.type) {
                TokenType.KEYWORD_BEGIN -> return node
                TokenType.KEYWORD_INT, TokenType.KEYWORD_BIN -> {
                    val type = if (token.type == TokenType.KEYWORD_INT) NodeType.INT_DEF else NodeType.BIN_DEF
                    getNextToken()
                    val def = Node.makeNode(type, parseVars())
                    node = Node.makeNode(NodeType.SEQUENCE, node, def)
                }
                else -> error("Expecting variable definition, found: " + token.type, token.line, token.pos)
            }
        }
    }

    private fun parseStatement(): Node? {
        var node: Node? = null
        if (token.type == TokenType.IDENTIFIER) {
            val ident = Node.makeLeaf(NodeType.IDENTIFIER, token.value)
            getNextToken()
            expect("Assign", TokenType.OP_ASSIGN)
            val expr = parseExpression(0)
            node = Node.makeNode(NodeType.OP_ASSIGN, ident, expr)
            expect("Semicolon", TokenType.SEMICOLON)
        }
        else error("Expecting start of statement, found: " + token.type, token.line, token.pos)
        return node
    }

    fun parse(): AbstractSyntaxTree {
        var defs: Node? = null
        var statements: Node? = null
        getNextToken()
        while (token.type != TokenType.KEYWORD_BEGIN) {
            if (token.type == TokenType.END_OF_INPUT) error("Missing end keyword", token.line, token.pos)
            defs = Node.makeNode(NodeType.SEQUENCE, defs, parseDefinition())
        }
        expect("Begin", TokenType.KEYWORD_BEGIN)
        while (token.type != TokenType.KEYWORD_END) {
            if (token.type == TokenType.END_OF_INPUT) error("Missing end keyword", token.line, token.pos)
            statements = Node.makeNode(NodeType.SEQUENCE, statements, parseStatement())
        }
        expect("End", TokenType.KEYWORD_END)
        if (token.type != TokenType.END_OF_INPUT) error("Excpecting end of input, found: " + token.type, token.line, token.pos)

        val root = Node.makeNode(NodeType.PROGRAM, defs, statements)
        return AbstractSyntaxTree(root)
    }
}