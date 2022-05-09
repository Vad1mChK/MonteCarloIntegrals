import ru.vad1mchk.math.montecarlo.app.integrals.GeometricalMonteCarloIntegral
import ru.vad1mchk.math.montecarlo.app.integrals.OrdinaryMonteCarloIntegral
import ru.vad1mchk.math.montecarlo.core.util.ExpressionParser

class Test {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            println(OrdinaryMonteCarloIntegral(10.0,5.0, ExpressionParser.parse("x","x")).integrate())
            println(OrdinaryMonteCarloIntegral(5.0,10.0, ExpressionParser.parse("x","x")).integrate())
            println(GeometricalMonteCarloIntegral(10.0,5.0, ExpressionParser.parse("x","x")).integrate())
            println(GeometricalMonteCarloIntegral(5.0,10.0, ExpressionParser.parse("x","x")).integrate())
        }
    }
}