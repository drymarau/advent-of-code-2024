import util.Distance
import util.Position
import util.contains
import util.get
import util.positions

class Day8(private val lines: List<String>) : Day {

    constructor(lines: Sequence<String>) : this(lines.toList())

    private val map = buildMap {
        for (position in lines.positions) {
            when (val c = lines[position]) {
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
                if (antiNode !in lines) return
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
