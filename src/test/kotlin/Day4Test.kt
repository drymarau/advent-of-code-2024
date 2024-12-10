import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isLessThan
import okio.FileSystem
import okio.Path.Companion.toPath
import util.Input
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.time.Duration.Companion.seconds
import kotlin.time.measureTimedValue

class Day4Test {

    private lateinit var day4: Day4

    @BeforeTest
    fun setUp() {
        val input = buildList {
            FileSystem.RESOURCES.readByUtf8Line("day4-input.txt".toPath(), ::add)
        }
        day4 = Day4(Input(input))
    }

    @Test
    fun `correctly calculates part 1`() {
        val result = measureTimedValue(day4::part1)
        assertThat(result::value).isEqualTo(18)
        assertThat(result::duration).isLessThan(1.seconds)
    }

    @Test
    fun `correctly calculates part 2`() {
        val result = measureTimedValue(day4::part2)
        assertThat(result::value).isEqualTo(9)
        assertThat(result::duration).isLessThan(1.seconds)
    }
}
