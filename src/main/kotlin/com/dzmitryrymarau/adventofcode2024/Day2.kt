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
import kotlin.math.sign

class Day2(lines: Sequence<String>) : Day {

    private val range = 1..3
    private val reports = lines.map {
        it.splitToSequence(WhitespaceRegex)
            .map(String::toInt)
            .toList()
    }

    override fun part1(): Number = reports.count(::safe)

    override fun part2(): Number = reports.count(::lenientSafe)

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
