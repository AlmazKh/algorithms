package ru.itis

object KMP {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = "bbccbdaabccabc"
        val substring = "abc"
        for (index in getKmp(substring, input)) {
            if (index > 0) {
                print("$index ")
            }
        }
    }

    private fun getKmp(substring: String, input: String): IntArray {
        val inputLength = input.length
        val substringLength = substring.length
        val result = IntArray(inputLength)
        val prefix = getPrefix("$substring#$input")
        var count = 0
        for (i in 0 until inputLength) {
            if (prefix[substringLength + i + 1] == substringLength) {
                result[count++] = i
            }
        }
        return result
    }

    // Вычисление префикс функции = массив длин наибольших бордеров
    private fun getPrefix(s: String): IntArray {
        // Создаем пустой массив размерности n, n = длина строки
        val result = IntArray(s.length)

        // в цикле будем искать бордер максимальной длины,
        // сравнивая суффиксы и префиксы строки
        for (i in 1 until s.length) {
            var j = result[i - 1]

            // вместо явного сравнения срок будем сравнивать подстроки меньшей длины,
            // чтобы найти бордер большей длины
            while (j > 0 && s[i] != s[j]) {
                j = result[j - 1]
            }

            // если нашли бордер, то увеличиваем значение j, чтобы найти длину бордера
            if (s[i] == s[j]) {
                j++
            }
            result[i] = j
        }
        return result
    }
}