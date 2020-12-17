package ru.itis

import java.util.*
import kotlin.collections.HashMap
import kotlin.math.abs

class ShannonFano {
    /**
     * Call methods to build the list, create an empty code table, and
     * then start recursively populating the code table
     */
    fun <T> getCodeTable(counts: Counts<T>): Map<T, MutableList<Boolean>> {
        val list = buildList(counts)
        val codeTable = buildEmptyCodeTable(counts)
        populateCodeTable(codeTable, list, counts)
        return codeTable
    }

    companion object {
        /**
         * Sort our input probability list descending
         *
         * @param counts Element counts
         * @return Elements sorted by probability
         */
        private fun <T> buildList(counts: Counts<T>): List<T> {
            val countsKeys = counts.elements!!
            val list: MutableList<T> = ArrayList(countsKeys.size)
            for (t in countsKeys) list.add(t)
            list.sortWith { o1: T, o2: T -> -counts.getCount(o1).compareTo(counts.getCount(o2)) }
            return list
        }

        /**
         * Build an empty code table, i.e. zero length bit vector for
         * each element
         *
         * @param counts Element counts
         * @return The empty bit vector code table
         */
        private fun <T> buildEmptyCodeTable(
                counts: Counts<T>): Map<T, MutableList<Boolean>> {
            val countsKeys = counts.elements!!
            val codeTable: MutableMap<T, MutableList<Boolean>> = HashMap(countsKeys.size)
            for (t in countsKeys) codeTable[t] = ArrayList()
            return codeTable
        }

        /**
         * Recursively populate the code table.
         *
         * Split in two parts with (mostly) equal probabilities. For one part
         * add zeros for the other ones to the bit vectors. Then split those parts
         * again until they contain only one element.
         *
         * @param codeTable The code table filled so far (in place)
         * @param list      Part of the list to process
         * @param counts    Element counts
         */
        private fun <T> populateCodeTable(codeTable: Map<T, MutableList<Boolean>>,
                                          list: List<T>, counts: Counts<T>) {
            if (list.size <= 1) return
            var sum = 0
            var fullSum = 0
            for (t in list) fullSum += counts.getCount(t)
            var bestdiff = 5f
            var i = 0
            out@ while (i < list.size) {
                val prediff = bestdiff
                sum += counts.getCount(list[i])
                bestdiff = abs(sum.toFloat() / fullSum - 0.5f)
                if (prediff < bestdiff) break@out
                i++
            }
            for (j in list.indices) {
                if (j < i) codeTable[list[j]]!!.add(java.lang.Boolean.FALSE) else codeTable[list[j]]!!.add(java.lang.Boolean.TRUE)
            }
            populateCodeTable(codeTable, list.subList(0, i), counts)
            populateCodeTable(codeTable, list.subList(i, list.size), counts)
        }

        @JvmStatic
        fun main(args: Array<String>) {
            println("Введите текст:")
            val input = Scanner(System.`in`)
            val s = input.nextLine()
            val countsMap: MutableMap<Char, Int> = HashMap()
            for (element in s) {
                if (countsMap.containsKey(element)) {
                    countsMap[element] = countsMap[element]!! + 1
                } else {
                    countsMap[element] = 1
                }
            }
            val counts = Counts(countsMap)
            val table = ShannonFano().getCodeTable(counts)
            val sb = StringBuffer()
            sb.append("Code table:\n")
            for (c in countsMap.keys) {
                sb.append(" $c -> ")
                for (b in table[c]!!) sb.append(if (!b) '0' else '1')
                sb.append("\n")
            }
            println(sb.toString())
        }
    }
}

class Counts<T>(map: Map<T, Int>?) {
    /**
     * Holds the element counts
     */
    private var map: Map<T, Int> = Collections.unmodifiableMap(map)
    /**
     * Get the set of elements for which counts exist
     * @return
     */
    /**
     * Holds all the different elements
     */
    var elements: Set<T>? = null
        get() {
            if (field == null) {
                field = map.keys
            }
            return field
        }
        private set

    /**
     * Get the count for one element
     * @param t The element
     * @return The number of occurrences
     */
    fun getCount(t: T): Int {
        var returnValue = map[t]
        if (returnValue == null) returnValue = 0
        return returnValue
    }
}
