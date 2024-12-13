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
package com.dzmitryrymarau.adventofcode2024.util

import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

fun Long.concat(other: Long) = other + this * when {
    other < 10 -> 10
    other < 100 -> 100
    other < 1000 -> 1000
    else -> 10.0.pow(floor(log10(other.toDouble())) + 1).toLong()
}

val Long.length: Int get() = length(this)

private tailrec fun length(n: Long, length: Int = 1): Int = when {
    n < 10 -> length
    else -> length(n / 10, length + 1)
}
