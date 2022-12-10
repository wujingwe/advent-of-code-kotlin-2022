object Day09 {

    private val ADJACENTS = listOf(
        0 to 0,
        -1 to 0, 1 to 0, 0 to -1, 0 to 1,
        -1 to -1, -1 to 1, 1 to -1, 1 to 1
    )

    private val STEPS = mapOf(
        "U" to (-1 to 0),
        "D" to (1 to 0),
        "R" to (0 to 1),
        "L" to (0 to -1)
    )

    private fun traverse(inputs: List<String>, knotsCount: Int): Int {
        val knots = mutableListOf(0 to 0) // head
        repeat(knotsCount) {
            knots.add(0 to 0) // knots
        }

        return inputs.map { s ->
            val (dir, count) = s.split(" ")
            dir to count.toInt()
        }.flatMap { (dir, count) ->
            List(count) { it }.map {
                val (sx, sy) = STEPS.getValue(dir)
                knots[0] = knots[0].first + sx to knots[0].second + sy

                for (i in 0 until knots.size - 1) {
                    val begin = knots[i]
                    val end = knots[i + 1]

                    val adjacent = ADJACENTS.any { (dx, dy) ->
                        end == (begin.first + dx to begin.second + dy)
                    }
                    if (!adjacent) {
                        val dx = begin.first - end.first
                        val dy = begin.second - end.second
                        val nx = if (dx > 0) 1 else -1
                        val ny = if (dy > 0) 1 else -1

                        knots[i + 1] = if (begin.second == end.second) { // same column
                            end.first + nx to end.second
                        } else if (begin.first == end.first) { // same row
                            end.first to end.second + ny
                        } else {
                            end.first + nx to end.second + ny
                        }
                    }
                }
                knots[knotsCount]
            }
        }.toSet().size
    }

    fun part1(inputs: List<String>): Int {
        return traverse(inputs, 1)
    }

    fun part2(inputs: List<String>): Int {
        return traverse(inputs, 9)
    }
}

fun main() {

    fun part1(inputs: List<String>): Int {
        return Day09.part1(inputs)
    }

    fun part2(inputs: List<String>): Int {
        return Day09.part2(inputs)
    }

    val testInput = readInput("../data/Day09_test")
    check(part1(testInput) == 88)
    check(part2(testInput) == 36)

    val input = readInput("../data/Day09")
    println(part1(input))
    println(part2(input))
}
