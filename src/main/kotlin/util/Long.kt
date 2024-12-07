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
