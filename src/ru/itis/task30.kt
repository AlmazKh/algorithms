package ru.itis

import java.nio.file.Files
import java.nio.file.Path
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.set

object LZ78 {
    @JvmStatic
    fun main(args: Array<String>) {
        val inputLines = Files.readAllLines(Path.of("30_input.txt"))
        var input = ""
        for (line in inputLines) {
            input = "$input$line "
        }
        val result: List<Node> = getLZ78(input)
        for (node in result) {
            print(java.lang.String.valueOf(node.position) + node.next)
        }
    }

    private fun getLZ78(input: String): List<Node> {
        // текущий префикс
        var buffer = ""
        val dictionary: MutableMap<String, Int> = HashMap()
        val result: MutableList<Node> = ArrayList<Node>()
        for (element in input) {
            // если можем увеличить префикс
            if (dictionary.containsKey(buffer + element)) {
                buffer += element
            } else {
                // добавляем пару в ответ
                val node = Node()
                node.next = element
                node.position = dictionary.getOrDefault(buffer, 0)
                result.add(node)

                // добавляем новое слово в словарь и очищаем буффер
                dictionary[buffer + element] = dictionary.size + 1
                buffer = ""
            }
        }

        // если префикс не пуст, то добавляем его в конец словаря (в ответ)
        if (buffer.isNotEmpty()) {
            val lastSymbol = buffer[buffer.length - 1]
            buffer = lastSymbol.toString() + buffer.substring(0, buffer.length - 1)
            val node = Node()
            node.next = lastSymbol
            node.position = dictionary[buffer]
            result.add(node)
        }
        return result
    }
}

class Node {
    var position: Int? = null
    var next: Char? = null
}