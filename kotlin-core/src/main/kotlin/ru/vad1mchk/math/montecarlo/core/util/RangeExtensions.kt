package ru.vad1mchk.math.montecarlo.core.util

/**
 * Object with some methods that extend the functionality of ranges.
 */
object RangeExtensions {
    /**
     * Iterates through a range of doubles with the given [step].
     *
     * @param step Step of range.
     * @return An iterable that goes through the same range with the
     *     specified step.
     */
    infix fun ClosedRange<Double>.step(step: Double): Iterable<Double> {
        if (start == endInclusive) {
            return arrayOf(start).asIterable()
        }
        if (!start.isFinite() || !endInclusive.isFinite()) {
            throw ArithmeticException("Start and end of the range must be positive.")
        }
        if (start < endInclusive && step <= 0.0) {
            throw ArithmeticException("Step must be positive when going up.")
        }
        if (start > endInclusive && step >= 0.0) {
            throw ArithmeticException("Step must be negative when going down.")
        }
        return generateSequence(start) { previous ->
            if (!previous.isFinite()) return@generateSequence null
            val next = previous + step
            if (next > endInclusive) null else next
        }.asIterable()
    }

    /**
     * Returns a cryptographically strong random double value within
     * this range.
     *
     * @return A random double value within this range.
     */
    fun ClosedRange<Double>.random(): Double {
        return BetterRandom.nextDouble(start, endInclusive)
    }
}