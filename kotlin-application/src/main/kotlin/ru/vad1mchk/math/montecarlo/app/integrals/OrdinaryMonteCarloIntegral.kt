package ru.vad1mchk.math.montecarlo.app.integrals

import net.objecthunter.exp4j.Expression
import ru.vad1mchk.math.montecarlo.app.util.Configuration
import ru.vad1mchk.math.montecarlo.core.util.RangeExtensions.random
import ru.vad1mchk.math.montecarlo.core.util.RangeExtensions.step

/**
 * Integral class for the geometrical Monte-Carlo method.
 *
 * The main point of this approach is to calculate the function of N
 * values randomly distributed between [leftLimit] and [rightLimit]. The
 * integral would approach ([rightLimit] - [leftLimit])/N multiplied
 * by the sum of all functions of these values when N is approaching
 * infinity since the randoms are uniformly distributed.
 *
 * @constructor Creates a new integral using the following parameters.
 * @param leftLimit
 * @param rightLimit
 * @param integrand
 */
class OrdinaryMonteCarloIntegral(
    leftLimit: Double,
    rightLimit: Double,
    integrand: Expression
) : MonteCarloIntegral(leftLimit, rightLimit, integrand) {
    override fun integrate(): Double {
        // Checks that the borders are not equal or reversed.
        if (isZero) return 0.0
        var left = if (isReversed) rightLimit else leftLimit
        var right = if (isReversed) leftLimit else rightLimit
        // Checks that there is no singularities/discontinuities. If any is detected, throws exception.
        minValue = calculateValue(left)
        maxValue = minValue
        for (variable in left..right step integrationStep) {
            calculateValue(variable).also {
                if (minValue > it) minValue = it
                if (maxValue < it) maxValue = it
            }
        }
        // Gets the property (count of dots to show, list of shown dots)
        val dotsToShow = Configuration.dotsToShowProperty.get()
        val shownDots = Configuration.shownDotsProperty.get()
        // Calculates the sum of the function values.
        var sumOfFunctions = 0.0
        for (i in 0 until dotsToDrop) {
            val variable = (left..right).random()
            val currentValue = calculateValue(variable)
            sumOfFunctions += calculateValue(variable)
            if (i < dotsToShow) shownDots.add(Pair(variable, currentValue))
        }
        return (rightLimit - leftLimit) * sumOfFunctions / dotsToDrop
    }

    override fun toString(): String {
        return "Ordinary " + super.toString()
    }
}