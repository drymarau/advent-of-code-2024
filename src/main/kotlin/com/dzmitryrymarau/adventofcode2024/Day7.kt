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

import com.dzmitryrymarau.adventofcode2024.util.concat
import com.dzmitryrymarau.adventofcode2024.util.head
import com.dzmitryrymarau.adventofcode2024.util.tail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.atomic.AtomicLong

class Day7(private val lines: Sequence<String>) : Day {

    override fun part1(): Number = sumOf(Long::plus, Long::times)

    override fun part2(): Number = sumOf(Long::plus, Long::times, Long::concat)

    private fun sumOf(vararg operators: Operator): Long {
        val result = AtomicLong()
        runBlocking(Dispatchers.Default) {
            for ((value, values) in lines.map(::Equation)) {
                launch {
                    if (valid(value, values.head, values.tail, *operators)) {
                        result.addAndGet(value)
                    }
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
