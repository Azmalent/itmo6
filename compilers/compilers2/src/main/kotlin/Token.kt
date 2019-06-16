internal class Token(val type: TokenType, var value: String, var line: Int, var pos: Int) {
    override fun toString(): String {
        var result = String.format("%5d  %5d %-15s", line, pos, type)
        if (type == TokenType.INTEGER) result += String.format("  %4s", value)
        else if (type == TokenType.IDENTIFIER) result += String.format(" %s", value)
        return result
    }
}