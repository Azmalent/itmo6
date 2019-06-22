package common

enum class TokenType(val operatorType: OperatorType, val precedence: Int, val nodeType: NodeType) {
    END_OF_INPUT    (OperatorType.NOT_OPERATOR, -1, NodeType.NONE),
    KEYWORD_BEGIN   (OperatorType.NOT_OPERATOR, -1, NodeType.NONE),
    KEYWORD_END     (OperatorType.NOT_OPERATOR, -1, NodeType.NONE),
    KEYWORD_INT     (OperatorType.NOT_OPERATOR, -1, NodeType.NONE),
    KEYWORD_BIN     (OperatorType.NOT_OPERATOR, -1, NodeType.NONE),
    OP_ASSIGN       (OperatorType.BINARY,       -1, NodeType.OP_ASSIGN),
    OP_OR           (OperatorType.BINARY,       1,  NodeType.OP_OR),
    OP_XOR          (OperatorType.BINARY,       2,  NodeType.OP_XOR),
    OP_AND          (OperatorType.BINARY,       3,  NodeType.OP_AND),
    OP_SUB          (OperatorType.BINARY,       4,  NodeType.OP_SUB),
    OP_ADD          (OperatorType.BINARY,       4,  NodeType.OP_ADD),
    OP_MUL          (OperatorType.BINARY,       5,  NodeType.OP_MUL),
    OP_DIV          (OperatorType.BINARY,       5,  NodeType.OP_DIV),
    OP_NEGATE       (OperatorType.UNARY,        6,  NodeType.OP_NEGATE),
    IDENTIFIER      (OperatorType.NOT_OPERATOR, -1, NodeType.IDENTIFIER),
    INTEGER         (OperatorType.NOT_OPERATOR, -1, NodeType.INTEGER),
    BINARY          (OperatorType.NOT_OPERATOR, -1, NodeType.BINARY),
    L_BRACKET       (OperatorType.NOT_OPERATOR, -1, NodeType.NONE),
    R_BRACKET       (OperatorType.NOT_OPERATOR, -1, NodeType.NONE),
    COMMA           (OperatorType.NOT_OPERATOR, -1, NodeType.NONE),
    SEMICOLON       (OperatorType.NOT_OPERATOR, -1, NodeType.NONE);

    val isValue: Boolean
        get() = this == INTEGER || this == IDENTIFIER

    val isOperator: Boolean
        get() = operatorType != OperatorType.NOT_OPERATOR

    val isUnaryOp: Boolean
        get() = operatorType == OperatorType.UNARY

    val isBinaryOp: Boolean
        get() = operatorType == OperatorType.BINARY
}