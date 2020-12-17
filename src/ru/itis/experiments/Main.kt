import java.math.BigInteger
import java.util.*
import java.util.Arrays.copyOf
import java.util.EnumSet.copyOf
import kotlin.collections.ArrayList
import kotlin.math.max
import kotlin.math.min

fun main() {
    //citiesGame()
    //fibonacci()
    //findMaxCommonSeq()
    //xor()
    //find2020()

    //countFlours(3, 25)
    intersects()
}
// нет в списке про два ряда пуговиц
//раскраска графа, хитрый алгоритм
// 26
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

//стеклянные шарики 6 и 7
var floorsSkipped = 0
fun countFlours(n: Int, m: Int) {
    val k = 1000
    val matrix = Array(n) { Array(k) { 1 } } // n - X k - Y
    for (i in 0 until k)
        matrix[0][i] = i + 1

    for (i in 1 until n)
        for (j in 1 until k) {
            matrix[i][j] = matrix[i - 1][j - 1] + matrix[i - 1][j]
        }
    var a = 1
    var tries = 1
    if (n!=1)
    for (i in 0 until k) {
        if (matrix[n - 1][i] >= m) {
            a = matrix[n - 2][i - 1]
            tries = i
            println("у вас $n шаров нужно $i попыток, начинаем с ${a + floorsSkipped} этажа")
            break
        }
    }else
        println("у вас $n шаров нужно  ${m-1} попыток, начинаем с ${a + floorsSkipped} этажа")
    println()
    if (tries != 1)
    when (readLine()) {
        "1" -> {
            countFlours(n - 1, m - (m - a))
        }
        else -> {
            floorsSkipped+= a
            countFlours(n, m - a)
        }
    }
}
// 10
fun find2020() {
    val start = System.currentTimeMillis()
    val n = 1010
    var prevArray = arrayListOf<BigInteger>(
        BigInteger.ONE,
        BigInteger.ONE,
        BigInteger.ONE,
        BigInteger.ONE,
        BigInteger.ONE,
        BigInteger.ONE,
        BigInteger.ONE,
        BigInteger.ONE,
        BigInteger.ONE,
        BigInteger.ONE
    )
    var newArray = arrayListOf<BigInteger>()
    for (i in 2 until n) {
        for (j in 0..i * 9 / 2)
            newArray.add(
                (newArray.lastOrNull()
                    ?: BigInteger.ZERO) + prevArray[j] - (prevArray.getOrNull(j - 10)
                    ?: BigInteger.ZERO)
            )
        newArray.asReversed().apply {
            newArray.addAll(this)
            if (i % 2 == 0) removeAt(i * 9 / 2)
        }
        prevArray = newArray
        newArray = arrayListOf()
    }
    var sum = BigInteger.ZERO
    prevArray.forEach {
        sum += it * it
    }
    val finish = System.currentTimeMillis()
    println(sum)
    print(start - finish)
}

// 8
fun citiesGame() {
    val list = listOf(
        "казан",
        "нью-йорк",
        "нижний тагил"
    )
    var string = "Not found"
    fun findNext(localList: List<String>, current: String, count: Int) {
        //println(current)
        if (count == list.size - 1) {
            string = current
            return
        }
        for (city in localList) {
            if (city.first() == current.last())
                findNext(localList - city, current + city, count + 1)
        }
    }
    for (city in list)
        findNext(list - city, city, 0)
    println(string)
}

// 2
fun xor() {
    var a = 155
    var b = 25
    a = a xor b
    b = b xor a
    a = b xor a
    print("$a $b")
}
// 1
fun fibonacci() {
    var a: BigInteger = BigInteger.ONE
    var b: BigInteger = BigInteger.ONE
    val positionToFind = 500
    for (x in 3..positionToFind) {
        b += a
        a = b - a
    }
    print(b)
}

//уникс
//университет
//12346
//1235786979
// возрастающую строку сравнить саму с собой с отсортированной 4 и 5
fun findMaxCommonSeq() {
    val firstSeq = intArrayOf(
        -1,
        1, 2, 3, 4, 6
    )
    val secondSeq = intArrayOf(
        -2,
        1, 2, 3, 5, 7, 8, 6, 9, 7, 9
    )
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