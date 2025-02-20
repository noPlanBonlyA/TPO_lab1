package lab1

import java.util.PriorityQueue

data class Edge(val target: Int, val weight: Double)

class Graph(val vertices: Int) {
    private val adjacencyList: Array<MutableList<Edge>> = Array(vertices) { mutableListOf() }

    fun addEdge(source: Int, target: Int, weight: Double) {
        adjacencyList[source].add(Edge(target, weight))
    }

    fun dijkstra(start: Int): DoubleArray {
        val distances = DoubleArray(vertices) { Double.POSITIVE_INFINITY }
        distances[start] = 0.0

        val priorityQueue = PriorityQueue(compareBy<Pair<Int, Double>> { it.second })
        priorityQueue.add(Pair(start, 0.0))

        while (priorityQueue.isNotEmpty()) {
            val (currentVertex, currentDistance) = priorityQueue.poll()

            if (currentDistance > distances[currentVertex]) continue

            for (edge in adjacencyList[currentVertex]) {
                val distance = currentDistance + edge.weight

                if (distance < distances[edge.target]) {
                    distances[edge.target] = distance
                    priorityQueue.add(Pair(edge.target, distance))
                }
            }
        }

        return distances
    }
}
