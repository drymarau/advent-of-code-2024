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

class Day8Test {

    private lateinit var day8: Day8

    @BeforeTest
    fun setUp() {
        val input = buildList {
            FileSystem.RESOURCES.readByUtf8Line("day8-input.txt".toPath(), ::add)
        }
        day8 = Day8(Input(input))
    }

    @Test
    fun `correctly calculates part 1`() {
        val result = measureTimedValue(day8::part1)
        assertThat(result::value).isEqualTo(14)
        assertThat(result::duration).isLessThan(1.seconds)
    }

    @Test
    fun `correctly calculates part 2`() {
        val result = measureTimedValue(day8::part2)
        assertThat(result::value).isEqualTo(34)
        assertThat(result::duration).isLessThan(1.seconds)
    }
}
