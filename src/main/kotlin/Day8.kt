import util.Distance
import util.Input
import util.Position

class Day8(private val input: Input) : Day {

    private val map = buildMap {
        for (position in input.positions) {
            when (val c = input[position]) {
                '.' -> continue
                else -> this[c] = (this[c] ?: listOf<Position>()) + position
            }
        }
    }

    override fun part1(): Number = calculate()

    override fun part2(): Number = calculate(resonant = true)

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
}
