package ru.vad1mchk.math.montecarlo.app.integrals

import net.objecthunter.exp4j.Expression
import ru.vad1mchk.math.montecarlo.core.exceptions.ImproperIntegralException
import ru.vad1mchk.math.montecarlo.core.integrals.Integral
import ru.vad1mchk.math.montecarlo.core.util.RangeExtensions.step

/**
 * Basic implementation of definite integrals calculated using
 * Monte-Carlo methods.
 *
 * @property leftLimit Left limit of integration domain.
 * @property rightLimit Right limit of integration domain.
 * @property integrand Function to integrate.
 */
abstract class MonteCarloIntegral(
    val leftLimit: Double,
    val rightLimit: Double,
    val integrand: Expression
) : Integral {
    companion object {
        /** Number of dots to drop by default. */
        const val DEFAULT_DOTS_TO_DROP = 1_000_000

        /**
         * Number of intervals to break the integration domain into.
         */
        const val DEFAULT_INTEGRATION_INTERVALS = 1_048_576
    }

    protected var dotsToDrop = DEFAULT_DOTS_TO_DROP
    protected var integrationStep: Double
    protected var integrandName = "f(x)"
    protected val isZero: Boolean
    protected val isReversed: Boolean
    var minValue: Double = 0.0
        protected set
    var maxValue: Double = 0.0
        protected set

    init {
        // Checks that the integral is proper. Otherwise, we are not obliged to calculate it, since the area might be
        // infinite.
        if (!leftLimit.isFinite() || !rightLimit.isFinite()) {
            throw ImproperIntegralException(
                "Encountered improper integral (Type I): one or both of the borders is not finite.",
                ImproperIntegralException.Type.ARBITRARY_DOMAIN
            )
        }
        // Checks that borders are not equal nor reversed.
        isZero = leftLimit == rightLimit
        isReversed = leftLimit > rightLimit
        // If the integral is proper, set the integration step to be 1/DEFAULT_INTEGRATION_INTERVALS of the domain.
        integrationStep = (rightLimit - leftLimit) * (if (isReversed) -1 else 1 )/ DEFAULT_INTEGRATION_INTERVALS
    }

    /**
     * Changes the number of dots to drop and returns this integral.
     *
     * @param dotsToDrop New count of dots dropped.
     * @return This integral.
     */
    fun dotsToDrop(dotsToDrop: Int): MonteCarloIntegral {
        if (dotsToDrop > 0) this.dotsToDrop = dotsToDrop
        return this
    }

    /**
     * Changes the integration step and returns this integral.
     *
     * @param integrationStep Step of integration.
     * @return This integral.
     */
    fun integrationStep(integrationStep: Double): MonteCarloIntegral {
        if (integrationStep > 0) this.integrationStep = integrationStep
        return this
    }

    /**
     * Changes the name of the integrand and returns this integral.
     *
     * @param integrandName New integrand name.
     * @return This integral.
     */
    fun integrandName(integrandName: String?): MonteCarloIntegral {
        this.integrandName = integrandName ?: "f(x)"
        return this
    }

    /**
     * Calculates the value of the integrand for the current value of
     * the variable. Throws an exception if it detects a non-finite
     * value, but the discontinuities are not always detected, since the
     * integration step is not infinitely small.
     *
     * @param variable Current value of the variable.
     * @return The current integrand value if it is finite.
     * @throws ImproperIntegralException if the integrand value is not
     *     finite or undefined or cannot be calculated.
     */
    @Throws(ImproperIntegralException::class)
    protected fun calculateValue(variable: Double): Double {
        val currentValue: Double
        try {
            currentValue = integrand.setVariable("x", variable).evaluate().also {
                if (!it.isFinite()) {
                    throw ImproperIntegralException(
                        "Encountered improper integral (Type II): the function value is not finite here.",
                        ImproperIntegralException.Type.SINGULARITY
                    )
                }
            }
        } catch (e: ArithmeticException) {
            throw ImproperIntegralException(
                "Encountered improper integral (Type II): the function value is not finite here.",
                e, ImproperIntegralException.Type.SINGULARITY
            )
        }
        return currentValue
    }

    protected fun calculateMinMax() {
        val left = if (isReversed) rightLimit else leftLimit
        var right = if (isReversed) leftLimit else rightLimit
        // Estimates the min and max value of the function.
        minValue = calculateValue(leftLimit)
        maxValue = minValue
        for (variable in left..right step integrationStep) {
            calculateValue(variable).also {
                if (minValue > it) minValue = it
                if (maxValue < it) maxValue = it
            }
        }
    }

    override fun toString(): String {
        return "Monte-Carlo integral of $integrandName from $leftLimit to $rightLimit"
    }
}