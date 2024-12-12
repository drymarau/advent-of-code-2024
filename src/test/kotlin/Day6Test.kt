class Day6Test : DayTest(
    path = "day6-input.txt",
    part1 = 41,
    part2 = 6,
    factory = {
        var row = -1
        var column = -1
        val map = mutableListOf<List<Char>>()
        val directions = "<^>v"
        it.forEachIndexed { i, line ->
            map += line.map { it }
            directions.forEach { c ->
                val index = line.indexOf(c)
                if (index >= 0) {
                    row = i
                    column = index
                }
            }
        }
        Day6(map, row, column)
    },
)
