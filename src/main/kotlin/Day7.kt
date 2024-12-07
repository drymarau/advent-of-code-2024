import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import util.concat
import util.head
import util.tail
import java.util.concurrent.atomic.AtomicLong

class Day7(private val lines: Sequence<String>) {

    fun part1(): Long = sumOf(Long::plus, Long::times)

    fun part2(): Long = sumOf(Long::plus, Long::times, Long::concat)

    private fun sumOf(vararg operators: Operator): Long {
        val result = AtomicLong()
        runBlocking(Dispatchers.Default) {
            for ((value, values) in lines.map(::Equation)) launch {
                if (valid(value, values.head, values.tail, *operators)) {
                    result.addAndGet(value)
                }
            }
        }
        return result.get()
    }

    private fun valid(goal: Long, current: Long, tail: List<Long>, vararg operators: Operator): Boolean {
        if (current > goal) return false
        if (tail.isEmpty()) return current == goal
        return operators.any { valid(goal, current.it(tail.head), tail.tail, *operators) }
    }

    private data class Equation(val value: Long, val values: List<Long>) {

        constructor(line: String) : this(line.split(':', limit = 2))

        constructor(parts: List<String>) : this(
            value = parts[0].toLong(),
            values = parts[1].splitToSequence(' ')
                .filter(String::isNotBlank)
                .map(String::toLong)
                .toList(),
        )
    }
}

private typealias Operator = Long.(Long) -> Long
