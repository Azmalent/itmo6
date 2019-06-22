package parser

import common.NodeType

class AbstractSyntaxTree (val root: Node) {
    fun prettyPrint(printNulls: Boolean = true) {
        printNode(root, printNulls, 0)
    }

    private fun printNode(node: Node?, printNulls: Boolean, indent: Int) {
        for (i in 0 until indent) print("\t|")
        node?.let {
            System.out.printf("%-14s", it.type)
            if (it.type == NodeType.IDENTIFIER || it.type == NodeType.INTEGER) {
                println(" " + it.value)
            } else {
                println()
                printNode(it.left, printNulls, indent + 1)
                printNode(it.right, printNulls, indent + 1)
            }
        } ?: if (printNulls) println("[null]")
    }
}