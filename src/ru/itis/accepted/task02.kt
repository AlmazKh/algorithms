package ru.itis.accepted

fun main() {
    xor()
}
/*  a|b|xor
    0|0|0
    0|1|1
    1|0|1
    1|1|0*/
fun xor() {
    var a = 155
    var b = 25
    a = a xor b
    b = b xor a
    a = b xor a
    print("$a $b")
}
