package BST

import treeInterface
import Node
import Queue

open class BST <T:Comparable<T>,P>(var root: Node<T,P>?=null) :treeInterface<T,P> {

    fun iterator(): Iterator<Node<T, P>> = BSTIterator(root)

    override fun insert(key: T,value: P) {

        if (search(key) != null) return

        if (root == null) {
            root = Node(key=key, value=value)
            return
        }
        var currNode: Node<T,P>? = root
        while (currNode!=null) {
            if (key < currNode.key) {
                if (currNode.leftChild != null) {
                    currNode = currNode.leftChild!!
                }
                else {
                    val newNode: Node<T, P> = Node(key = key, value = value)
                    newNode.parent = currNode
                    currNode.leftChild = newNode
                    return
                }
            } else if (key > currNode.key) {
                if (currNode.rightChild != null) {
                    currNode = currNode.rightChild!!
                }
                else {
                    val newNode: Node<T, P> = Node(key = key, value = value)
                    newNode.parent = currNode
                    currNode.rightChild = newNode
                    return
                }
            } else return
        }
    }

    override fun search(key: T ): P? {
        var currNode: Node<T,P>? = root
        while (currNode!=null) {
            //println(currNode.key);
            if (key < currNode.key) {
                //println(currNode.leftChild);
                currNode = currNode.leftChild!!
            } else if (key > currNode.key) {
                //println(currNode.rightChild);
                currNode = currNode.rightChild!!
            } else {
                return currNode.value
            }
        }
        return null
    }

    override fun erase(key: T) {
        var currNode = root

        while (currNode != null && currNode.key != key)
            currNode = if (currNode.key > key) currNode.leftChild else currNode.rightChild

        if (currNode == null) return

        var deletedNode: Node<T, P>? = currNode
        if ((deletedNode!!.leftChild != null) && (deletedNode.rightChild != null))
        {
            deletedNode = currNode.rightChild
            while (deletedNode!!.leftChild != null)
                deletedNode = deletedNode.leftChild
        }

        val node = deletedNode.leftChild
        node?.parent = deletedNode.parent

        if (deletedNode.parent != null)
            if (deletedNode.parent!!.leftChild == deletedNode)
                deletedNode.parent!!.leftChild = node
            else
                deletedNode.parent!!.rightChild = node
        else
            root = node

        if (deletedNode != currNode) {
            currNode.key = deletedNode.key
            currNode.value = deletedNode.value
        }
    }
    override fun printTree() {
        val q = Queue<T,P>()
        if (root != null) q.push(root!!)
        while(!q.empty()){
            val v = q.pop()
            println(v.data.value)
            if (v.data.leftChild != null) q.push(v.data.leftChild!!)
            if (v.data.rightChild != null) q.push(v.data.rightChild!!)
        }

    }
}