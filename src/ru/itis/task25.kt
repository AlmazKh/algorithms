package ru.itis

import java.util.*

fun main() {
    val n: Long
    val result: Boolean
    val k = 4

    println("Введите число:")
    val scanner = Scanner(System.`in`)
    n = scanner.nextLong()
    result = isPrime(n, k.toLong())
    println(if (result) "Простое" else "Не простое")
}

fun power(x: Long, y: Long, p: Long): Long {
    var x = x
    var y = y
    var res: Long = 1

    //обновляет, если это больше или равно p
    x %= p
    while (y > 0) {
        if (y and 1 == 1L) res = res * x % p

        y = y shr 1
        x = x * x % p
    }
    return res
}

fun millerTest(d: Long, n: Long): Boolean {
    // рандомное число от 2 до n-2
    val a = 2 + (Math.random() % (n - 4)).toInt()
    var x = power(a.toLong(), d, n)

    if (x == 1L || x == n - 1) return true

    var d2 = d
    while (d2 != n - 1) {
        x = x * x % n
        d2 *= 2
        if (x == 1L) return false
        if (x == n - 1) return true
    }
    return false
}

fun isPrime(n: Long, k: Long): Boolean {
    if (n <= 1 || n == 4L) return false
    if (n <= 3) return true
    var d = n - 1

    while (d % 2 == 0L) d /= 2

    for (i in 0 until k)
        if (!millerTest(d, n)) return false
    return true
}
