class Day1Test : DayTest(
    path = "day1-input.txt",
    part1 = 11,
    part2 = 31,
    factory = {
        val l = mutableListOf<Int>()
        val r = mutableListOf<Int>()
        val regex = Regex("\\s+")
        it.forEach {
            val parts = it.splitToSequence(regex)
                .map(String::toInt)
                .toList()
            l += parts[0]
            r += parts[1]
        }
        Day1(l.asSequence(), r.asSequence())
    },
)
