fun main() {

    fun findMarker(input: String, size: Int): Int {
        return input.windowed(size).indexOfFirst { it.toSet().size == size } + size
    }

    fun part1(inputs: String): Int {
        return findMarker(inputs, 4)
    }

    fun part2(inputs: String): Int {
        return findMarker(inputs, 14)
    }

    val testInput = readText("../data/Day06_test")
    check(part1(testInput) == 7)
    check(part1("bvwbjplbgvbhsrlpgdmjqwftvncz") == 5)
    check(part1("nppdvjthqldpwncqszvftbrmjlhg") == 6)
    check(part1("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg") == 10)
    check(part1("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw") == 11)

    check(part2(testInput) == 19)
    check(part2("bvwbjplbgvbhsrlpgdmjqwftvncz") == 23)
    check(part2("nppdvjthqldpwncqszvftbrmjlhg") == 23)
    check(part2("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg") == 29)
    check(part2("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw") == 26)

    val input = readText("../data/Day06")
    println(part1(input))
    println(part2(input))
}
