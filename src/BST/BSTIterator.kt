package BST

import Node
import Queue

open class BSTIterator <T : Comparable<T>, P>(var node: Node<T, P>?) : Iterator<Node<T, P>> {

    var q = Queue<T,P>()

    init {
        q.push(node!!)
    }

    override fun hasNext(): Boolean = !q.empty()

    override fun next(): Node<T, P> {
        var v = q.pop();
        if (v.data.leftChild != null) q.push(v.data.leftChild!!)
        if (v.data.rightChild != null) q.push(v.data.rightChild!!)
        return v.data
    }

}