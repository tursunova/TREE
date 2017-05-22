package bTree

class BTreeIterator<T : Comparable<T>, P>(var node: BNode<T, P>?) : Iterator<BNode<T, P>> {

    var q = mutableListOf<BNode<T, P>?>()

    init {
        q.add(node)
    }

    override fun hasNext(): Boolean{
        if (node == null)
            return false
        return q.isNotEmpty()
    }


    override fun next(): BNode<T, P> {
        val v = q[0]
        if (!v!!.isLeaf()){

            for(child in v.children){
                q.add(child)
            }

        }
        return v
    }

}
