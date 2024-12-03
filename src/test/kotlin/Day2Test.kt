import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isLessThan
import okio.FileSystem
import okio.Path.Companion.toPath
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.measureTimedValue

class Day2Test {

    private lateinit var day2: Day2

    @BeforeTest
    fun setUp() {
        val reports = buildList<List<Int>> {
            val regex = Regex("\\s+")
            FileSystem.RESOURCES.readByUtf8Line("day2-input.txt".toPath()) {
                this += it.splitToSequence(regex)
                    .map(String::toInt)
                    .toList()
            }
        }
        day2 = Day2(reports)
    }

    @Test
    fun `correctly calculates part 1`() {
        val result = measureTimedValue(day2::part1)
        assertThat(result::value).isEqualTo(2)
        assertThat(result::duration).isLessThan(5.milliseconds)
    }

    @Test
    fun `correctly calculates part 2`() {
        val result = measureTimedValue(day2::part2)
        assertThat(result::value).isEqualTo(4)
        assertThat(result::duration).isLessThan(5.milliseconds)
    }
}
