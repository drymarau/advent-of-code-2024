class Day5Test : DayTest(
    path = "day5-input.txt",
    part1 = 143,
    part2 = 123,
    factory = {
        val rules = mutableMapOf<Int, Set<Int>>()
        val updates = mutableListOf<List<Int>>()
        it.forEach {
            when {
                '|' in it -> {
                    val (beforePage, page) = it.splitToSequence('|', limit = 2)
                        .map(String::toInt)
                        .toList()
                    val set = rules.getOrPut(beforePage, ::mutableSetOf)
                    rules.put(beforePage, set + page)
                }

                ',' in it -> {
                    updates += it.splitToSequence(',')
                        .map(String::toInt)
                        .toList()
                }
            }
        }
        Day5(rules, updates)
    },
)
