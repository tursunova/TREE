import RBTree.Color

class Node<T: Comparable<T>, P>(var key: T, var value: P, var color: Color = Color.black) {

    var parent: Node<T,P>? = null
    var leftChild: Node<T,P>? = null
    var rightChild: Node<T,P>? = null
    fun isRoot(): Boolean = (parent == null)
    fun isLeaf(): Boolean = ((leftChild == null) && (rightChild == null))

}

