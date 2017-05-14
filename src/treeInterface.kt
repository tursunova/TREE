
interface treeInterface<T : Comparable<T>, P> {
    fun insert(key: T, value: P)
    fun search(key: T): P?
    fun erase(key: T)
    fun printTree()
}
