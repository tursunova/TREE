package RBTree

import Node
import Queue
import treeInterface

open class RedBlackTree <T:Comparable<T>,P>(var root: Node<T,P>? = null) :treeInterface<T,P>  {

    fun iterator(): Iterator<Node<T, P>> = RedBlackTreeIterator(root)


    override fun insert(key: T, value: P) {

        if (searchNode(key) != null) return

        if (root == null) {
            root = Node(key=key, value=value, color = Color.black)
            return
        }
        var currNode: Node<T,P>? = root
        while (currNode!=null) {
            if (key < currNode.key) {
                if (currNode.leftChild != null) {
                    currNode = currNode.leftChild!!
                }
                else {
                    val newNode: Node<T, P> = Node(key = key, value = value, color = Color.red)
                    newNode.parent = currNode
                    currNode.leftChild = newNode
                    fixInsert(newNode)
                    return
                }
            } else if (key > currNode.key) {
                if (currNode.rightChild != null) {
                    currNode = currNode.rightChild!!
                }
                else {
                    val newNode: Node<T, P> = Node(key = key, value = value, color = Color.red)
                    newNode.parent = currNode
                    currNode.rightChild = newNode
                    fixInsert(newNode)
                    return
                }
            } else return
        }

    }

    private fun fixInsert(node: Node<T, P>) {
        var newNode = node
        var currNode: Node<T, P>?
        while (newNode.parent != null && newNode.parent!!.color == Color.red) {
            if (newNode.parent?.parent?.leftChild == newNode.parent) {
                currNode = newNode.parent?.parent?.rightChild
                if (currNode != null && currNode.color == Color.red) {
                    newNode.parent?.color = Color.black
                    currNode.color = Color.black
                    newNode.parent?.parent?.color = Color.red
                    newNode = newNode.parent!!.parent!!
                } else {
                    if (newNode.parent?.rightChild == newNode) {
                        newNode = newNode.parent!!
                        leftRotation(newNode)
                    }
                    newNode!!.parent!!.color = Color.black
                    newNode.parent!!.parent?.color = Color.red
                    rightRotation((newNode.parent!!.parent!!))

                }

            } else {
                currNode = newNode?.parent?.parent?.leftChild//null?
                if (currNode != null && currNode.color == Color.red) {
                    newNode.parent?.color = Color.black
                    currNode.color = Color.black
                    newNode.parent?.parent?.color = Color.red
                    newNode = newNode.parent!!.parent!!
                } else {
                    if (newNode.parent?.leftChild == newNode) {
                        newNode = newNode.parent!!
                        rightRotation(newNode)
                    }
                    newNode.parent?.color = Color.black
                    newNode.parent?.parent?.color = Color.red
                    leftRotation(newNode.parent!!.parent!!)

                }
            }
        }
        root!!.color = Color.black
    }
    private fun leftRotation(node: Node<T,P>){
        val newNode = node.rightChild
        if (node.parent == null){
            this.root = newNode
        }
        node.rightChild = newNode!!.leftChild
        newNode.leftChild?.parent = node
        newNode.leftChild = node
        newNode.parent = node.parent
        node.parent = newNode
        if (node == newNode.parent?.leftChild) {
            newNode.parent?.leftChild = newNode
        } else
            newNode.parent?.rightChild = newNode
    }
    private fun rightRotation(node: Node<T, P>) {
        val newNode = node.leftChild
        if (node.parent == null){
            this.root = newNode
        }
        node.leftChild = newNode!!.rightChild
        newNode.rightChild?.parent = node
        newNode.rightChild = node
        newNode.parent = node.parent
        node.parent = newNode
        if (node == newNode.parent?.leftChild) {
            newNode.parent?.leftChild = newNode
        } else
            newNode.parent?.rightChild = newNode
    }

    override fun search(key: T ): P? {
        var currNode: Node<T,P>? = root
        while (currNode!=null) {
            if (key < currNode.key) {
                currNode = currNode.leftChild!!
            } else if (key > currNode.key) {
                currNode = currNode.rightChild!!
            } else {
                return currNode.value
            }
        }
        return null
    }

    fun searchNode(key: T ): Node<T,P>? {
        var currNode: Node<T,P>? = root
        while (currNode!=null) {
            if (key < currNode.key) {
                currNode = currNode.leftChild!!
            } else if (key > currNode.key) {
                currNode = currNode.rightChild!!
            } else {
                return currNode
            }
        }
        return null
    }

    override fun erase(key: T){
        var currNode = root
        while (currNode != null && currNode.key != key) {
            currNode = if (currNode.key > key) currNode.leftChild else currNode.rightChild
        }
        if (currNode == null) {
            return
        }
        var deletedNode: Node<T, P>? =  currNode

        if ((deletedNode!!.leftChild != null) && (deletedNode.rightChild != null)) {
            deletedNode = currNode.rightChild
            while (deletedNode!!.leftChild != null) {
                deletedNode = deletedNode.leftChild
            }
        }
        val node = deletedNode.leftChild ?: deletedNode.rightChild
        node?.parent = deletedNode.parent
        if (deletedNode.parent != null) {
            if (deletedNode.parent!!.leftChild == deletedNode) {
                deletedNode.parent!!.leftChild = node
            } else {
                deletedNode.parent!!.rightChild = node
            }
        } else {
            root = node
        }
        if (deletedNode != currNode) {
            currNode.key = deletedNode.key
            currNode.value = deletedNode.value
        }
        if (deletedNode.color == Color.black) {
            if (node != null) {
                fixDelete(node)
            } else if (root != null) {
                fixDelete(deletedNode, true)
            }
        }
        return
    }

    private fun fixDelete(node: Node<T, P>?, isNull: Boolean = false) {

        var x = isNull
        var currNode = node
        while (x || currNode != root && currNode!!.color == Color.black) {
            if (currNode!!.parent!!.leftChild == currNode || currNode.parent!!.leftChild == null) {
                var brotherNode = currNode.parent!!.rightChild
                if (brotherNode!!.color == Color.red) {

                    brotherNode.color = Color.black
                    currNode.parent!!.color = Color.red
                    leftRotation(currNode.parent!!)
                    brotherNode = currNode.parent!!.rightChild
                }
                if (!(brotherNode!!.leftChild != null &&
                        brotherNode.rightChild != null &&
                        brotherNode.rightChild!!.color == Color.red &&
                        brotherNode.leftChild!!.color == Color.red)) {
                    brotherNode.color = Color.red
                    currNode = currNode.parent
                } else {
                    if (brotherNode.leftChild != null && brotherNode.leftChild!!.color == Color.red) {
                        brotherNode.leftChild!!.color = Color.black
                        brotherNode.color = Color.red
                        rightRotation(brotherNode)
                    }
                    brotherNode.color = currNode.parent!!.color
                    currNode.parent!!.color = Color.black
                    brotherNode.rightChild?.color = Color.black
                    leftRotation(currNode.parent!!)
                    currNode = root
                }
            } else {
                var brotherNode = currNode.parent!!.leftChild
                if (brotherNode!!.color == Color.red) {
                    brotherNode.color = Color.black
                    currNode.parent!!.color = Color.red
                    rightRotation(currNode.parent!!)
                    brotherNode = currNode.parent!!.leftChild
                }
                if (!(brotherNode!!.leftChild != null &&
                        brotherNode.rightChild != null &&
                        brotherNode.rightChild!!.color == Color.red &&
                        brotherNode.leftChild!!.color == Color.red)) {
                    brotherNode.color = Color.red
                    currNode = currNode.parent
                } else {
                    if (brotherNode.rightChild != null && brotherNode.rightChild!!.color == Color.red) {
                        brotherNode.rightChild!!.color = Color.black
                        brotherNode.color = Color.red
                        leftRotation(brotherNode)
                    }
                    brotherNode.color = currNode.parent!!.color
                    currNode.parent!!.color = Color.black
                    brotherNode.leftChild?.color = Color.black
                    rightRotation(currNode.parent!!)
                    currNode = root
                }
            }
            x = false
        }
        currNode?.color = Color.black
    }



    override fun printTree() {
        val q = Queue<T,P>()
        if (root != null) q.push(root!!)
        while(!q.empty()){
            val v = q.pop()
            println("value: " + v.data.value + " color: " + v.data.color)
            if (v.data.leftChild != null) q.push(v.data.leftChild!!)
            if (v.data.rightChild != null) q.push(v.data.rightChild!!)
        }

    }


    internal fun check(): Boolean {
        if (root == null) return true
        if (root!!.color == Color.red) return false
        if (root!!.leftChild== null && root!!.rightChild == null) return true
        return !(numberOfBlackNodes(root!!) == -1)
    }

    private fun numberOfBlackNodes(node: Node<T, P>): Int
    {
        var left = 0
        var right = 0
        if (node.leftChild != null) left = numberOfBlackNodes(node.leftChild!!)
        if (node.rightChild != null) right = numberOfBlackNodes(node.rightChild!!)
        if (left != right || left == -1 || right == -1) return -1
        return left
    }
}