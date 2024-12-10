package util

@JvmInline
value class Input(private val value: List<String>) {

    val positions: Sequence<Position>
        get() = sequence {
            for (row in value.indices) {
                for (column in value[row].indices) {
                    yield(Position(row, column))
                }
            }
        }

    operator fun get(position: Position) = value[position.row][position.column]

    operator fun contains(position: Position) =
        position.row in value.indices && position.column in value[position.row].indices
}
