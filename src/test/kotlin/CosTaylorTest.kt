package lab1

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.math.PI
import kotlin.math.cos

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CosTaylorTest {

    private val precision = 1e-6

    @Nested
    @DisplayName("Basic functionality tests")
    inner class BasicCases {

        @Test
        fun `cosTaylor at zero should be 1`() {
            assertEquals(1.0, cosTaylor(0.0, precision), precision)
        }

        @ParameterizedTest
        @ValueSource(doubles = [PI / 4, PI / 2, PI, 2 * PI, -PI / 4, -PI / 2, -PI, -2 * PI])
        fun `cosTaylor should match Math_cos`(x: Double) {
            assertEquals(cos(x), cosTaylor(x, precision), precision)
        }

        @Test
        fun `cosTaylor at irrational x should match Math_cos`() {
            val x = PI / Math.E
            assertEquals(cos(x), cosTaylor(x, precision), precision)
        }
    }

    @Nested
    @DisplayName("Edge case tests")
    inner class EdgeCases {

        @Test
        fun `cosTaylor at very large x should match Math_cos`() {
            val x = 1e10
            assertEquals(cos(x), cosTaylor(x, precision), precision)
        }

        @Test
        fun `cosTaylor at very small x should be close to 1`() {
            val x = 1e-10
            assertEquals(cos(x), cosTaylor(x, 1e-12), 1e-12)
        }

        @Test
        fun `cosTaylor with zero terms should return 0 for pi-div-2`() {
            val x = PI / 2
            assertEquals(0.0, cosTaylor(x, precision), precision)
        }
    }

    @Nested
    @DisplayName("Precision tests")
    inner class PrecisionTests {

        @Test
        fun `cosTaylor with high precision should be more accurate`() {
            val x = PI / 4
            val resultHigh = cosTaylor(x, 1e-12)
            assertEquals(cos(x), resultHigh, 1e-12)
        }

        @Test
        fun `cosTaylor with low precision should be less accurate`() {
            val x = PI / 4
            val resultLow = cosTaylor(x, 1e-2)
            assertEquals(cos(x), resultLow, 1e-2)
        }
    }

    @Nested
    @DisplayName("Convergence tests")
    inner class ConvergenceTests {

        @Test
        fun `cosTaylor with more terms should converge better`() {

            val x = PI / 3
            val exactValue = cos(x)

            val resultLowTerms = cosTaylor(x, precision, 5)
            val resultHighTerms = cosTaylor(x, precision, 15)

            val errorLow = kotlin.math.abs(resultLowTerms - exactValue)
            val errorHigh = kotlin.math.abs(resultHighTerms - exactValue)

            assert(errorHigh < errorLow)
        }

        @Test
        fun `cosTaylor with 2 terms should follow initial Taylor series`() {
            val x = PI / 4
            val expected = 1.0 - (x * x) / 2
            assertEquals(expected, cosTaylor(x, precision, 2), precision)
        }
    }
}
