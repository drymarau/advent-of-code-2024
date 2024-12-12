import util.Distance
import util.Position
import util.contains
import util.get
import util.nextDigit
import util.positions

class Day10(private val lines: List<String>) : Day {

    constructor(lines: Sequence<String>) : this(lines.toList())

    override fun part1(): Number = count(::mutableSetOf)

    override fun part2(): Number = count(::mutableListOf)

    private fun count(block: () -> MutableCollection<Position>): Int {
        var count = 0
        for (position in lines.positions.filter { lines[it] == '0' }) {
            val collection = block()
            count(position, block = collection::add)
            count += collection.size
        }
        return count
    }

    private fun count(position: Position, current: Char = lines[position], block: (Position) -> Unit) {
        if (lines[position] != current) return
        if (current == '9') {
            block(position)
            return
        }
        val current = current.nextDigit()
        for (distance in distances) {
            val position = position + distance
            if (position !in lines) continue
            count(position, current, block)
        }
    }

    private val distances = sequenceOf(Distance.row(1), Distance.row(-1), Distance.column(1), Distance.column(-1))
}
