class Day9(private val input: String) {

    fun part1(): Long {
        val fileSystem = input.generateFileSystem()
        var freeSpaceIndex = fileSystem.indexOfFirst { it == null }
        var fileIndex = fileSystem.indexOfLast { it != null }
        val indices = fileSystem.indices
        while (freeSpaceIndex < fileIndex) {
            fileSystem[freeSpaceIndex] = fileSystem[fileIndex]
            fileSystem[fileIndex] = null
            while (freeSpaceIndex in indices && fileSystem[freeSpaceIndex] != null) freeSpaceIndex++
            while (fileIndex in indices && fileSystem[fileIndex] == null) fileIndex--
        }
        return fileSystem.calculateChecksum()
    }

    fun part2(): Long {
        val fileSystem = input.generateFileSystem()
        var freeSpaceIndex = fileSystem.indexOfFirst { it == null }
        var fileIndex = fileSystem.indexOfLast { it != null }
        val indices = fileSystem.indices
        while (freeSpaceIndex < fileIndex) {
            val fileId = fileSystem[fileIndex]
            var fileSize = 0
            while (fileIndex in indices && fileSystem[fileIndex] == fileId) {
                fileSize++
                fileIndex--
            }
            fileIndex++
            while (freeSpaceIndex + fileSize - 1 in indices) {
                val space = fileSystem.subList(freeSpaceIndex, freeSpaceIndex + fileSize)
                if (space.all { it == null }) {
                    repeat(fileSize) {
                        fileSystem[freeSpaceIndex + it] = fileId
                        fileSystem[fileIndex + it] = null
                    }
                    break
                } else {
                    freeSpaceIndex++
                    if (freeSpaceIndex >= fileIndex) break
                }
            }
            freeSpaceIndex = fileSystem.indexOfFirst { it == null }
            while (--fileIndex in indices && fileSystem[fileIndex] == null) Unit
        }
        return fileSystem.calculateChecksum()
    }

    private fun String.generateFileSystem() = buildList {
        input.forEachIndexed { i, c ->
            val s = when (i % 2) {
                0 -> "${i / 2}"
                else -> null
            }
            repeat(c.digitToInt()) { add(s) }
        }
    }.toMutableList()

    private fun List<String?>.calculateChecksum() = asSequence()
        .mapIndexed { i, s -> i * (s?.toLong() ?: 0L) }
        .sum()
}
