package bTree

import java.util.*

class BNode<T: Comparable<T>, P> {
    var keys = ArrayList<T>()
    var values = ArrayList<P>()
    var children = ArrayList<BNode<T, P>> ()
    fun isLeaf () = children.size == 0
}