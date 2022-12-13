object Day11 {

    class Monkey(
        val id: Int,
        val levels: MutableList<Long>,
        val op: Int,
        val operand: Int,
        val divider: Int,
        val pos: Int,
        val neg: Int,
        var count: Int = 0
    )

    private const val OP_ADD = 0
    private const val OP_MUL = 1
    private const val OP_SQUARE = 2

    private fun parse(inputs: String): List<Monkey> {
        return inputs.split("\n\n").map { s ->
            val tokens = s.split("\n")
            val id = tokens[0].split(" ")[1].trimEnd(':').toInt() // Monkey 0
            val levels = tokens[1]
                .trim()
                .split(" ")
                .drop(2)
                .map { it.trimEnd(',').toLong() }
                .toMutableList()

            val (_op, _operand) = tokens[2].trim().split(" ").drop(4)
            val (op, operand) = if (_op == "+") {
                OP_ADD to _operand.toInt()
            } else if (_op == "*" && _operand == "old") {
                OP_SQUARE to 0
            } else {
                OP_MUL to _operand.toInt()
            }

            val divider = tokens[3].trim().split(" ")[3].toInt()
            val pos = tokens[4].trim().split(" ")[5].toInt()
            val neg = tokens[5].trim().split(" ")[5].toInt()
            Monkey(id, levels, op, operand, divider, pos, neg)
        }
    }

    private fun iteration(monkeys: List<Monkey>, count: Int, mitigateFunc: (Long) -> Long) {
        repeat(count) {
            monkeys.forEach { monkey ->
                val levels = monkey.levels.toList()
                monkey.levels.clear()

                levels.forEach { level ->
                    val newLevel = when (monkey.op) {
                        OP_ADD -> (level + monkey.operand)
                        OP_SQUARE -> (level * level)
                        else -> (level * monkey.operand)
                    }
                    val worry = mitigateFunc(newLevel)
                    if (worry % monkey.divider == 0L) {
                        monkeys[monkey.pos].levels.add(worry)
                    } else {
                        monkeys[monkey.neg].levels.add(worry)
                    }
                    monkey.count++
                }
            }
        }
    }

    fun part1(inputs: String): Long {
        val monkeys = parse(inputs)
        iteration(monkeys, 20) { it / 3 }
        val (a, b) = monkeys.sortedByDescending { it.count }
        return a.count.toLong() * b.count
    }

    fun part2(inputs: String): Long {
        val monkeys = parse(inputs)
        val divider = monkeys.map(Monkey::divider).reduce(Int::times).toLong()
        iteration(monkeys, 10_000) { it % divider }
        val (a, b) = monkeys.sortedByDescending { it.count }
        return a.count.toLong() * b.count
    }
}

fun main() {

    fun part1(inputs: String): Long {
        return Day11.part1(inputs)
    }

    fun part2(inputs: String): Long {
        return Day11.part2(inputs)
    }

    val testInput = readText("../data/Day11_test")
    check(part1(testInput) == 10605L)
    check(part2(testInput) == 2713310158L)

    val input = readText("../data/Day11")
    println(part1(input))
    println(part2(input))
}
