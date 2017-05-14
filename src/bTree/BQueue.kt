package bTree


class BQueue<T : Comparable<T>,P>() {
    class Item<T : Comparable<T>, P>(val data: BNode<T,P>, var next: Item<T, P>? = null)

    private var head : Item<T,P>? = null
    private var tail : Item<T,P>? = null

    fun push(item : BNode<T,P>) {
        val i = Item(data = item)
        if (tail == null) {
            head = i
            tail = head
        } else {
            tail?.next = i
            tail = i
        }
    }

    fun pop(): Item<T, P> {
        if (head == null) {
            throw UnsupportedOperationException("Queue is empty!")
        } else {
            var result = head!!
            head = head?.next
                tail = null
            return result
        }
    }
    fun empty(): Boolean{
       // if (head == null) return true else return false
        return (head == null)
    }

}