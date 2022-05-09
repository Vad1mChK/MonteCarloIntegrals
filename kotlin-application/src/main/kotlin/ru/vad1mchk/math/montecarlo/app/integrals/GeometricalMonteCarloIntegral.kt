package ru.vad1mchk.math.montecarlo.app.integrals

import net.objecthunter.exp4j.Expression
import ru.vad1mchk.math.montecarlo.app.util.Configuration
import ru.vad1mchk.math.montecarlo.core.util.RangeExtensions.random
import ru.vad1mchk.math.montecarlo.core.util.RangeExtensions.step

/**
 * Integral class for the geometrical Monte-Carlo method.
 *
 * The main point of this approach is to enclose the curve within a
 * rectangle, which spans from [leftLimit] to [rightLimit] horizontally
 * and from min to max value of the function vertically.
 *
 * Then `N` dots with random coordinates are dropped within the
 * rectangle, and only `K` of them are under the curve. The enclosed
 * area under the curve would approach `S = S0*K/N`, where `S0` is the
 * area of said rectangle.
 *
 * The resulting area would be the sum of the enclosed area
 * and the area of the "bottom rectangle" which has the area
 * `([rightLimit]-[leftLimit]) * minValue`.
 *
 * @constructor Creates a new integral using the following parameters.
 * @param leftLimit Left limit of integration domain.
 * @param rightLimit Right limit of integration domain.
 * @param integrand Function to integrate.
 */
class GeometricalMonteCarloIntegral(
    leftLimit: Double,
    rightLimit: Double,
    integrand: Expression
) : MonteCarloIntegral(leftLimit, rightLimit, integrand) {
    override fun integrate(): Double {
        // Checks that the borders are not equal or reversed.
        if (isZero) return 0.0
        var left = if (isReversed) rightLimit else leftLimit
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
        // Calculate the area of the rectangle
        val bottomArea = (rightLimit - leftLimit) * minValue
        val enclosedArea = (rightLimit - leftLimit) * (maxValue - minValue)
        // Gets the property (count of dots to show, list of shown dots)
        val dotsToShow = Configuration.dotsToShowProperty.get()
        val shownDots = Configuration.shownDotsProperty.get()
        // Drop the dots and find how many of them are under the curve.
        var dotsUnderCurve = 0
        for (i in 0 until dotsToDrop) {
            val dot = Pair((left..right).random(), (minValue..maxValue).random())
            if (i < dotsToShow) {
                shownDots.add(dot)
            }
            if (dot.second < calculateValue(dot.first)) {
                dotsUnderCurve++
            }
        }
        return enclosedArea * (dotsUnderCurve.toDouble() / dotsToDrop.toDouble()) + bottomArea
    }

    override fun toString(): String {
        return "Geometrical " + super.toString()
    }
}