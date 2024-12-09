class Day8(private val input: List<String>) {

    private val map = buildMap {
        for (row in input.indices) {
            for (column in input[row].indices) {
                when (val c = input[row][column]) {
                    '.' -> continue
                    else -> this[c] = (this[c] ?: listOf<Position>()) + Position(row, column)
                }
            }
        }
    }

    fun part1(): Int = calculate()

    fun part2(): Int = calculate(resonant = true)

    private fun calculate(resonant: Boolean = false): Int {
        val antiNodes = buildSet {
            tailrec fun iterate(position: Position, distance: Distance, operation: Position.(Distance) -> Position) {
                val antiNode = position.operation(distance)
                if (antiNode !in input) return
                this += antiNode
                if (resonant) iterate(antiNode, distance, operation)
            }
            for ((_, positions) in map) {
                for (i in 0..positions.lastIndex - 1) {
                    for (j in i + 1..positions.lastIndex) {
                        if (resonant) {
                            this += positions[i]
                            this += positions[j]
                        }
                        val distance = positions[i] - positions[j]
                        iterate(positions[i], distance, Position::plus)
                        iterate(positions[j], distance, Position::minus)
                    }
                }
            }
        }
        return antiNodes.size
    }

    private data class Position(val row: Int, val column: Int) {

        operator fun plus(distance: Distance) = Position(row = row + distance.row, column = column + distance.column)

        operator fun minus(distance: Distance) = Position(row = row - distance.row, column = column - distance.column)

        operator fun minus(other: Position) = Distance(row = row - other.row, column = column - other.column)
    }

    private data class Distance(val row: Int, val column: Int)

    private operator fun List<String>.contains(position: Position) =
        position.row in indices && position.column in this[position.row].indices
}
