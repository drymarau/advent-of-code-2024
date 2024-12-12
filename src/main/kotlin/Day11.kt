import Day11.Stones.Many
import Day11.Stones.One
import Day11.Stones.Zero
import util.length
import kotlin.math.pow

class Day11(private val input: String) : Day {

    override fun part1(): Number = blink(25)

    override fun part2(): Number = blink(75)

    private fun blink(times: Int): Long {
        val cache = mutableMapOf<Stone, Long>()
        return input.splitToSequence(' ')
            .map(String::toLong)
            .map { Stone(it, times) }
            .sumOf { blink(it, cache) }
    }

    private fun blink(stone: Stone, cache: MutableMap<Stone, Long>): Long =
        stone.blink().sumOf { cache.getOrPut(it) { blink(it, cache) } }

    private data class Stone(val value: Long, val times: Int) {

        fun blink(): Stones {
            if (times == 0) return Stones()
            val times = times - 1
            if (value == 0L) return Stones(Stone(1L, times))
            val length = value.length
            if (length % 2 == 0) {
                val divisor = 10.0.pow(length / 2).toLong()
                return Stones(Stone(value / divisor, times), Stone(value % divisor, times))
            }
            return Stones(Stone(value * 2024, times))
        }
    }

    private sealed interface Stones {

        data object Zero : Stones

        @JvmInline
        value class One(val stone: Stone) : Stones

        @JvmInline
        value class Many(val stones: List<Stone>) : Stones

        companion object {

            operator fun invoke(vararg stones: Stone) = when (stones.size) {
                0 -> Zero
                1 -> One(stones[0])
                else -> Many(stones.asList())
            }
        }
    }

    private inline fun Stones.sumOf(selector: (Stone) -> Long): Long = when (this) {
        is Zero -> 1L
        is One -> selector(stone)
        is Many -> stones.sumOf(selector)
    }
}
