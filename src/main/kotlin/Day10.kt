import util.Distance
import util.Input
import util.Position
import util.nextDigit

class Day10(private val input: Input) {

    fun part1(): Int = count(::mutableSetOf)

    fun part2(): Int = count(::mutableListOf)

    private fun count(block: () -> MutableCollection<Position>): Int {
        var count = 0
        for (position in input.positions.filter { input[it] == '0' }) {
            val collection = block()
            collection.count(position)
            count += collection.size
        }
        return count
    }

    private fun MutableCollection<Position>.count(position: Position, current: Char = '0') {
        if (input[position] != current) return
        if (current == '9') {
            add(position)
            return
        }
        val current = current.nextDigit()
        for (distance in distances) {
            val position = position + distance
            if (position !in input) continue
            count(position, current)
        }
    }

    private val distances = sequenceOf(Distance.row(1), Distance.row(-1), Distance.column(1), Distance.column(-1))
}
