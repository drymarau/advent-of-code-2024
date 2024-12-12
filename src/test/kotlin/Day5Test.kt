import okio.FileSystem
import okio.Path.Companion.toPath

class Day5Test : DayTest(part1 = 143, part2 = 123) {

    override fun create(): Day {
        val rules = mutableMapOf<Int, Set<Int>>()
        val updates = mutableListOf<List<Int>>()
        FileSystem.RESOURCES.readByUtf8Line("day5-input.txt".toPath()) {
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
        return Day5(rules, updates)
    }
}
