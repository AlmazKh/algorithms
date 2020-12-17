package ru.itis

import java.math.BigInteger

fun main() {
    find2020()
}

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



// http://xn--o1acm.xn--80aaahbr4a1c.xn--p1acf/theory/lucky/index.htm
/*
  D 0 1 2 3 4 5
k 0 1 1 1 1
| 1   1 2 3
v 2   1 3 6
  .   . . .
  9   1 10 55
  10     9
  11     8
*/
