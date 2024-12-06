import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isLessThan
import okio.FileSystem
import okio.Path.Companion.toPath
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.time.Duration.Companion.seconds
import kotlin.time.measureTimedValue

class Day6Test {

    private lateinit var day6: Day6

    @BeforeTest
    fun setUp() {
        var row = -1
        var column = -1
        val map = mutableListOf<List<Char>>()
        val directions = "<^>v"
        FileSystem.RESOURCES.readByUtf8LineIndexed("day6-input.txt".toPath()) { i, line ->
            map += line.map { it }
            directions.forEach { c ->
                val index = line.indexOf(c)
                if (index >= 0) {
                    row = i
                    column = index
                }
            }
        }
        day6 = Day6(map, row, column)
    }

    @Test
    fun `correctly calculates part 1`() {
        val result = measureTimedValue(day6::part1)
        assertThat(result::value).isEqualTo(41)
        assertThat(result::duration).isLessThan(1.seconds)
    }

    @Test
    fun `correctly calculates part 2`() {
        val result = measureTimedValue(day6::part2)
        assertThat(result::value).isEqualTo(6)
        assertThat(result::duration).isLessThan(1.seconds)
    }
}
