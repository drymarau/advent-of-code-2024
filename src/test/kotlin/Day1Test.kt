import okio.FileSystem
import okio.Path.Companion.toPath

class Day1Test : DayTest(part1 = 11, part2 = 31) {

    override fun create(): Day {
        val l = mutableListOf<Int>()
        val r = mutableListOf<Int>()
        val regex = Regex("\\s+")
        FileSystem.RESOURCES.readByUtf8Line("day1-input.txt".toPath()) {
            val parts = it.splitToSequence(regex)
                .map(String::toInt)
                .toList()
            l += parts[0]
            r += parts[1]
        }
        return Day1(l.asSequence(), r.asSequence())
    }
}
