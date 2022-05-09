package ru.vad1mchk.math.montecarlo.core.exceptions

/**
 * Exception thrown if the expression cannot be parsed correctly.
 */
class InvalidExpressionException: RuntimeException {
    constructor(): super()
    constructor(message: String): super(message)
    constructor(cause: Throwable): super(cause)
    constructor(message: String, cause: Throwable): super(message, cause)
}