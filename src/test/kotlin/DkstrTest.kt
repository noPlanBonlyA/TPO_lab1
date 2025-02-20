package lab1

import kotlin.test.Test
import kotlin.test.assertEquals

class DijkstraTest {

    @Test
    fun testDijkstraSimpleGraph() {
        val graph = Graph(5)
        graph.addEdge(0, 1, 2.0)
        graph.addEdge(0, 2, 4.0)
        graph.addEdge(1, 2, 1.0)
        graph.addEdge(1, 3, 7.0)
        graph.addEdge(2, 4, 3.0)
        graph.addEdge(3, 4, 1.0)

        val distances = graph.dijkstra(0)

        assertEquals(0.0, distances[0])
        assertEquals(2.0, distances[1])
        assertEquals(3.0, distances[2])
        assertEquals(9.0, distances[3])
        assertEquals(6.0, distances[4])
    }

    @Test
    fun testDijkstraWithUnreachableNodes() {
        val graph = Graph(3)
        graph.addEdge(0, 1, 5.0)
        // Node 2 is not reachable

        val distances = graph.dijkstra(0)

        assertEquals(0.0, distances[0])
        assertEquals(5.0, distances[1])
        assertEquals(Double.POSITIVE_INFINITY, distances[2])
    }
}