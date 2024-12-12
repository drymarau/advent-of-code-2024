class Day2Test : DayTest(
    path = "day2-input.txt",
    part1 = 2,
    part2 = 4,
    factory = {
        val reports = buildList<List<Int>> {
            val regex = Regex("\\s+")
            it.forEach {
                this += it.splitToSequence(regex)
                    .map(String::toInt)
                    .toList()
            }
        }
        Day2(reports)
    },
)
