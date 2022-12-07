class Day07 {

    sealed class Node(val name: String, val parent: Node? = null) {
        val children = mutableMapOf<String, Node>()
    }

    class Directory(name: String, parent: Node? = null) : Node(name, parent)

    class File(name: String, val size: Int, parent: Node) : Node(name, parent)

    fun parse(inputs: List<String>): Node {
        val root: Node = Directory("/")
        var current = root
        inputs.forEach { command ->
            if (command.startsWith("$ cd ..")) {
                current = current.parent!!
            } else if (command.startsWith("$ cd")) {
                val name = command.split(" ")[2]
                val folder = current.children.getOrPut(name) { Directory(name, current) }
                current = folder
            } else if (command.startsWith("$ ls")) {
                // Do nothing
            } else {
                val (type, name) = command.split(" ")
                if (type == "dir") {
                    current.children[name] = Directory(name, current)
                } else {
                    val size = type.toInt()
                    current.children[name] = File(name, size, current)
                }
            }
        }
        return root
    }

    fun traverse(node: Node, predicate: (Int) -> Boolean): List<Int> {
        val res = mutableListOf<Int>()
        traverse(node, res, predicate)
        return res
    }

    private fun traverse(node: Node, res: MutableList<Int>, predicate: (Int) -> Boolean): Int {
        return if (node is File) {
            node.size
        } else {
            val total = node.children.values.fold(0) { acc, child ->
                acc + traverse(child, res, predicate)
            }
            if (predicate(total)) {
                res.add(total)
            }
            total
        }
    }
}

fun main() {

    fun part1(inputs: List<String>): Int {
        val day07 = Day07()
        val root = day07.parse(inputs)
        return day07.traverse(root) { size -> size <= 100000 }.sum()
    }

    fun part2(inputs: List<String>): Int {
        val day07 = Day07()
        val root = day07.parse(inputs)

        val total = day07.traverse(root) { true }.max()
        val unused = 70000000 - total
        val target = 30000000 - unused
        return day07.traverse(root) { size -> size >= target }.min()
    }

    val testInput = readInput("../data/Day07_test")
    check(part1(testInput) == 95437)
    check(part2(testInput) == 24933642)

    val input = readInput("../data/Day07")
    println(part1(input))
    println(part2(input))
}
