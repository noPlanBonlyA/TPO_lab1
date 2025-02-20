package lab1

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DijkstraTest {

    private fun createGraphWithEdges(): Graph {
        val graph = Graph(5)
        graph.addEdge(0, 1, 2.0)
        graph.addEdge(0, 2, 4.0)
        graph.addEdge(1, 2, 1.0)
        graph.addEdge(1, 3, 7.0)
        graph.addEdge(2, 4, 3.0)
        graph.addEdge(3, 4, 1.0)
        return graph
    }

    @Nested
    @DisplayName("Basic functionality tests")
    inner class BasicTests {

        @Test
        @DisplayName("Dijkstra should correctly find shortest paths in a simple graph")
        fun `dijkstra finds shortest paths`() {
            val graph = createGraphWithEdges()

            val log = mutableListOf<Pair<Int, Double>>()
            graph.logCallback = { vertex, distance -> log.add(Pair(vertex, distance)) }

            graph.dijkstra(0)

            val expectedLog = listOf(
                Pair(0, 0.0),
                Pair(1, 2.0),
                Pair(2, 3.0),
                Pair(4, 6.0),
                Pair(3, 9.0)
            )

            assertEquals(expectedLog, log)
        }
    }

    @Nested
    @DisplayName("Edge case tests")
    inner class EdgeCases {

        @Test
        @DisplayName("Dijkstra should handle a single-node graph")
        fun `dijkstra with single node`() {
            val graph = Graph(1)

            val log = mutableListOf<Pair<Int, Double>>()
            graph.logCallback = { vertex, distance -> log.add(Pair(vertex, distance)) }

            graph.dijkstra(0)

            val expectedLog = listOf(Pair(0, 0.0))
            assertEquals(expectedLog, log)
        }

        @Test
        @DisplayName("Dijkstra should handle a graph without edges")
        fun `dijkstra with empty graph`() {
            val graph = Graph(5)
            val distances = graph.dijkstra(0)

            assertEquals(0.0, distances[0])
            for (i in 1 until 5) {
                assertEquals(Double.POSITIVE_INFINITY, distances[i])
            }
        }
    }

    @Nested
    @DisplayName("Additional edge case tests")
    inner class AdditionalEdgeCases {

        @Test
        @DisplayName("Dijkstra should handle a fully connected graph with equal weights")
        fun `dijkstra with equal weights`() {
            val graph = Graph(4)
            for (i in 0 until 4) {
                for (j in 0 until 4) {
                    if (i != j) graph.addEdge(i, j, 1.0)
                }
            }

            val distances = graph.dijkstra(0)
            val expectedDistances = listOf(0.0, 1.0, 1.0, 1.0)
            assertEquals(expectedDistances, distances.toList())
        }

        @Test
        @DisplayName("Dijkstra should throw an exception for a non-existent starting vertex")
        fun `dijkstra with invalid start vertex throws exception`() {
            val graph = createGraphWithEdges()
            assertThrows(IndexOutOfBoundsException::class.java) {
                graph.dijkstra(10)
            }
        }

        @Test
        @DisplayName("Dijkstra should correctly handle a large graph")
        fun `dijkstra with large graph`() {
            val graph = Graph(1000)
            for (i in 0 until 999) {
                graph.addEdge(i, i + 1, 1.0)
            }
            val distances = graph.dijkstra(0)
            assertEquals(999.0, distances[999])
        }
    }
}