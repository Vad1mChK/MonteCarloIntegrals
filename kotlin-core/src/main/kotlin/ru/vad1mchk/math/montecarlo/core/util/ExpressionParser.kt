package ru.vad1mchk.math.montecarlo.core.util

import ru.vad1mchk.math.montecarlo.core.exceptions.InvalidExpressionException
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import net.objecthunter.exp4j.function.Functions
import net.objecthunter.exp4j.function.Function
import net.objecthunter.exp4j.tokenizer.UnknownFunctionOrVariableException
import java.lang.Math.*

/**
 * Object to parse the expressions more conveniently.
 */
object ExpressionParser {
    /**
     * Parses the expression string with the specified variables and returns an expression as a result.
     * @param expressionString String to extract the expressions from.
     * @param variables Names of the variables.
     * @return An expression which you can apply to one or more arguments.
     * @throws InvalidExpressionException if the expression contains undefined functions or variables.
     *
     */
    @Throws(UnknownFunctionOrVariableException::class)
    fun parse(expressionString: String, vararg variables: String): Expression {
        // Some useful function aliases, e.g. refer to "tan" as "tg"
        val functionAliases = arrayOf<Function>(
            object: Function("tg") {
                override fun apply(vararg args: Double): Double {
                    return Functions.getBuiltinFunction("tan").apply(*args)
                }
            },
            object: Function("ctg") {
                override fun apply(vararg args: Double): Double {
                    return Functions.getBuiltinFunction("cot").apply(*args)
                }
            },
            object: Function("ln") {
                override fun apply(vararg args: Double): Double {
                    return Functions.getBuiltinFunction("log").apply(*args)
                }
            },
            object: Function("lg") {
                override fun apply(vararg args: Double): Double {
                    return Functions.getBuiltinFunction("log10").apply(*args)
                }
            },
            object: Function("arcsin") {
                override fun apply(vararg args: Double): Double {
                    return Functions.getBuiltinFunction("asin").apply(*args)
                }
            },
            object: Function("arccos") {
                override fun apply(vararg args: Double): Double {
                    return Functions.getBuiltinFunction("acos").apply(*args)
                }
            },
            object: Function("arctan") {
                override fun apply(vararg args: Double): Double {
                    return Functions.getBuiltinFunction("atan").apply(*args)
                }
            },
            object: Function("arctg") {
                override fun apply(vararg args: Double): Double {
                    return Functions.getBuiltinFunction("atan").apply(*args)
                }
            },
            object: Function("acot") {
                override fun apply(vararg args: Double): Double {
                    return PI/2 - Functions.getBuiltinFunction("atan").apply(*args)
                }
            },
            object: Function("arccot") {
                override fun apply(vararg args: Double): Double {
                    return PI/2 - Functions.getBuiltinFunction("atan").apply(*args)
                }
            },
            object: Function("arcctg") {
                override fun apply(vararg args: Double): Double {
                    return PI/2 - Functions.getBuiltinFunction("atan").apply(*args)
                }
            },
            object: Function("sh") {
                override fun apply(vararg args: Double): Double {
                    return Functions.getBuiltinFunction("sinh").apply(*args)
                }
            },
            object: Function("ch") {
                override fun apply(vararg args: Double): Double {
                    return Functions.getBuiltinFunction("cosh").apply(*args)
                }
            },
            object: Function("th") {
                override fun apply(vararg args: Double): Double {
                    return Functions.getBuiltinFunction("tanh").apply(*args)
                }
            },
            object: Function("coth") {
                override fun apply(vararg args: Double): Double {
                    val tanh = Functions.getBuiltinFunction("tanh").apply(*args)
                    if (tanh == 0.0) {
                        throw ArithmeticException("Division by zero in hyperbolic cotangent!")
                    }
                    return 1.0/tanh
                }
            },
            object: Function("cth") {
                override fun apply(vararg args: Double): Double {
                    val th = Functions.getBuiltinFunction("tanh").apply(*args)
                    if (th == 0.0) {
                        throw ArithmeticException("Division by zero in hyperbolic cotangent!")
                    }
                    return 1.0/th
                }
            },
            object: Function("arsinh") {
                override fun apply(vararg args: Double): Double {
                    return sinh(log(args[0]+sqrt(pow(args[0],2.0)+1.0)))
                }
            },
            object: Function("arsh") {
                override fun apply(vararg args: Double): Double {
                    return sinh(log(args[0]+sqrt(pow(args[0],2.0)+1.0)))
                }
            },
            object: Function("arcosh") {
                override fun apply(vararg args: Double): Double {
                    return sinh(log(args[0]+sqrt(pow(args[0],2.0)-1.0)))
                }
            },
            object: Function("arch") {
                override fun apply(vararg args: Double): Double {
                    return sinh(log(args[0]+sqrt(pow(args[0],2.0)-1.0)))
                }
            },
            object: Function("artanh") {
                override fun apply(vararg args: Double): Double {
                    return 0.5*sinh(log((1+args[0])/(1-args[0])))
                }
            },
            object: Function("arth") {
                override fun apply(vararg args: Double): Double {
                    return 0.5*sinh(log((1+args[0])/(1-args[0])))
                }
            },
            object: Function("arcoth") {
                override fun apply(vararg args: Double): Double {
                    return 0.5*sinh(log((args[0]+1)/(args[0]-1)))
                }
            },
            object: Function("arcth") {
                override fun apply(vararg args: Double): Double {
                    return 0.5*sinh(log((args[0]+1)/(args[0]-1)))
                }
            }
        )
        try {
            return ExpressionBuilder(expressionString).apply {
                functions(*functionAliases)
                variables(*variables)
            }.build()
        } catch (e: Exception) {
            if (e is IllegalArgumentException || e is ArithmeticException) {
                throw InvalidExpressionException(e)
            }
            throw e
        }
    }
}