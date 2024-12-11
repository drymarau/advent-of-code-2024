package util

import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

fun Long.concat(other: Long) = other + this * when {
    other < 10 -> 10
    other < 100 -> 100
    other < 1000 -> 1000
    else -> 10.0.pow(floor(log10(other.toDouble())) + 1).toLong()
}

val Long.length: Int get() = length(this)

private tailrec fun length(n: Long, length: Int = 1): Int = when {
    n < 10 -> length
    else -> length(n / 10, length + 1)
}
