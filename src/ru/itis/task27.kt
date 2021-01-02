package ru.itis

import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

class Graham {
    class Point(var x: Double, var y: Double) : Comparable<Point?> {
        val POLAR_ORDER: Comparator<Point?> = PolarOrder()

        override fun toString(): String {
            return "($x, $y)"
        }

        override operator fun compareTo(other: Point?): Int {
            other?.let {
                if (y < it.y) return -1
                if (y > it.y) return +1
                if (x < it.x) return -1
                return if (x > it.x) +1 else 0
            }
            return 0
        }

        // класс для сортировки по полярному углу
        inner class PolarOrder : Comparator<Point?> {
            override fun compare(q1: Point?, q2: Point?): Int {
                if (q1 != null && q2 != null) {
                    val dx1 = q1.x - x
                    val dy1 = q1.y - y
                    val dx2 = q2.x - x
                    val dy2 = q2.y - y
                    return if (dy1 >= 0 && dy2 < 0) -1 // q1 выше; q2 ниже
                    else if (dy2 >= 0 && dy1 < 0) +1 // q1 ниже; q2 выше
                    else if (dy1 == 0.0 && dy2 == 0.0) { // коллинеарные
                        if (dx1 >= 0 && dx2 < 0) -1 else if (dx2 >= 0 && dx1 < 0) +1 else 0
                    } else -rotate(this@Point, q1, q2) // обе сверху или снизу
                }
                return 0
            }
        }

        companion object {
            fun rotate(a: Point?, b: Point?, c: Point?): Int {
                val area = (b!!.x - a!!.x) * (c!!.y - a.y) - (b.y - a.y) * (c.x - a.x)
                return if (area < 0) -1 else if (area > 0) +1 else 0
            }
        }

        init {
            if (x == 0.0) this.x = 0.0 // convert -0.0 to +0.0
            if (y == 0.0) this.y = 0.0 // convert -0.0 to +0.0
        }
    }

    // стек точек - оболочка
    private val hull = Stack<Point?>()

    // метод для получения точек из стека (используется для вывода)
    fun hull(): Iterable<Point?> {
        val s = Stack<Point?>()
        for (p in hull) s.push(p)
        return s
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val strings = Files.readAllLines(Paths.get("C:\\Users\\Almaz\\IdeaProjects\\algorithms\\27_input.txt"))
            val pointList: MutableList<Point> = ArrayList()
            for (st in strings) {
                val s = st.split(" ".toRegex()).toTypedArray()
                val p = Point(s[0].toInt().toDouble(), s[1].toInt().toDouble())
                pointList.add(p)
            }
            val graham = Graham(pointList.toTypedArray())
            println("Выпуклая оболочка состоит из следующих точек: ")
            val coordsToWrite: MutableList<String> = ArrayList()
            for (p in graham.hull()) {
                println(p)
                val coords = p!!.x.toString() + " " + p.y
                coordsToWrite.add(coords)
            }
            Files.write(Paths.get("C:\\Users\\Almaz\\IdeaProjects\\algorithms\\27_output.txt"), coordsToWrite)
        }
    }

    constructor(pts: Array<Point?>) {
        val N = pts.size
        val points = arrayOfNulls<Point>(N)
        System.arraycopy(pts, 0, points, 0, N)
        Arrays.sort(points)
        Arrays.sort(points, 1, N, points[0]?.POLAR_ORDER)
        hull.push(points[0]) // p[0] первая точка
        var k1 = 1
        while (k1 < N) {
            if (points[0] != points[k1]) break
            k1++
        }
        if (k1 == N) return
        var k2: Int
        k2 = k1 + 1
        while (k2 < N) {
            if (Point.rotate(points[0], points[k1], points[k2]) != 0) break
            k2++
        }
        hull.push(points[k2 - 1]) // points[k2-1] вторая крайняя точка
        // процесс срезания углов
        for (i in k2 until N) {
            var top = hull.pop()
            while (Point.rotate(hull.peek(), top, points[i]) <= 0) {
                top = hull.pop()
            }
            hull.push(top)
            hull.push(points[i])
        }
    }
}