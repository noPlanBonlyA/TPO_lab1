package lab1

import java.util.PriorityQueue

data class Edge(val target: Int, val weight: Double)

class Graph(private val vertices: Int) {
    private val adjacencyList: Array<MutableList<Edge>> = Array(vertices) { mutableListOf() }
    var logCallback: ((Int, Double) -> Unit)? = null

    fun addEdge(source: Int, target: Int, weight: Double) {
        require(weight >= 0) { "Dijkstra не поддерживает отрицательные веса!" }
        adjacencyList[source].add(Edge(target, weight))
    }

    fun dijkstra(start: Int): DoubleArray {
        val distances = DoubleArray(vertices) { Double.POSITIVE_INFINITY }
        distances[start] = 0.0

        val priorityQueue = PriorityQueue(compareBy<Pair<Int, Double>> { it.second })
        priorityQueue.add(Pair(start, 0.0))

        val visited = BooleanArray(vertices)

        while (priorityQueue.isNotEmpty()) {
            val (currentVertex, currentDistance) = priorityQueue.poll()

            if (visited[currentVertex]) continue
            visited[currentVertex] = true

            logCallback?.invoke(currentVertex, currentDistance)

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
