class Day3(private val input: String) {

    private val regex = Regex("""mul\((\d{1,3}),(\d{1,3})\)|do\(\)|don't\(\)""")

    fun part1(): Int = regex.findAll(input).sumOf(::mul)

    fun part2(): Int {
        var apply = true
        return regex.findAll(input)
            .onEach {
                when (it.value) {
                    "do()" -> apply = true
                    "don't()" -> apply = false
                }
            }
            .filter { apply }
            .sumOf(::mul)
    }

    private fun mul(result: MatchResult): Int = when (result.value) {
        "do()", "don't()" -> 0
        else -> result.groupValues[1].toInt() * result.groupValues[2].toInt()
    }
}
