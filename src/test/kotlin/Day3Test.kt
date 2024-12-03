import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isLessThan
import okio.FileSystem
import okio.Path.Companion.toPath
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.measureTimedValue

class Day3Test {

    private lateinit var day3: Day3

    @BeforeTest
    fun setUp() {
        val input = buildString {
            FileSystem.RESOURCES.readByUtf8Line("day3-input.txt".toPath()) {
                append(it)
            }
        }
        day3 = Day3(input)
    }

    @Test
    fun `correctly calculates part 1`() {
        val result = measureTimedValue(day3::part1)
        assertThat(result::value).isEqualTo(161)
        assertThat(result::duration).isLessThan(5.milliseconds)
    }

    @Test
    fun `correctly calculates part 2`() {
        val result = measureTimedValue(day3::part2)
        assertThat(result::value).isEqualTo(48)
        assertThat(result::duration).isLessThan(5.milliseconds)
    }
}
