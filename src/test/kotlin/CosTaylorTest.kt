package lab1

import kotlin.math.cos
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test


class CosTaylorTest {

    @Test
    fun testCosTaylorAtZero() {
        val result = cosTaylor(0.0, 10)
        assertEquals(1.0, result, 1e-6)
    }

    @Test
    fun testCosTaylorAtPiDiv4() {
        val x = Math.PI / 4
        val result = cosTaylor(x, 10)
        assertEquals(cos(x), result, 1e-6)
    }

    @Test
    fun testCosTaylorAtPi() {
        val x = Math.PI
        val result = cosTaylor(x, 10)
        assertEquals(cos(x), result, 1e-6)
    }
}
