object Day08 {

    private val DIRS = listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)

    fun part1(inputs: List<String>): Int {
        val forest = inputs
            .withIndex()
            .flatMap { (row, line) ->
                line.withIndex().map { (col, value) -> (row to col) to value - '0'}
            }
            .toMap()

        return forest.map { (pos, sourceValue) ->
            DIRS.map { (dx, dy) ->
                val (startRow, startCol) = pos
                val sequence = generateSequence(startRow to startCol) { (row, col) ->
                    val nextRow = row + dx
                    val nexCol = col + dy

                    if (nextRow in inputs.indices && nexCol in inputs[0].indices) {
                        nextRow to nexCol
                    } else {
                        null
                    }
                }
                sequence
                    .drop(1)
                    .firstOrNull { forest.getValue(it) >= sourceValue }
            }.any { it == null } // reach the edge
        }.count { it }
    }

    fun part2(inputs: List<String>): Int {
        val forest = inputs
            .withIndex()
            .flatMap { (row, line) ->
                line.withIndex().map { (col, value) -> (row to col) to value - '0'}
            }
            .toMap()

        return forest.map { (pos, sourceValue) ->
            DIRS.map { (dx, dy) ->
                val (startRow, startCol) = pos
                val sequence = generateSequence(startRow to startCol) { (row, col) ->
                    val nextRow = row + dx
                    val nexCol = col + dy

                    if (nextRow in inputs.indices && nexCol in inputs[0].indices) {
                        nextRow to nexCol
                    } else {
                        null
                    }
                }
                sequence
                    .withIndex()
                    .drop(1)
                    .firstOrNull { (_, value) -> forest.getValue(value) >= sourceValue }
                    ?.index
                    ?: (sequence.count() - 1)
            }.fold(1) { acc, elem -> acc * elem }
        }.max()
    }
}

fun main() {

    fun part1(inputs: List<String>): Int {
        return Day08.part1(inputs)
    }

    fun part2(inputs: List<String>): Int {
        return Day08.part2(inputs)
    }

    val testInput = readInput("../data/Day08_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val input = readInput("../data/Day08")
    println(part1(input))
    println(part2(input))
}
