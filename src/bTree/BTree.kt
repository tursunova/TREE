package bTree

import treeInterface

open class BTree<T : Comparable<T>, P>(private var root: BNode<T, P>? = null, val t: Int = 3) : treeInterface<T, P>, Iterable<BNode<T,P>> {

    override fun iterator(): Iterator<BNode<T, P>> = BTreeIterator(root)

    override fun search(key: T): P?{
        if (searchNode(key)==null) return null
        val currNode: BNode<T,P> = searchNode(key)!!
        var i: Int = 0
        while (i < currNode.keys.size && key > currNode.keys[i]) {
            i++
        }
        return currNode.values[i]
    }

    private fun searchNode(key: T?, node: BNode<T, P>? = root): BNode<T, P>? {
        if (key == null || node == null) return null
        if (node.keys.contains(key)) return node
        if (node.isLeaf()) return null
        var i = 0
        while ((i < node.keys.size) && (key > node.keys[i])) {
            i++
        }
        return searchNode(key, node.children[i])

    }


    private fun split(node: BNode<T,P>, i: Int){

        val parent = node
        val newChild = BNode<T, P>()
        val child = parent.children[i]
        for (j in 0..t - 2)
            newChild.keys.add(element = child.keys.removeAt(t))
        if (!child.isLeaf())
            for (j in 0..t - 1) newChild.children.add(element = child.children.removeAt(t))
        parent.children.add(i + 1, newChild)
        parent.keys.add(index=i, element = child.keys.removeAt(t-1))

    }

    private fun insertNonfull(node: BNode<T,P>, key: T, value: P){
        var i: Int = node.keys.size - 1

        if (node.isLeaf()){
            while(i >= 0 && key < node.keys[i]){
                i--
            }
            node.keys.add(i + 1, key)
            node.values.add(i + 1, value)
        } else {
            while (i >= 0 && key < node.keys[i]) {
                i--
            }
            i++
            if (node.children[i].keys.size == 2*t - 1){
                split(node, i)
                if (key > node.keys[i]) {
                    i++
                }
            }
            insertNonfull(node.children[i], key, value)
        }

    }


    override fun insert(key: T, value: P) {

        if (root == null) {
            this.root = BNode()
            root!!.keys.add(key)
            root!!.values.add(value)
            return
        }
        if (searchNode(key) != null) return
        if (root!!.keys.size == 2 * t - 1) {
            val newRoot: BNode<T, P> = BNode()
            val currRoot: BNode<T, P> = root!!
            this.root = newRoot
            newRoot.children.add(currRoot)
            split(newRoot, 0)
            insertNonfull(newRoot, key, value)
        } else {
            insertNonfull(root!!, key, value)
        }

    }

    override fun erase(key: T) {
        if (search(key) == null) return
        var flag = delete(key = key, node = root)
        if (!root!!.isLeaf() && root!!.keys.size == 0) root = root!!.children[0]
        return
    }

    private fun delete(key: T, node: BNode<T, P>? = root): Boolean {
        if (node == null) return false
        val size = node.keys.size
        var i = 0
        while (i < size && key > node.keys[i]) i++

        when {
            i < node.keys.size && key == node.keys[i] -> {
                if (node.isLeaf()) {
                    node.keys.removeAt(i)
                    node.values.removeAt(i)
                    return true
                }
                val deletion = Pair(node.keys[i], node.values[i])

                if (node.children[i].keys.size > t - 1) {
                    var tmp = node.children[i]
                    while (!tmp.isLeaf()) tmp = tmp.children[tmp.children.lastIndex]
                    node.keys[i] = tmp.keys[tmp.keys.lastIndex]
                    node.values[i] = tmp.values[tmp.values.lastIndex]
                    tmp.keys[tmp.keys.lastIndex] = deletion.first
                    tmp.values[tmp.values.lastIndex] = deletion.second
                    return delete(key = key, node = node.children[i])
                } else if (node.children[i + 1].keys.size > t - 1) {
                    var tmp = node.children[i + 1]
                    while (!tmp.isLeaf()) tmp = tmp.children[0]
                    node.keys[i] = tmp.keys[0]
                    node.values[i] = tmp.values[0]
                    tmp.keys[0] = deletion.first
                    tmp.values[0] = deletion.second
                    return delete(key = key, node = node.children[i + 1])
                } else return delete(node = mergeNodes(left = node.children[i], right = node.children[i + 1], parent = node), key = key)
            }
            else -> {
                if (node.children[i].keys.size == t - 1) {
                    val flag = PullKeyFromNeighbor(child = node.children[i], parent = node)
                    if (flag == 0) {
                        if (i == 0) return delete(key = key, node = mergeNodes(left = node.children[i], right = node.children[i + 1], parent = node))
                        else return delete(key = key, node = mergeNodes(left = node.children[i - 1], right = node.children[i], parent = node))
                    }
                    else delete(key = key, node = node.children[i])
                } else return delete(node = node.children[i], key = key)
            }
        }
        return true
    }
    private fun PullKeyFromNeighbor(child: BNode<T, P>, parent: BNode<T, P>): Int
    {
        val indexOfChild = parent.children.indexOf(child)
        val size = parent.children.size

        if (indexOfChild > 0 && parent.children[indexOfChild - 1].keys.size > t - 1) {
            val neighbour = parent.children[indexOfChild - 1]
            child.keys.add(0, parent.keys[indexOfChild - 1])
            child.values.add(0, parent.values[indexOfChild - 1])
            parent.keys[indexOfChild - 1] = neighbour.keys[neighbour.keys.lastIndex]
            parent.values[indexOfChild - 1] = neighbour.values[neighbour.values.lastIndex]
            neighbour.keys.removeAt(neighbour.keys.lastIndex)
            neighbour.values.removeAt(neighbour.values.lastIndex)
            if (!child.isLeaf()) {
                child.children.add(0, neighbour.children[neighbour.children.lastIndex])
                neighbour.children.removeAt(neighbour.children.lastIndex)
            }
            return -1
        } else if (indexOfChild < size - 1 && parent.children[indexOfChild + 1].keys.size > t - 1) {
            val neighbour = parent.children[indexOfChild + 1]
            child.keys.add(parent.keys[indexOfChild])
            parent.keys[indexOfChild] = neighbour.keys[0]
            neighbour.keys.removeAt(0)

            child.values.add(parent.values[indexOfChild])
            parent.values[indexOfChild] = neighbour.values[0]
            neighbour.values.removeAt(0)
            if (!child.isLeaf()) {
                child.children.add(child.children.lastIndex + 1, neighbour.children[0])
                neighbour.children.removeAt(0)
            }
            return 1
        }
        return 0
    }

    private fun mergeNodes(left: BNode<T, P>, right: BNode<T, P>, parent: BNode<T, P>): BNode<T, P> {
        left.keys.add(element = parent.keys[parent.children.indexOf(right) - 1])
        parent.keys.removeAt(parent.children.indexOf(right) - 1)
        left.keys.addAll(right.keys)
        left.values.addAll(right.values)
        left.children.addAll(right.children)
        parent.children.remove(right)
        return left
    }

    override fun printTree() {

        val q = BQueue<T,P>()

        if (root != null) q.push(root!!)

        while(!q.empty()){

            val v = q.pop()

            for(node in v.data.values) {
                println(node)
            }

            if (!v.data.isLeaf()){

                for(child in v.data.children){
                    q.push(child)
                }

            }

        }

    }

}
