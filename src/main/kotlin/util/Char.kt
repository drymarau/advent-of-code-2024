package util

fun Char.nextDigit(): Char {
    require(code in ValidCodes) { "$this is not in '0'..'8'" }
    return Char(code + 1)
}

private val ValidCodes = 48..56
