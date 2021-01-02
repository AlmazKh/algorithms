package ru.itis

import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

object Jarvis {

    internal class Point(var x: Int, var y: Int) {
        override fun toString(): String {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}'
        }
    }

    // "Косое" произведение
    private fun crossProduct(a1: Point?, a2: Point?, b1: Point?, b2: Point?): Long {
        return (a2!!.x - a1!!.x).toLong() * (b2!!.y - b1!!.y) -
                (b2.x - b1.x).toLong() * (a2.y - a1.y)
    }

    // квадрат расстояния между двумя точками
    private fun distance(a1: Point?, a2: Point?): Long {
        return (a2!!.x - a1!!.x).toLong() * (a2.x - a1.x) +
                (a2.y - a1.y).toLong() * (a2.y - a1.y)
    }

    private fun jarvisConvexHull(a: Array<Point?>): Array<Point> {
        var m = 0
        for (i in 1 until a.size) {
            if (a[i]!!.x > a[m]!!.x) {
                m = i
            } else if (a[i]!!.x == a[m]!!.x && a[i]!!.y < a[m]!!.y) {
                m = i
            }
        }
        val p = arrayOfNulls<Point>(a.size + 1)
        p[0] = a[m]
        a[m] = a[0]
        a[0] = p[0]
        var k = 0
        var min = 0
        do {
            for (j in 1 until a.size) {
                if (crossProduct(p[k], a[min], p[k], a[j]) < 0 ||
                        crossProduct(p[k], a[min], p[k], a[j]) == 0L &&
                        distance(p[k], a[min]) < distance(p[k], a[j])) {
                    min = j
                }
            }
            k++
            p[k] = a[min]
            min = 0
        } while (!(p[k]!!.x == p[0]!!.x && p[k]!!.y == p[0]!!.y))
        return Arrays.copyOf(p, k)
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val strings = Files.readAllLines(Paths.get("27_input.txt"))
        val pointList: MutableList<Point> = ArrayList()
        for (st in strings) {
            val s = st.split(" ".toRegex()).toTypedArray()
            val p = Point(s[0].toInt(), s[1].toInt())
            pointList.add(p)
        }
        val points = jarvisConvexHull(pointList.toTypedArray())
        println(points.size)
        val coordsToWrite: MutableList<String> = ArrayList()
        for (p in points) {
            val coords = p.x.toString() + " " + p.y
            coordsToWrite.add(coords)
        }
        Files.write(Paths.get("31_output.txt"), coordsToWrite)
    }
}
