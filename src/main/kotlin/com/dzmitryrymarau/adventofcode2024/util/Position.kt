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

data class Position(val row: Int, val column: Int) : Comparable<Position> {

    operator fun plus(distance: Distance) = Position(
        row = row + distance.row,
        column = column + distance.column
    )

    operator fun minus(distance: Distance) = Position(
        row = row - distance.row,
        column = column - distance.column
    )

    operator fun plus(direction: Direction) = plus(direction.distance)

    operator fun minus(direction: Direction) = minus(direction.distance)

    operator fun minus(other: Position) = Distance(
        row = row - other.row,
        column = column - other.column,
    )

    override fun compareTo(other: Position): Int {
        val rowDiff = row - other.row
        if (rowDiff != 0) return rowDiff
        return column - other.column
    }
}
