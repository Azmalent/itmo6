package common

enum class NodeType(val nodeName: String) {
    NONE(""),
    PROGRAM("Program"),
    INT_DEF("Definitions"),
    BIN_DEF("Statements"),
    IDENTIFIER("Identifier"),
    INTEGER("Integer"),
    BINARY("Binary"),
    SEQUENCE("Sequence"),
    OP_ASSIGN("Assign"),
    OP_NEGATE("Negate"),
    OP_ADD("Add"),
    OP_SUB("Subtract"),
    OP_MUL("Multiply"),
    OP_DIV("Divide"),
    OP_OR("Or"),
    OP_XOR("Xor"),
    OP_AND("And");

    override fun toString(): String {
        return nodeName
    }
}