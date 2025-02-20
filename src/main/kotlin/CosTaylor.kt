package lab1

import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.PI


fun cosTaylor(x: Double, tolerance: Double = 1e-6, maxTerms: Int = 100): Double {

    val xNorm = x % (2 * PI)
    var result = 0.0
    var term = 1.0
    var n = 0

    result += term
    n++

    while (n < maxTerms) {
        term *= -xNorm * xNorm / ((2 * n - 1) * (2 * n))
        result += term

        if (abs(term) < tolerance) break

        n++
    }

    return result
}


fun main() {
    val x = Math.PI / 4
    println("cos($x) по ряду Тейлора: ${cosTaylor(x)}")
    println("cos($x) через Math.cos: ${cos(x)}")
}