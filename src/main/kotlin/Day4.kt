class Day4(private val lines: List<String>) {

    fun part1(): Int {
        var count = 0
        for (i in lines.indices) {
            for (j in lines[i].indices) {
                if (lines[i][j] != 'X') continue
                count += Direction.entries.count { it.matches(i, j, "XMAS") }
            }
        }
        return count
    }

    fun part2(): Int {
        var count = 0
        for (i in lines.indices) {
            for (j in lines[i].indices) {
                if (!Direction.BottomRight.matches(i, j)) continue
                if (!Direction.BottomLeft.matches(i, j + 2)) continue
                count++
            }
        }
        return count
    }

    private fun Direction.matches(i: Int, j: Int): Boolean {
        val term = when (lines[i][j]) {
            'M' -> "MAS"
            'S' -> "SAM"
            else -> return false
        }
        return matches(i, j, term)
    }

    private fun Direction.matches(i: Int, j: Int, term: String): Boolean {
        require(term.isNotEmpty()) { "term is empty." }
        if (i + vertical * (term.length - 1) !in lines.indices) return false
        if (j + horizontal * (term.length - 1) !in lines[0].indices) return false
        return generateSequence(i) { it + vertical }
            .zip(generateSequence(j) { it + horizontal }) { v, h -> lines[v][h] }
            .zip(term.asSequence()) { l, r -> l == r }
            .all { it }
    }

    private enum class Direction(val horizontal: Int, val vertical: Int) {

        Left(horizontal = 1, vertical = 0),
        Top(horizontal = 0, vertical = -1),
        Right(horizontal = -1, vertical = 0),
        Bottom(horizontal = 0, vertical = 1),
        TopLeft(horizontal = -1, vertical = -1),
        TopRight(horizontal = 1, vertical = -1),
        BottomLeft(horizontal = -1, vertical = 1),
        BottomRight(horizontal = 1, vertical = 1),
    }
}
