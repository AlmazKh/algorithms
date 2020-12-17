package ru.itis

import java.math.BigInteger

fun main() {
    fibonacci()
}

/*
* a 1 1 2 3 5
* b 1 2 3 5 8
* */
fun fibonacci() {
    var a = BigInteger.ONE
    var b = BigInteger.ONE
    val positionToFind = 500
    for (x in 3..positionToFind) {
        b += a
        a = b - a
    }
    print(b)
}