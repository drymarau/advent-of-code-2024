import kotlin.math.abs
import kotlin.math.sign

class Day2(private val reports: List<List<Int>>) {

    private val range = 1..3

    fun part1(): Int = reports.count(::safe)

    fun part2(): Int = reports.count(::lenientSafe)

    private fun safe(levels: List<Int>): Boolean {
        var sign = 0
        for (i in 1..levels.lastIndex) {
            val diff = levels[i] - levels[i - 1]
            if (sign == 0) sign = diff.sign
            if (sign == 0) return false
            if (sign != diff.sign) return false
            if (abs(diff) !in range) return false
        }
        return true
    }

    private fun lenientSafe(levels: List<Int>) = generateSequence(0, Int::inc)
        .take(levels.size)
        .map { levels.filterIndexed { index, _ -> index != it } }
        .any(::safe)
}
