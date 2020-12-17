package ru.itis.experiments

fun main() {
    countFlours(20, 1000)
}

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
                floorsSkipped += a
                countFlours(n, m - a)
            }
        }
}
