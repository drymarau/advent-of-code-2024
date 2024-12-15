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

enum class Direction(val distance: Distance) {

    Top(row = -1, column = 0),
    Left(row = 0, column = -1),
    Right(row = 0, column = 1),
    Bottom(row = 1, column = 0),
    TopLeft(row = -1, column = -1),
    TopRight(row = -1, column = 1),
    BottomLeft(row = 1, column = -1),
    BottomRight(row = 1, column = 1),
    ;

    constructor(row: Int, column: Int) : this(Distance(row, column))

    override fun toString(): String = when (this) {
        Top -> "↑"
        Left -> "←"
        Right -> "→"
        Bottom -> "↓"
        TopLeft -> "↖"
        TopRight -> "↗"
        BottomLeft -> "↙"
        BottomRight -> "↘"
    }

    companion object {

        fun from(c: Char): Direction = when (c) {
            '↑', '^' -> Top
            '←', '<' -> Left
            '→', '>' -> Right
            '↓', 'v' -> Bottom
            '↖' -> TopLeft
            '↗' -> TopRight
            '↙' -> BottomLeft
            '↘' -> BottomRight
            else -> throw IllegalArgumentException("Invalid character '$c'.")
        }
    }
}
