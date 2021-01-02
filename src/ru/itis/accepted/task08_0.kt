package ru.itis.accepted

fun main() {
    citiesGame()
}

fun citiesGame() {
    val list = listOf(
            "казан",
            "нью-йорк",
            "ниовгород"
    )
    var string = "Not found"
    fun findNext(localList: List<String>, current: String, count: Int) {
        //println(current)
        if (count == list.size - 1) {
            string = current
            return
        }
        for (city in localList) {
            if (city.first() == current.last())
                findNext(localList - city, current + city, count + 1)
        }
    }
    for (city in list)
        findNext(list - city, city, 0)
    println(string)
}
