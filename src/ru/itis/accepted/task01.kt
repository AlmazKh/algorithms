package ru.itis.accepted

import java.math.BigInteger

fun main() {
    fibonacci(500)
}

/*
* a 1 1 2 3 5
* b 1 2 3 5 8
* */
fun fibonacci(positionToFind: Int) {
    var a = BigInteger.ONE
    var b = BigInteger.ONE
    for (x in 3..positionToFind) {
        b += a
        a = b - a
    }
    print(b)
}
