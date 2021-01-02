package ru.itis.accepted


fun main() {
    findMaxCommonSeq(intArrayOf(-1, 1, 2, 3, 4, 6), intArrayOf(-2, 1, 2, 3, 5, 7, 8, 6, 9, 7, 9))
}

/*
* https://www.youtube.com/watch?t=1736&v=m4HOkVeN4Mo&feature=youtu.be&ab_channel=%D0%A2%D0%B8%D0%BC%D0%BE%D1%84%D0%B5%D0%B9%D0%A5%D0%B8%D1%80%D1%8C%D1%8F%D0%BD%D0%BE%D0%B2
* */

fun findMaxCommonSeq(seq1: IntArray, seq2: IntArray) {
    val firstSeq = seq1
    val secondSeq = seq2
    // длина наибольшей возможной подпоследовательности частей firstSeq и secondSeq
    val matrix = Array(firstSeq.size + 1) { Array(secondSeq.size + 1) { 0 } }
    var endValue = 0
    var maxEl = 0
    var maxI = 0
    var maxJ = 0
    val maxSeq = ArrayList<Int>()
    for (j in 1..secondSeq.size) {
        for (i: Int in 1..firstSeq.size) {
            if (firstSeq[i - 1] == secondSeq[j - 1]) {
                matrix[i][j] = maxOf(matrix[i - 1][j - 1], matrix[i - 1][j], matrix[i][j - 1]) + 1
            } else {
                matrix[i][j] = maxOf(matrix[i - 1][j - 1], matrix[i - 1][j], matrix[i][j - 1])
            }
            if (matrix[i][j] > maxEl) {
                endValue = firstSeq[i - 1]
                maxI = i
                maxJ = j
            }
            maxEl = matrix[i][j]
        }
    }
    //println(maxEl)
    maxSeq.add(endValue)
    while (maxEl > 0) {
        for (j in 1..maxJ) {
            for (i: Int in 1..maxI) {
                if (matrix[i][j] == maxEl - 1) {
                    maxSeq.add(firstSeq[i - 1])
                    maxI = i
                    maxJ = j
                    maxEl -= 1
                }
            }
        }
    }
    maxSeq.removeAt(maxSeq.size - 1)
    maxSeq.reverse()

    for (el in maxSeq) {
        print("$el ")
    }
}
// 5
// возрастающую строку сравнить саму с собой с отсортированной для нахождения НВП
// есть А, берем сортируем А и ищем подпоследовательность