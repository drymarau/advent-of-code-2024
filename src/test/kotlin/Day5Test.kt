import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isLessThan
import okio.FileSystem
import okio.Path.Companion.toPath
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.time.Duration.Companion.seconds
import kotlin.time.measureTimedValue

class Day5Test {

    private lateinit var day5: Day5

    @BeforeTest
    fun setUp() {
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
        day5 = Day5(rules, updates)
    }

    @Test
    fun `correctly calculates part 1`() {
        val result = measureTimedValue(day5::part1)
        assertThat(result::value).isEqualTo(143)
        assertThat(result::duration).isLessThan(1.seconds)
    }

    @Test
    fun `correctly calculates part 2`() {
        val result = measureTimedValue(day5::part2)
        assertThat(result::value).isEqualTo(123)
        assertThat(result::duration).isLessThan(1.seconds)
    }
}
