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

import com.dzmitryrymarau.adventofcode2024.util.WhitespaceRegex
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
