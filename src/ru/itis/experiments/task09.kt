/*
package ru.itis

import */
/**//*
lombok.Builder
import lombok.Data
import java.util.*
import java.util.function.Consumer
import java.util.function.Function
import java.util.function.Predicate
import java.util.stream.Stream


object EulerChecker {
    fun checkForEulerPath(graph: Map<Char?, List<CityGame.Edge?>>): Boolean {
        val vertexDeg: MutableList<Deg> = ArrayList()
        graph.keys.forEach(Consumer { vertex: Char? -> vertexDeg.add(deg(vertex, graph)) })
        val oddVertex = vertexDeg.stream()
                .filter { deg: Deg -> deg.degSum % 2 != 0 }
                .count()
        return oddVertex <= 2 && vertexDeg.stream().anyMatch { obj: Deg -> obj.isStartVertex } && vertexDeg.stream().anyMatch { obj: Deg -> obj.isEndVertex }
    }

    fun deg(vertex: Char?, graph: Map<Char?, List<CityGame.Edge?>>): Deg {
        val outCount = graph[vertex]!!.size
        val inCount = graph.values.stream()
                .flatMap<CityGame.Edge?>(Function<List<CityGame.Edge?>, Stream<out CityGame.Edge?>> { obj: List<CityGame.Edge?> -> obj.stream() })
                .filter(Predicate<CityGame.Edge?> { word: CityGame.Edge? -> word.getTo().equals(vertex) })
                .count().toInt()
        return builder()
                .outCount(outCount)
                .inCount(inCount)
                .build()
    }

    @Data
    @Builder
    class Deg {
        private val inCount = 0
        private val outCount = 0
        val degSum: Int
            get() = inCount + outCount
        val isStartVertex: Boolean
            get() = outCount - inCount == 1
        val isEndVertex: Boolean
            get() = inCount - outCount == 1
    }
}*/
