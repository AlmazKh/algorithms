package ru.itis

import java.io.*


/*
* стр. 387 глава 15 из Кормена (про конвейер)
* */
object Vaccine {
    var reader: BufferedReader? = null
    var writer: BufferedWriter? = null

    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val a = processInputLine(reader!!.readLine())
        val b = processInputLine(reader!!.readLine())
        val fA = processInputLine(reader!!.readLine())
        val fB = processInputLine(reader!!.readLine())
        val f1 = IntArray(a.size - 1)
        val f2 = IntArray(a.size - 1)
        val l1 = IntArray(a.size - 2)
        val l2 = IntArray(a.size - 2)

        // Стоимость начала разработки в какой-либо стране
        f1[0] = a[0]
        f2[0] = b[0]
        for (i in 1 until a.size - 1) {

            // Стоимость i этапа в первой стране (России)
            val f1Cost = f1[i - 1] + a[i]
            // Стоимость i этапа если провести этап в России, приехав из Китая
            val f2Tof1Cost = f2[i - 1] + fB[i - 1] + a[i]

            // Обнаружение минимальной стоимости разработки i этапа
            if (f1Cost <= f2Tof1Cost) {
                f1[i] = f1Cost
                l1[i - 1] = 1
            } else {
                f1[i] = f2Tof1Cost
                l1[i - 1] = 2
            }

            // Стоимость i этапа во 2 стране (Китае)
            val f2Cost = f2[i - 1] + b[i]
            // Стоимость i этапа если провести этап в Китая, приехав из России
            val f1Tof2Cost = f1[i - 1] + fA[i - 1] + b[i]

            // Обнаружение минимальной стоимости разработки i этапа
            if (f2Cost <= f1Tof2Cost) {
                f2[i] = f2Cost
                l2[i - 1] = 2
            } else {
                f2[i] = f1Tof2Cost
                l2[i - 1] = 1
            }
        }

        // Нахождение страны, в которой следует заканчивать разработку для самой быстрого создания вакцины
        val country = if (f1[a.size - 2] + a[a.size - 1] <= f2[a.size - 2] + b[a.size - 1]) 1 else 2
        writer!!.write("Country " + country + " step " + f1.size)
        writer!!.newLine()

        // Вывод этапов разработки на основе страны, в которой следует закончить разработку
        for (i in l1.indices.reversed()) {
            val way = if (country == 1) l2[i] else l1[i]
            writer!!.write("Country " + way + " step " + (i + 1))
            writer!!.newLine()
        }
        writer!!.close()
    }

    private fun processInputLine(line: String): IntArray {
        val numbers = line.trim { it <= ' ' }.split(" ".toRegex()).toTypedArray()
        val array = IntArray(numbers.size)
        for (i in numbers.indices) {
            array[i] = numbers[i].toInt()
        }
        return array
    }

    init {
        try {
            reader = BufferedReader(FileReader(File("03_input.txt")))
            writer = BufferedWriter(FileWriter(File("03_output.txt")))
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}