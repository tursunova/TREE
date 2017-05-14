import BST.BST
import bTree.BTree
import RBTree.RedBlackTree
import tests.RBTreeTest

fun main(args: Array<String>){
    /*val tree = BST<Int, Int>()
    val btree = BTree<Int, Int>()
    val rbtree = RedBlackTree<Int,Int>()
    /*println("Ura!!!");
    btree.insert(5,5)
    btree.insert(0,0)
    btree.insert(13,13)
    btree.insert(11,11)
    btree.insert(19,19)
    btree.insert(9,9)
    btree.insert(10,10)
    btree.insert(3,3)
    btree.printTree();*/

    println("Ura!!!");
    rbtree.insert(5,5)
    rbtree.insert(0,0)
    rbtree.insert(13,13)
    rbtree.insert(11,11)
    rbtree.insert(19,19)
    rbtree.insert(9,9)
    rbtree.insert(10,10)
    rbtree.insert(3,3)
    println(rbtree.check())
    rbtree.printTree();
    println("Ura!!!");
    rbtree.erase(10)
    rbtree.erase(5)
    rbtree.erase(0)
    println(rbtree.check())
    //rbtree.insert(1,1)
    //rbtree.printTree();
    rbtree.erase(3)
    rbtree.erase(19)
    println(rbtree.check())
    rbtree.printTree();
    println(rbtree.check())

    /*println("Ura!!!");
    tree.insert(5,5)
    tree.insert(0,0)
    tree.insert(13,13)
    tree.insert(11,11)
    tree.insert(19,19)
    tree.insert(9,9)
    tree.insert(10,10)
    tree.insert(3,3)
    tree.printTree();
    println("Ura!!!");
    tree.erase(10)
    tree.erase(5)
    tree.erase(0)
    tree.erase(3)
    tree.printTree();
*/
* */
}
