class Day5(private val rules: Map<Int, Set<Int>>, private val updates: List<List<Int>>) : Day {

    override fun part1(): Number = updates.asSequence()
        .filter(::good)
        .sumOf(::middleElement)

    override fun part2(): Number = updates.asSequence()
        .filterNot(::good)
        .map { it.sortedWith(comparator) }
        .sumOf(::middleElement)

    private fun good(update: List<Int>): Boolean {
        val seen = mutableSetOf<Int>()
        update.forEach {
            val rule = rules.getOrElse(it, ::emptySet)
            val intersection = rule.intersect(seen)
            if (intersection.isNotEmpty()) return false
            seen.add(it)
        }
        return true
    }

    private fun <T> middleElement(list: List<T>) = list[list.size / 2]

    private val comparator = Comparator<Int> { a, b ->
        when (b) {
            in rules.getOrElse(a, ::mutableSetOf) -> -1
            else -> 0
        }
    }
}
