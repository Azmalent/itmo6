package parser

import common.NodeType

class Node private constructor(val type: NodeType, val left: Node?, val right: Node?, val value: String?) {
    companion object {
        fun makeNode(type: NodeType, left: Node?, right: Node? = null): Node {
            return Node(type, left, right, "")
        }
        fun makeLeaf(type: NodeType, value: String): Node {
            return Node(type, null, null, value)
        }
    }
}