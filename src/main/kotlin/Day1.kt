import kotlin.math.abs

class Day1(private val left: Sequence<Int>, private val right: Sequence<Int>) : Day {

    override fun part1(): Number = left.sorted()
        .zip(right.sorted())
        .map { (l, r) -> abs(l - r) }
        .sum()

    override fun part2(): Number {
        val frequency = right.groupingBy { it }
            .eachCount()
            .withDefault { 0 }
        return left.fold(0) { acc, n -> acc + n * frequency.getValue(n) }
    }
}
