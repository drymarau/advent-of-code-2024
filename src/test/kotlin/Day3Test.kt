import okio.FileSystem
import okio.Path.Companion.toPath

class Day3Test : DayTest(part1 = 161, part2 = 48) {

    override fun create(): Day {
        val input = buildString {
            FileSystem.RESOURCES.readByUtf8Line("day3-input.txt".toPath()) {
                append(it)
            }
        }
        return Day3(input)
    }
}
