package util

val <T> List<T>.head get() = first()

val <T> List<T>.tail get() = slice(1..lastIndex)

val List<String>.positions: Sequence<Position>
    get() = sequence {
        for (row in indices) {
            for (column in get(row).indices) {
                yield(Position(row, column))
            }
        }
    }

operator fun List<String>.get(position: Position) = this[position.row][position.column]

fun List<String>.getOrNull(position: Position): Char? {
    val row = getOrNull(position.row) ?: return null
    return row.getOrNull(position.column)
}

operator fun List<String>.contains(position: Position) =
    position.row in indices && position.column in this[position.row].indices
