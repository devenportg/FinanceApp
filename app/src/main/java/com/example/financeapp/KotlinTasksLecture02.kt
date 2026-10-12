@file:Suppress("MagicNumber")

// ============================================================
//  Практика 02. Kotlin: основы языка, массивы и Map
//  Все 8 задач в одном файле (4 из лекции + 4 с LeetCode)

import java.io.ByteArrayOutputStream
import java.io.PrintStream

// ------------------------------------------------------------
//  Константы решений
// ------------------------------------------------------------
private const val FIZZ_BUZZ_LIMIT = 20
private const val FIZZ_DIVISOR = 3
private const val BUZZ_DIVISOR = 5
private const val FIZZ_BUZZ_DIVISOR = FIZZ_DIVISOR * BUZZ_DIVISOR
private const val MIN_RELEVANT_PRICE = 3

// ------------------------------------------------------------
//  1. Из лекции. Приветствие без null
//  greeting("Маша") -> "Привет, МАША"; greeting(null) -> "Привет, Гость"
//  Оператор !! не используется.
// ------------------------------------------------------------
fun greeting(name: String?): String {
    val displayName = name?.uppercase() ?: "Гость"
    return "Привет, $displayName"
}

// ------------------------------------------------------------
//  2. Из лекции. FizzBuzz для чисел от 1 до 20
// ------------------------------------------------------------
fun fizzBuzz(): List<String> {
    val result = mutableListOf<String>()
    for (number in 1..FIZZ_BUZZ_LIMIT) {
        val label = when {
            number % FIZZ_BUZZ_DIVISOR == 0 -> "FizzBuzz"
            number % FIZZ_DIVISOR == 0 -> "Fizz"
            number % BUZZ_DIVISOR == 0 -> "Buzz"
            else -> number.toString()
        }
        result.add(label)
    }
    return result
}

// ------------------------------------------------------------
//  3. Из лекции. Печать списка цен
//  Печатает: количество цен, первую цену (или "Нет цен"), затем каждую цену.
// ------------------------------------------------------------
fun printPrices(prices: List<Int>) {
    println(prices.size)
    println(prices.firstOrNull() ?: "Нет цен")
    for (price in prices) {
        println(price)
    }
}

// ------------------------------------------------------------
//  4. Из лекции. Обработка цен
//  Оставить цены >= 3, отсортировать по убыванию, превратить в "Цена: N".
// ------------------------------------------------------------
fun filteredPrices(prices: List<Int>): List<String> = prices
    .filter { it >= MIN_RELEVANT_PRICE }
    .sortedDescending()
    .map { "Цена: $it" }

// ------------------------------------------------------------
//  5. LeetCode 1480. Накопленная сумма (Running Sum of 1d Array)
//  ВАЖНО: на LeetCode метод должен лежать внутри класса Solution,
//  поэтому здесь класс назван RunningSumSolution, чтобы в одном файле
//  не было двух классов Solution. Для сайта LeetCode достаточно
//  переименовать класс в Solution.
// ------------------------------------------------------------
class RunningSumSolution {
    fun runningSum(nums: IntArray): IntArray {
        val result = IntArray(nums.size)
        var sum = 0
        for (index in nums.indices) {
            sum += nums[index]
            result[index] = sum
        }
        return result
    }
}

// ------------------------------------------------------------
//  6. LeetCode 1. Сумма двух чисел (Two Sum)
//  Один проход с Map: для каждого числа ищем дополнение target - число.
//  Сложность O(n) вместо O(n^2) у двух вложенных циклов.
// ------------------------------------------------------------
class TwoSumSolution {
    fun twoSum(nums: IntArray, target: Int): IntArray {
        val indexByNumber = mutableMapOf<Int, Int>()
        for (index in nums.indices) {
            val previousIndex = indexByNumber[target - nums[index]]
            if (previousIndex != null) {
                return intArrayOf(previousIndex, index)
            }
            indexByNumber[nums[index]] = index
        }
        error("По условию решение существует")
    }
}

// ------------------------------------------------------------
//  7. LeetCode 242. Анаграмма (Valid Anagram)
//  Считаем буквы первой строки, вычитаем буквы второй;
//  в конце все счётчики должны стать нулевыми.
// ------------------------------------------------------------
class AnagramSolution {
    fun isAnagram(s: String, t: String): Boolean {
        if (s.length != t.length) {
            return false
        }
        val counts = mutableMapOf<Char, Int>()
        for (char in s) {
            counts[char] = counts.getOrDefault(char, 0) + 1
        }
        for (char in t) {
            counts[char] = counts.getOrDefault(char, 0) - 1
        }
        return counts.all { (_, count) -> count == 0 }
    }
}

// ------------------------------------------------------------
//  8. LeetCode 350. Пересечение массивов II (Intersection of Two Arrays II)
//  Частоты первого массива в Map, затем проход по второму.
// ------------------------------------------------------------
class IntersectionSolution {
    fun intersect(nums1: IntArray, nums2: IntArray): IntArray {
        val counts = mutableMapOf<Int, Int>()
        for (number in nums1) {
            counts[number] = counts.getOrDefault(number, 0) + 1
        }
        val result = mutableListOf<Int>()
        for (number in nums2) {
            val count = counts[number] ?: 0
            if (count > 0) {
                result.add(number)
                counts[number] = count - 1
            }
        }
        return result.toIntArray()
    }
}

// ============================================================
//  Тестовые данные со слайдов
// ============================================================
private val pricesCases = listOf(
    listOf(5, 4, 3, 5) to listOf("4", "5", "5", "4", "3", "5"),
    listOf(2) to listOf("1", "2", "2"),
    emptyList<Int>() to listOf("0", "Нет цен"),
    listOf(3, 3, 4) to listOf("3", "3", "3", "3", "4"),
    listOf(1, 2, 3, 4, 5) to listOf("5", "1", "1", "2", "3", "4", "5"),
)

private val filteredPricesCases = listOf(
    listOf(5, 2, 4, 3, 5) to listOf("Цена: 5", "Цена: 5", "Цена: 4", "Цена: 3"),
    listOf(2, 1) to emptyList<String>(),
    listOf(3) to listOf("Цена: 3"),
    listOf(4, 5, 2) to listOf("Цена: 5", "Цена: 4"),
    emptyList<Int>() to emptyList<String>(),
)

private val runningSumCases = listOf(
    intArrayOf(1, 2, 3, 4) to listOf(1, 3, 6, 10),
    intArrayOf(1, 1, 1, 1, 1) to listOf(1, 2, 3, 4, 5),
    intArrayOf(3, 1, 2, 10, 1) to listOf(3, 4, 6, 16, 17),
    intArrayOf(-1, 2, -3) to listOf(-1, 1, -2),
    intArrayOf(5) to listOf(5),
)

private val twoSumCases = listOf(
    Triple(intArrayOf(2, 7, 11, 15), 9, listOf(0, 1)),
    Triple(intArrayOf(3, 2, 4), 6, listOf(1, 2)),
    Triple(intArrayOf(3, 3), 6, listOf(0, 1)),
    Triple(intArrayOf(-1, -2, -3, -4, -5), -8, listOf(2, 4)),
    Triple(intArrayOf(0, 4, 3, 0), 0, listOf(0, 3)),
)

private val anagramCases = listOf(
    Triple("anagram", "nagaram", true),
    Triple("rat", "car", false),
    Triple("listen", "silent", true),
    Triple("aacc", "ccac", false),
    Triple("", "", true),
)

private val intersectionCases = listOf(
    Triple(intArrayOf(1, 2, 2, 1), intArrayOf(2, 2), listOf(2, 2)),
    Triple(intArrayOf(4, 9, 5), intArrayOf(9, 4, 9, 8, 4), listOf(4, 9)),
    Triple(intArrayOf(1, 2, 2, 1), intArrayOf(2), listOf(2)),
    Triple(intArrayOf(1, 3, 3), intArrayOf(2, 4), emptyList()),
    Triple(intArrayOf(5, 5, 5), intArrayOf(5, 5, 5, 5), listOf(5, 5, 5)),
)

// ============================================================
//  Проверка всех решений
// ============================================================
fun captureOutput(block: () -> Unit): List<String> {
    val originalOut = System.out
    val buffer = ByteArrayOutputStream()
    System.setOut(PrintStream(buffer, true, "UTF-8"))
    try {
        block()
    } finally {
        System.setOut(originalOut)
    }
    return buffer.toString("UTF-8").trim().lines()
}

fun main() {
    // Фикс кодировки: чтобы русский текст не превращался в "??????" в панели Run.
    System.setOut(PrintStream(System.out, true, "UTF-8"))

    // 1. Приветствие без null
    check(greeting("Маша") == "Привет, МАША") { "greeting(\"Маша\")" }
    check(greeting(null) == "Привет, Гость") { "greeting(null)" }
    check(greeting("иван") == "Привет, ИВАН") { "greeting(\"иван\")" }
    check(greeting("") == "Привет, ") { "greeting(\"\")" }
    check(greeting("Анна Петрова") == "Привет, АННА ПЕТРОВА") { "Анна Петрова" }
    println("1. Приветствие без null: ок")

    // 2. FizzBuzz
    val fizz = fizzBuzz()
    check(fizz.size == FIZZ_BUZZ_LIMIT) { "fizzBuzz size" }
    check(fizz[8] == "Fizz") { "для 9 ожидалось Fizz" }
    check(fizz[9] == "Buzz") { "для 10 ожидалось Buzz" }
    check(fizz[14] == "FizzBuzz") { "для 15 ожидалось FizzBuzz" }
    check(fizz[6] == "7") { "для 7 ожидалось 7" }
    check(fizz[19] == "Buzz") { "для 20 ожидалось Buzz" }
    println("2. FizzBuzz: ок (1..20 -> ${fizz.take(5)}...)")

    // 3. Список цен
    for ((prices, expected) in pricesCases) {
        check(captureOutput { printPrices(prices) } == expected) { "printPrices($prices)" }
    }
    println("3. Список цен: ок")

    // 4. Обработка цен
    for ((prices, expected) in filteredPricesCases) {
        check(filteredPrices(prices) == expected) { "filteredPrices($prices)" }
    }
    println("4. Обработка цен: ок")

    // 5. LeetCode 1480
    val runningSum = RunningSumSolution()
    for ((input, expected) in runningSumCases) {
        val actual = runningSum.runningSum(input).toList()
        check(actual == expected) { "runningSum(${input.toList()})" }
    }
    println("5. LeetCode 1480 (накопленная сумма): ок")

    // 6. LeetCode 1
    val twoSum = TwoSumSolution()
    for ((nums, target, expected) in twoSumCases) {
        check(twoSum.twoSum(nums, target).toList() == expected) { "twoSum(${nums.toList()}, $target)" }
    }
    println("6. LeetCode 1 (сумма двух чисел): ок")

    // 7. LeetCode 242
    val anagram = AnagramSolution()
    for ((first, second, expected) in anagramCases) {
        check(anagram.isAnagram(first, second) == expected) { "isAnagram(\"$first\", \"$second\")" }
    }
    println("7. LeetCode 242 (анаграмма): ок")

    // 8. LeetCode 350
    val intersection = IntersectionSolution()
    for ((first, second, expected) in intersectionCases) {
        val actual = intersection.intersect(first, second).sorted().toList()
        check(actual == expected.sorted()) { "intersect(${first.toList()}, ${second.toList()})" }
    }
    println("8. LeetCode 350 (пересечение массивов): ок")

    println("Все проверки пройдены: 8 задач")
}
