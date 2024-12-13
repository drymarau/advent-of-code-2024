import util.WhitespaceRegex
import kotlin.math.abs

class Day1(lines: Sequence<String>) : Day {

    private val left: Sequence<Int>
    private val right: Sequence<Int>

    init {
        val l = mutableListOf<Int>()
        val r = mutableListOf<Int>()
        for (line in lines) {
            val parts = line.splitToSequence(WhitespaceRegex, limit = 2)
                .map(String::toInt)
                .toList()
            l += parts[0]
            r += parts[1]
        }
        left = l.asSequence()
        right = r.asSequence()
    }

    override fun part1(): Number = left.sorted()
        .zip(right.sorted())
        .sumOf { (l, r) -> abs(l - r) }

    override fun part2(): Number {
        val frequency = right.groupingBy { it }
            .eachCount()
            .withDefault { 0 }
        return left.sumOf { it * frequency.getValue(it) }
    }
}
