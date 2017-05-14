class Queue<T : Comparable<T>,P>() {
    class Item<T : Comparable<T>, P>(val data: Node<T,P>, var next: Item<T, P>? = null)

    private var head : Item<T,P>? = null
    private var tail : Item<T,P>? = null

    fun push(item : Node<T,P>) {
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
            val result = head!!
            head = head?.next
            if (head == null)
                tail = null
            return result
        }
    }
    fun empty(): Boolean{
        return head == null
    }

}