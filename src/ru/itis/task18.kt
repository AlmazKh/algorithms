package ru.itis

import java.util.*
import kotlin.collections.HashMap

internal open class Node(val sum: Int) : Comparable<Node?> {
    var code: String? = null

    open fun buildCode(code: String?) {
        this.code = code
    }

    override fun compareTo(other: Node?): Int {
        return Integer.compare(sum, other!!.sum)
    }
}

internal class InternalNode(var left: Node, var right: Node?) : Node(left.sum + right!!.sum) {
    override fun buildCode(code: String?) {
        this.code = code
        super.buildCode(code)
        left.buildCode(code + "0")
        right?.buildCode(code + "1")
    }
}

internal class LeafNode(var symbol: Char, count: Int) : Node(count) {
    override fun buildCode(code: String?) {
        super.buildCode(code)
        println("$symbol: $code")
    }
}

fun main() {
    println("Введите текст:")
    val input = Scanner(System.`in`)
    val s = input.nextLine()
    val count: MutableMap<Char, Int> = HashMap()
    var n = 0
    for (element in s) {
        if (count.containsKey(element)) {
            count[element] = count[element]!! + 1
        } else {
            count[element] = 1
        }
        n++
    }
    println(n)

    val charNodes: MutableMap<Char, Node> = HashMap()
    val priorityQueue = PriorityQueue<Node>()
    for ((key, value) in count) {
        val node = LeafNode(key, value)
        charNodes[key] = node
        priorityQueue.add(node)
    }
    while (priorityQueue.size > 1) {
        val first = priorityQueue.poll()
        val second = priorityQueue.poll()!!
        val node = InternalNode(first, second)
        priorityQueue.add(node)
    }
    println("Битовые коды символов:")
    val root = priorityQueue.poll()
    if (count.size == 1) {
        assert(root != null)
        root.code = "0"
    } else {
        assert(root != null)
        root.buildCode("")
    }
    val encodedString = StringBuilder()
    for (i in 0 until s.length) {
        val c = s[i]
        encodedString.append(charNodes[c]!!.code)
    }
    var compression = 0f
    compression = n * 8 / encodedString.length.toFloat()
    println("Результат:")
    println(encodedString)
    println("Сжали в $compression раз")
}