package ru.vad1mchk.math.montecarlo.core.exceptions

/**
 * Exception thrown when the integral cannot be calculated, e.g. a
 * discontinuity has been detected.
 */
open class IntegralException : RuntimeException {
    /**
     * Constructs a new integral exception with `null` as its detail
     * message.
     */
    constructor() : super()

    /**
     * Constructs a new integral exception with the specified detail
     * message.
     */
    constructor(message: String) : super(message)

    /**
     * Constructs a new integral exception with the specified cause and
     * a detail message of (`cause==null ? null : cause.toString()`)
     * (which typically contains the class and detail message of cause).
     */
    constructor(cause: Throwable) : super(cause)

    /**
     * Constructs a new integral exception with the specified detail
     * message and cause.
     */
    constructor(message: String, cause: Throwable) : super(message, cause)
}