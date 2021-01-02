package ru.itis.accepted

import java.util.*

fun main() {
    val k = 3 // шары
    val n = 25 // этажи
    val dropAttemptCount = computeMinBallsDropCount(k, n)
    val x = dropAttemptCount[k][n]
    println(x)
    printInstructions(k, n, x, dropAttemptCount)
}


private fun printInstructions(k: Int, n: Int, totalAttemptNeedCount: Int, dropAttemptCount: Array<IntArray>) {
    var k = k
    var n = n
    var currentFloor = -1
    var attemptCount = 0
    var lastCrashedFloor = n + 1
    var lastNonCrashedFloor = 0
    val originalFloorCount = n
    while (true) {
        val prevFloor = currentFloor
        if (k == 1) {
            currentFloor = lastNonCrashedFloor + 1
        } else {
            for (j in 1 until n + 1) {
                if (dropAttemptCount[k][j] == totalAttemptNeedCount - attemptCount) {
                    currentFloor = n - j + 1
                    break
                }
            }
        }
        if (prevFloor == currentFloor) {
            currentFloor = dropAttemptCount[k][n]
        }
        attemptCount++
        printDropFrom(currentFloor, attemptCount)
        val scanner = Scanner(System.`in`)
        println("Мяч разбился?")
        if (!scanner.nextLine().equals("да", ignoreCase = true)) {
            println("Мяч не разбился")
            lastNonCrashedFloor = currentFloor
        } else {
            println("Мяч разбился")
            lastCrashedFloor = currentFloor
            n = lastCrashedFloor - 1
            k -= 1
        }
        if (lastNonCrashedFloor + 1 == lastCrashedFloor || lastCrashedFloor == 1 || lastCrashedFloor == originalFloorCount) {
            println()
            if (lastCrashedFloor > originalFloorCount || lastCrashedFloor == 1) {
                println("Этаж не найден")
            } else {
                println("Мяч разбивается с: $lastCrashedFloor этажа")
            }
            return
        }
        println()
    }
}

private fun printDropFrom(floor: Int, attempt: Int) {
    println("$attempt попытка. Кидаем мяч с $floor этажа")
}

private fun computeMinBallsDropCount(k: Int, n: Int): Array<IntArray> {
    val dropAttemptCount = Array(k + 1) { IntArray(n + 1) }
    for (i in 0..n) {
        dropAttemptCount[0][i] = 0
        dropAttemptCount[1][i] = i
    }
    for (j in 0..k) {
        dropAttemptCount[j][0] = 0
    }
    for (i in 2..k) {
        for (j in 1..n) {
            var minimum = Int.MAX_VALUE
            for (x in 1..j) {
                minimum = Math.min(minimum, 1 + Math.max(dropAttemptCount[i][j - x], dropAttemptCount[i - 1][x - 1]))
            }
            dropAttemptCount[i][j] = minimum
        }
    }
    return dropAttemptCount
}