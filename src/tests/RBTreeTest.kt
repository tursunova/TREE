package tests

import org.junit.jupiter.api.Assertions.*
import RBTree.RedBlackTree
import RBTree.Color
import Node

internal class RBTreeTest{

    fun createTree():RedBlackTree<Int,Int>? {

        val tree = RedBlackTree<Int,Int>()

        tree.root = Node(10, 10, Color.black)
        tree.root!!.leftChild = Node(6, 6, Color.black)
        tree.root!!.leftChild!!.parent = tree.root
        tree.root!!.rightChild = Node(28, 28, Color.black)
        tree.root!!.rightChild!!.parent = tree.root

        tree.root!!.leftChild!!.leftChild = Node(1, 1, Color.black)
        tree.root!!.leftChild!!.leftChild!!.parent = tree.root!!.leftChild
        tree.root!!.leftChild!!.rightChild = Node(9, 9, Color.black)
        tree.root!!.leftChild!!.rightChild!!.parent = tree.root!!.leftChild

        tree.root!!.rightChild!!.leftChild = Node(21, 21, Color.red)
        tree.root!!.rightChild!!.leftChild!!.parent = tree.root!!.rightChild
        tree.root!!.rightChild!!.rightChild = Node(33, 33, Color.black)
        tree.root!!.rightChild!!.rightChild!!.parent = tree.root!!.rightChild

        tree.root!!.leftChild!!.leftChild!!.leftChild = Node(0, 0, Color.red)
        tree.root!!.leftChild!!.leftChild!!.leftChild!!.parent = tree.root!!.leftChild!!.leftChild
        tree.root!!.leftChild!!.leftChild!!.rightChild = Node(3, 3, Color.red)
        tree.root!!.leftChild!!.leftChild!!.rightChild!!.parent = tree.root!!.leftChild!!.leftChild


        tree.root!!.leftChild!!.rightChild!!.leftChild = Node(8, 8, Color.red)
        tree.root!!.leftChild!!.rightChild!!.leftChild!!.parent = tree.root!!.leftChild!!.rightChild

        tree.root!!.rightChild!!.leftChild!!.leftChild = Node(17, 17, Color.black)
        tree.root!!.rightChild!!.leftChild!!.leftChild!!.parent = tree.root!!.rightChild!!.leftChild
        tree.root!!.rightChild!!.leftChild!!.rightChild = Node(25, 25, Color.black)
        tree.root!!.rightChild!!.leftChild!!.rightChild!!.parent = tree.root!!.rightChild!!.leftChild

        tree.root!!.rightChild!!.rightChild!!.rightChild = Node(36, 36, Color.red)
        tree.root!!.rightChild!!.rightChild!!.rightChild!!.parent = tree.root!!.rightChild!!.rightChild

        tree.root!!.rightChild!!.leftChild!!.leftChild!!.rightChild = Node(18, 18, Color.red)
        tree.root!!.rightChild!!.leftChild!!.leftChild!!.rightChild!!.parent = tree.root!!.rightChild!!.leftChild!!.leftChild

        if (tree.check())
            return tree
        else
            return null
    }

    fun searchInEmptyTree(){
        val tree = RedBlackTree<Int,Int>()
        assertEquals(null,tree.search(3))

    }
    fun deleteInEmptyTree() {
        val tree = RedBlackTree<Int, Int>()
        tree.erase(3)

    }
    fun searchNotExistKey(){
        val tree = createTree()
        assertEquals(null,tree?.search(27))
    }
    fun deleteNotExistKey(){
        val tree = createTree()
        val newTree = createTree()
        tree?.erase(27)
        assertEquals(tree,newTree)
    }
    fun searchExistKey(){
        val tree = createTree()
        assertEquals(3,tree?.search(3))
    }
    fun deleteRoot(){
        val tree = createTree()
        tree?.erase(10)
        assertEquals(true,tree?.check())
    }
    fun insertExistKey(){
        val tree = createTree()
        val newTree = createTree()
        tree?.insert(3,3)
        assertEquals(tree,newTree)
    }
    fun insertInEmptyTree(){
        val tree = RedBlackTree<Int,Int> ()
        tree.insert(3,3)
        assertEquals(true,tree.check())
    }
    fun insertRedNode(){
        val tree = createTree()
        tree?.insert(27,27)
        assertEquals(true,tree?.check())
    }
    fun insertNode(){
        val tree = createTree()
        tree?.insert(19,19)
        assertEquals(true,tree?.check())
    }
    fun deleteNodewithBlackBrother(){
        val tree = createTree()
        tree?.erase(21)
        assertEquals(true,tree?.check())
    }
    fun deleteNodeWithoutChildren(){
        val tree = createTree()
        tree?.erase(36)
    }
    fun deleteNodeWithOneChild(){
        val tree = createTree()
        tree?.erase(33)
    }
    fun stressTest() {
        val tree = RedBlackTree<Int, Int>()
        for (i in 1..10000000)
            tree.insert(i, i)
        for (i in 1..10000000)
            tree.erase(i)
        assertEquals(true, tree.check())
    }
}
