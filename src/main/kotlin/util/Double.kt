package util

val Double.whole: Boolean get() = this % 1.0 == 0.0
