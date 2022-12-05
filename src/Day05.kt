import java.util.*

fun main() {

    fun parse(input: List<String>): Map<Line, List<String>> {
        return input.groupBy { s ->
            when {
                s.isEmpty() -> Line.Space
                s.startsWith("move") -> Line.Op
                else -> Line.Crate
            }
        }
    }

    fun initCargos(crates: List<String>): List<Stack<Char>> {
        val stackCount = crates.last().chunked(4).count()
        val cargos = mutableListOf<Stack<Char>>().apply {
            repeat(stackCount) {
                add(Stack())
            }
        }
        crates.reduceRight { line, acc ->
            line.chunked(4).forEachIndexed { index, token ->
                if (token.contains("[")) {
                    val cargo = token[1]
                    cargos[index].push(cargo)
                }
            }
            acc
        }
        return cargos.toList()
    }

    fun initOps(ops: List<String>): List<Triple<Int, Int, Int>> {
        return ops.map { s ->
            val (count, src, dst) = s.split(" ")
                .filterIndexed { index, _ ->  index == 1 || index == 3 || index == 5 }
                .map { it.toInt() }
            Triple(count, src, dst)
        }
    }

    fun part1(inputs: List<String>): String {
        val groups = parse(inputs)

        val crates = groups[Line.Crate]!!
        val cargos = initCargos(crates)

        val ops = initOps(groups[Line.Op]!!)
        ops.forEach { (count, src, dst) ->
            repeat(count) {
                val cargo = cargos[src - 1].pop()
                cargos[dst - 1].push(cargo)
            }
        }
        return cargos.fold(StringBuilder()) { acc, elem ->
            acc.append(elem.pop())
        }.toString()
    }

    fun part2(inputs: List<String>): String {
        val groups = parse(inputs)

        val crates = groups[Line.Crate]!!
        val cargos = initCargos(crates)

        val ops = initOps(groups[Line.Op]!!)
        ops.forEach { (count, src, dst) ->
            val temp = Stack<Char>()
            repeat(count) {
                val cargo = cargos[src - 1].pop()
                temp.push(cargo)
            }
            repeat(count) {
                cargos[dst - 1].push(temp.pop())
            }
        }
        return cargos.fold(StringBuilder()) { acc, elem ->
            acc.append(elem.pop())
        }.toString()
    }

    val testInput = readInput("../data/Day05_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInput("../data/Day05")
    println(part1(input))
    println(part2(input))
}

sealed class Line {
    object Space : Line()
    object Crate : Line()
    object Op : Line()
}
