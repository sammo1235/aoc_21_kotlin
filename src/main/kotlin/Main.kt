import java.io.File
fun main(args: Array<String>) {
    println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")
}

class Sample() {
    fun sum(a: Int, b: Int): Int {
        return a + b
    }
}

fun data(day: Int): List<String> {
    return File("assets/day_$day.txt").useLines { it.toList() }
}

fun dataAsInts(day: Int): List<Int> {
    return File("assets/day_$day.txt").useLines { it.toList() }.map { it.toInt() }
}


class Day1 {
    fun partOne(): Int {
        val data = dataAsInts(day = 1)
        var result = 0;
        for (i in 1 until data.size) {
            if (data[i-1] < data[i]) {
                result+=1;
            }
        }
        return result;
    }

    fun partTwo(): Int {
        val data = dataAsInts(day = 1)
        var result = 0;
        for (i in 1 until data.size - 2) {
            if (data.slice(i - 1..i + 1).sum() < data.slice(i..i + 2).sum()) {
                result += 1
            }
        }
        return result;
    }
}

class Day2 {
    fun partOne(): Int {
        val data = data(day = 2)
        var x = 0;
        var y = 0;
        val dataIterator = data.iterator();
        while (dataIterator.hasNext()) {
            val line = dataIterator.next().split("\\s".toRegex()).toTypedArray()
            val dir = line[0];
            val qu = line[1].toInt();
            if (dir == "forward") {
                x += qu
            } else if (dir == "down") {
                y += qu
            } else if (dir == "up") {
                y -= qu
            } else {
                x -= qu
            }
        }
        return x*y;
    }

    fun partTwo(): Int {
        val data = data(day = 2)
        var x = 0;
        var y = 0;
        var a = 0;
        val dataIterator = data.iterator();
        while(dataIterator.hasNext()) {
            val line = dataIterator.next().split("\\s".toRegex()).toTypedArray();
            val dir = line[0];
            val qu = line[1].toInt();
            if (dir == "forward") {
                x += qu;
                y += a * qu;
            } else if (dir == "down") {
                a += qu;
            } else if (dir == "up") {
                a -= qu;
            }
        }
        return x * y;
    }
}

class Day3 {
    fun partOne(): Int {
        val data = data(3)
        var transposed = transposeArray(data)

        var gamma: String = ""
        var epsilon: String = ""
        for (i in 0 until data[0].length) {
            val tally = transposed[i].toCollection(ArrayList()).groupingBy { it }.eachCount().toList().sortedBy { (_, value) -> value }
            gamma = gamma.plus(tally[1].first.toString())
            epsilon = epsilon.plus(tally[0].first.toString())
        }
        return gamma.toInt(2) * epsilon.toInt(2)
    }

    fun partTwo(): Int {
        var data = data(3)
        val oxy = calculate(data, "oxygen", '1')
        val co2 = calculate(data, "co2", '0')
        return oxy.toInt(2) * co2.toInt(2);
    }

    private fun calculate(data: List<String>, method: String, precedence: Char): String {
        var dat = data
        while(dat.size > 1) {
            loop@ for (i in dat.indices) {
                var transposed = transposeArray(dat)
                val tally = transposed[i].toCollection(ArrayList()).groupingBy { it }.eachCount().toList().sortedBy { (_, value) -> value }
                dat = if (tally[0].second == tally[1].second) {
                    dat.filter { it[i] == precedence }
                } else {
                    dat.filter { it[i].digitToInt() == tally[if (method == "oxygen") 1 else 0].first }
                }
                if (dat.size == 1) {
                    break@loop
                }
            }
        }
        return dat[0]
    }

    private fun transposeArray(array: List<String>): Array<IntArray> {
        val row = array.size
        val column = array[0].length
        var transpose = Array(column) { IntArray(row) }
        for (i in 0 until row) {
            for (j in 0 until column) {
                transpose[j][i] = array[i].toCharArray()[j].digitToInt()
            }
        }
        return transpose;
    }
}

class Day4 {
    fun partOne(): Int {
        val data = data(4)
        val called = data[0].split(",")
        var bingo = false;
        var winningNo: Int = 0
        var total = 0
        val boards = grabBoards(data.slice(1 until(data.size-1)));
        loop1@ for (calledNumber in called) {
            run breaking3@ {
                boards.forEach { entry ->
                    run breaking2@ {
                        entry.value.forEach { element ->
                            run breaking1@ {
                                element.forEachIndexed { index, pairing ->
                                    if(pairing.first == calledNumber) {
                                        element[index] = pairing.copy(first = pairing.first, true)
                                    }
                                    if (winningLineOrColumn(element, entry.value, index)) {
                                        winningNo = calledNumber.toInt()
                                        bingo = true
                                        total = getTotal(entry.value)
                                    }
                                    if (bingo) return@breaking1
                                }
                            }
                            if (bingo) return@breaking2
                        }
                    }
                    if (bingo) return@breaking3
                }
            }
            if (bingo) break@loop1
        }
        return total * winningNo
    }

    private fun getTotal(lines: MutableList<MutableList<Pair<String, Boolean>>>): Int {
        return lines.fold(0) { sum, el -> sum + el.filter{ !it.second }.fold(0) { sum, el1 -> sum + el1.first.toInt() }}
    }

    private fun transpose(data: MutableList<MutableList<Pair<String, Boolean>>>): MutableList<MutableList<Pair<String, Boolean>>> {
        // this takes a matrix of 5x5 and transposes it whilst removing any blank tiles
        val newLines: MutableList<MutableList<Pair<String, Boolean>>> = MutableList(5) { mutableListOf(Pair("0", false), Pair("0", false),Pair("0", false),Pair("0", false),Pair("0", false)) }
        for (i in 0 until data.size) {
            for (j in 0 until data[i].size) {
                if (data[i][j] == Pair("", false)) {
                } else {
                    newLines[j][i] = data[i][j]
                }
            }
        }
        return newLines
    }

    fun winningLineOrColumn(line: MutableList<Pair<String, Boolean>>, lines: MutableList<MutableList<Pair<String, Boolean>>>, idx: Int): Boolean {
        if (!line.map { it.second }.distinct().contains(false)) {
            return true
        } else if (!transpose(lines)[idx].map {it.second}.contains(false)) {
            return true
        }
        return false
     }

    fun grabBoards(data: List<String>): Map<Int, MutableList<MutableList<Pair<String, Boolean>>>> {
        var boards : Map<Int, MutableList<MutableList<Pair<String, Boolean>>>> = mutableMapOf<Int, MutableList<MutableList<Pair<String, Boolean>>>>()
        var n = 0;

        var tempList = mutableListOf<MutableList<Pair<String, Boolean>>>()
        data.map { it.split("\\s+".toRegex()).map { Pair(it, false) }.filter { it.first != ""}.toMutableList()}.forEach {
            if(it.size == 0) {
                n += 1
                boards = boards.plus(n to tempList)
                tempList = mutableListOf<MutableList<Pair<String, Boolean>>>()
            } else {
                tempList.add(it)
            }
        }
        return boards;
    }
}

