/*
 * Copyright 2024 Dzmitry Rymarau
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dzmitryrymarau.adventofcode2024

import com.dzmitryrymarau.adventofcode2024.util.length
import kotlin.math.pow

class Day11(private val input: String) : Day {

    constructor(lines: Sequence<String>) : this(lines.joinToString())

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
        is Stones.Zero -> 1L
        is Stones.One -> selector(stone)
        is Stones.Many -> stones.sumOf(selector)
    }
}
