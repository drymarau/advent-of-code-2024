package util

val <T> List<T>.head get() = first()

val <T> List<T>.tail get() = slice(1..lastIndex)
