package ru.vad1mchk.math.montecarlo.core.integrals

import ru.vad1mchk.math.montecarlo.core.exceptions.IntegralException

/**
 * Base interface that should be implemented by all definite integrals.
 */
interface Integral {
    /**
     * Calculates the definite integral of the function.
     *
     * @return The double value of the definite integral.
     * @throws IntegralException if the integration did not succeed.
     */
    @Throws(IntegralException::class)
    fun integrate(): Double
}