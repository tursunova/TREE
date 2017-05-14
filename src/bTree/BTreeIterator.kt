package bTree

class BTreeIterator<T : Comparable<T>, P>(node: BNode<T, P>?) : Iterator<BNode<T, P>> {

    var q = BQueue<T,P>()

    init {
        q.push(node!!)
    }

    override fun hasNext(): Boolean = !q.empty()

    override fun next(): BNode<T, P> {
        val v = q.pop()
        if (!v.data.isLeaf()){

            for(child in v.data.children){
                q.push(child)
            }

        }
        return v.data
    }

}