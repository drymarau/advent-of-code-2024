import okio.FileSystem
import okio.Path.Companion.toPath

class Day2Test : DayTest(part1 = 2, part2 = 4) {

    override fun create(): Day {
        val reports = buildList<List<Int>> {
            val regex = Regex("\\s+")
            FileSystem.RESOURCES.readByUtf8Line("day2-input.txt".toPath()) {
                this += it.splitToSequence(regex)
                    .map(String::toInt)
                    .toList()
            }
        }
        return Day2(reports)
    }
}
