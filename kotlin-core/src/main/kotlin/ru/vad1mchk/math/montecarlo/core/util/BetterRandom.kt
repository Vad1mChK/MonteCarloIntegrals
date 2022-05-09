package ru.vad1mchk.math.montecarlo.core.util

import java.security.SecureRandom
import kotlin.random.asKotlinRandom

/**
 * Object that provides a more convenient way to interact with
 * [SecureRandom], which generates cryptographically strong randoms.
 */
object BetterRandom {
    private val secureRandomNumberGenerator = SecureRandom().asKotlinRandom()

    /**
     * Generates a random int value uniformly distributed between [left]
     * and [right].
     *
     * @param left Min value to generate.
     * @param right Max value to generate.
     * @return A random int value between [left] and [right].
     */
    fun nextInt(left: Int, right: Int): Int {
        if (left == right) return left
        if (left < right) return (left..right).random(secureRandomNumberGenerator)
        return (right..left).random(secureRandomNumberGenerator)
    }

    /**
     * Generates a random double value uniformly distributed between
     * [left] (inclusive) and [right] (exclusive).
     *
     * @param left Min value to generate.
     * @param right Max value to generate.
     * @return A random double value between [left] and [right].
     */
    fun nextDouble(left: Double, right: Double): Double {
        return left + (right - left) * secureRandomNumberGenerator.nextDouble()
    }
}