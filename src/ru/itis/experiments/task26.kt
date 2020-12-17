package ru.itis.experiments

import kotlin.math.min

// нет в списке про два ряда пуговиц
//раскраска графа, хитрый алгоритм
// 26

fun main() {
    intersects()
}

fun intersects() {
    var list = mutableListOf<Pair<Int, Int>>(
            Pair(1, 4),
            Pair(2, 3),
            Pair(2, 4),
            Pair(3, 6),
            Pair(4, 7),
            Pair(8, 5),
            Pair(4, 1),
            Pair(2, 1)
    )
    val floors = listOf(
            list,
            mutableListOf(),
            mutableListOf(),
            mutableListOf(),
            mutableListOf(),
            mutableListOf(),
            mutableListOf(),
            mutableListOf(),
            mutableListOf(),
            mutableListOf(),
            mutableListOf()
    )

    for (floorIndex in floors.indices) {
        val indexesToRemove = mutableListOf<Int>()
        floors[floorIndex].sortBy { min(it.first, it.second) }
        for (lineIndex in floors[floorIndex].indices) {
            val line = floors[floorIndex][lineIndex]
            for (compareLineIndex in lineIndex + 1 until floors[floorIndex].size) {
                fun moveLine() {
                    floors[floorIndex + 1].add(line)
                    indexesToRemove.add(lineIndex)
                }

                val compareLine = floors[floorIndex][compareLineIndex]
                if ((line.first <= compareLine.first && line.second >= compareLine.second) ||
                        (line.first > compareLine.first && compareLine.second > line.second) ||
                        (line.second > compareLine.second && compareLine.first > line.first)
                ) {
                    moveLine()
                    break
                }
            }
        }
        var iterator = 0
        indexesToRemove.forEach {
            floors[floorIndex].removeAt(it - iterator++)
        }
    }

    floors.forEach {
        if (it.isNotEmpty())
            println()
        it.forEach {
            print("$it  ")
        }
    }
    println()
    println(floors.count { it.isNotEmpty() })
}
