package RBTree

import Node
import Queue

class RedBlackTreeIterator<T : Comparable<T>, P>(node: Node<T, P>?) : Iterator<Node<T, P>> {

    var q = Queue<T,P>()

    init {
        q.push(node!!)
    }

    override fun hasNext(): Boolean = !q.empty()

    override fun next(): Node<T, P> {
        val v = q.pop()
        if (v.data.leftChild != null) q.push(v.data.leftChild!!)
        if (v.data.rightChild != null) q.push(v.data.rightChild!!)
        return v.data
    }

}