package ru.vad1mchk.math.montecarlo.core.exceptions

/**
 * Exception thrown when the integral is improper, i.e. if its intervals
 * approach infinity or if discontinuities are detected within the
 * integration interval.
 */
class ImproperIntegralException : IntegralException {
    /** Type of the improper integral. */
    enum class Type {
        /**
         * When one or both of the endpoints of the integration interval
         * are approaching infinity.
         */
        ARBITRARY_DOMAIN,

        /**
         * When the value of the integrand is infinite or undefined at
         * this point.
         */
        SINGULARITY,

        /**
         * When both the endpoints of the interval and the value of the
         * integrand are not finite.
         */
        BOTH
    }

    private var type: Type? = null

    /**
     * Constructs a new integral exception with `null` as its detail
     * message. The type is set to null by default.
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

    /**
     * Constructs a new integral exception with `null` as its detail
     * message and the specified type.
     */
    constructor(type: Type) : super() {
        this.type = type
    }

    /**
     * Constructs a new integral exception with the specified detail
     * message and type.
     */
    constructor(message: String, type: Type) : super(message) {
        this.type = type
    }

    /**
     * Constructs a new integral exception with the specified cause
     * and type and a detail message of (`cause==null ? null :
     * cause.toString()`) (which typically contains the class and detail
     * message of cause).
     */
    constructor(cause: Throwable, type: Type) : super(cause) {
        this.type = type
    }

    /**
     * Constructs a new integral exception with the specified detail
     * message, cause, and type.
     */
    constructor(message: String, cause: Throwable, type: Type) : super(message, cause) {
        this.type = type
    }
}