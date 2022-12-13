typealias Point = Pair<Int, Int>
object Day12 {

    private val DIRS = listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)

    private fun bfs(map: List<List<Char>>, start: Point, end: Point): Int {
        val m = map.size
        val n = map[0].size
        val visited = MutableList(m) { MutableList(n) { false } }

        val queue = ArrayDeque<Point>()
        queue.addLast(start)

        var count = 0
        while (queue.isNotEmpty()) {
            val size = queue.size
            for (i in 0 until size) {
                val point = queue.removeFirst()
                if (visited[point.first][point.second]) { // important!
                    continue
                }
                visited[point.first][point.second] = true

                if (point == end) {
                    return count
                }

                DIRS.forEach { (dx, dy) ->
                    val nx = point.first + dx
                    val ny = point.second + dy

                    if (nx in 0 until m && ny in 0 until n && !visited[nx][ny]) {
                        if (map[nx][ny] - map[point.first][point.second] <= 1) {
                            queue.addLast(nx to ny)
                        }
                    }
                }
            }
            count++
        }
        return Int.MAX_VALUE
    }

    fun part1(inputs: List<String>): Int {
        val m = inputs.size
        val n = inputs[0].length

        var start = 0 to 0
        var end = 0 to 0

        val map = inputs.map { s -> s.toCharArray().toMutableList() }
        for (i in 0 until m) {
            for (j in 0 until n) {
                if (map[i][j] == 'S') {
                    map[i][j] = 'a'
                    start = i to j
                } else if (map[i][j] == 'E') {
                    map[i][j] = 'z'
                    end = i to j
                }
            }
        }
        return bfs(map, start, end)
    }

    fun part2(inputs: List<String>): Int {
        val m = inputs.size
        val n = inputs[0].length

        val starts = mutableListOf<Point>()
        var end = 0 to 0

        val map = inputs.map { s -> s.toCharArray().toMutableList() }
        for (i in 0 until m) {
            for (j in 0 until n) {
                if (map[i][j] == 'a') {
                    starts.add(i to j)
                } else if (map[i][j] == 'S') {
                    map[i][j] = 'a'
                    starts.add(i to j)
                } else if (map[i][j] == 'E') {
                    map[i][j] = 'z'
                    end = i to j
                }
            }
        }
        return starts.minOf { bfs(map, it, end) }
    }
}

fun main() {

    fun part1(inputs: List<String>): Int {
        return Day12.part1(inputs)
    }

    fun part2(inputs: List<String>): Int {
        return Day12.part2(inputs)
    }

    val testInput = readInput("../data/Day12_test")
    check(part1(testInput) == 31)
    check(part2(testInput) == 29)

    val input = readInput("../data/Day12")
    println(part1(input))
    println(part2(input))
}
