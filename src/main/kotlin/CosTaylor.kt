package lab1

import kotlin.math.pow
import kotlin.math.abs

fun factorial(n: Int): Long {
    return if (n <= 1) 1 else n * factorial(n - 1)
}

fun cosTaylor(x: Double, terms: Int = 10): Double {
    var result = 0.0
    for (n in 0 until terms) {
        val term = ((-1.0).pow(n) * x.pow(2 * n)) / factorial(2 * n)
        result += term
    }
    return result
}

fun main() {
    val x = Math.PI / 4
    println("cos($x) по ряду Тейлора: ${cosTaylor(x, 10)}")
    println("cos($x) через Math.cos: ${Math.cos(x)}")
}