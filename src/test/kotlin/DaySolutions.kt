import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day1Solutions {
    private val testSample: Day1 = Day1()

    @Test
    fun testDay1() {
        val expectedPartOne: Int = 1342
        val expectedPartTwo: Int = 1378
        assertEquals(expectedPartOne, testSample.partOne())
        assertEquals(expectedPartTwo, testSample.partTwo())
    }
}

internal class Day2Solutions {
    private val testSample: Day2 = Day2()

    @Test
    fun testDay2() {
        val expectedPartOne: Int = 2039256;
        val expectedPartTwo: Int = 1856459736;

        assertEquals(expectedPartOne, testSample.partOne())
        assertEquals(expectedPartTwo, testSample.partTwo())
    }
}

internal class Day3Solutions {
    private val testSample: Day3 = Day3()

    @Test
    fun testDay3() {
        val expectedPartOne: Int = 775304;
        val expectedPartTwo: Int = 1370737;
        assertEquals(expectedPartOne, testSample.partOne())
        assertEquals(expectedPartTwo, testSample.partTwo())
    }
}