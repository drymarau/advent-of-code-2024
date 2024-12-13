import util.Direction
import util.Position
import util.contains
import util.get
import util.getOrNull
import util.positions

class Day12(private val lines: List<String>) : Day {

    constructor(lines: Sequence<String>) : this(lines.toList())

    private val directions = sequenceOf(
        Direction.Left,
        Direction.Top,
        Direction.Right,
        Direction.Bottom,
    )

    override fun part1(): Number = sumOf { it.size * it.perimeter() }

    override fun part2(): Number = sumOf { it.size * it.sides() }

    private inline fun sumOf(selector: (Set<Position>) -> Int): Int {
        var sum = 0
        val visited = mutableSetOf<Position>()
        for (position in lines.positions.filterNot(visited::contains)) {
            val plot = buildSet { lines.plot(position, this) }
            visited += plot
            sum += selector(plot)
        }
        return sum
    }

    private fun List<String>.plot(position: Position, region: MutableSet<Position>, plant: Char = this[position]) {
        if (position !in this) return
        if (position in region) return
        if (plant == this[position]) {
            region += position
            for (direction in directions) plot(position + direction, region, plant)
        }
    }

    private fun Set<Position>.perimeter() = asSequence().sumOf { lines.perimeter(it) }

    private fun List<String>.perimeter(position: Position) = directions.sumOf { perimeter(position, it) }

    private fun List<String>.perimeter(position: Position, direction: Direction) =
        if (this[position] != getOrNull(position + direction)) 1 else 0

    private fun Set<Position>.sides(): Int {
        val min = Position(minOf(Position::row), minOf(Position::column))
        val max = Position(maxOf(Position::row), maxOf(Position::column))
        return directions.sumOf { sides(min, max, it) }
    }

    private fun Set<Position>.sides(min: Position, max: Position, direction: Direction): Int {
        val outerRange = when (direction) {
            Direction.Left -> max.column downTo min.column
            Direction.Top -> max.row downTo min.row
            Direction.Right -> min.column..max.column
            Direction.Bottom -> min.row..max.row
            else -> throw IllegalArgumentException("Invalid direction: $direction.")
        }
        val innerRange = when (direction) {
            Direction.Left, Direction.Right -> min.row..max.row
            Direction.Top, Direction.Bottom -> min.column..max.column
            else -> throw IllegalArgumentException("Invalid direction: $direction.")
        }
        var sides = 0
        for (o in outerRange) {
            var sideLength = 0
            for (i in innerRange) {
                val position = when (direction) {
                    Direction.Left, Direction.Right -> Position(i, o)
                    Direction.Top, Direction.Bottom -> Position(o, i)
                    else -> throw IllegalArgumentException("Invalid direction: $direction.")
                }
                if (position in this && position + direction !in this) {
                    sideLength += 1
                } else {
                    if (sideLength > 0) sides += 1
                    sideLength = 0
                }
            }
            if (sideLength > 0) sides += 1
        }
        return sides
    }
}
