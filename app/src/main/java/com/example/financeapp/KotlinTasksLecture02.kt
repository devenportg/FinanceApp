package com.example.financeapp

import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.text.iterator


//  Практика 02. Kotlin: основы языка, массивы и Map
//  Все 8 задач в одном файле (4 из лекции + 4 с LeetCode).


//  1. Из лекции. Приветствие без null
fun greeting(name: String?): String {
    val displayName = name?.uppercase() ?: "Гость"
    return "Привет, $displayName"
}



//  2. Из лекции. FizzBuzz для чисел от 1 до 20
fun fizzBuzz(): List<String> {
    val result = mutableListOf<String>()
    for (number in 1..20) {
        val label = when {
            number % 15 == 0 -> "FizzBuzz"
            number % 3 == 0 -> "Fizz"
            number % 5 == 0 -> "Buzz"
            else -> number.toString()
        }
        result.add(label)
    }
    return result
}



//  3. Из лекции. Печать списка цен
fun printPrices(prices: List<Int>) {
    println(prices.size)
    println(prices.firstOrNull() ?: "Нет цен")
    for (price in prices) {
        println(price)
    }
}



//  4. Из лекции. Обработка цен
fun filteredPrices(prices: List<Int>): List<String> = prices
    .filter { it >= 3 }
    .sortedDescending()
    .map { "Цена: $it" }



//  5. LeetCode 1480. Накопленная сумма (Running Sum of 1d Array)
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



//  6. LeetCode 1. Сумма двух чисел (Two Sum)
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


//  7. LeetCode 242. Анаграмма (Valid Anagram)
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
            val count = counts[char] ?: return false
            if (count == 1) {
                counts.remove(char)
            } else {
                counts[char] = count - 1
            }
        }
        return counts.isEmpty()
    }
}


//  8. LeetCode 350. Пересечение массивов II (Intersection of Two Arrays II)
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



//  Проверка всех решений на тестовых данных со слайдов

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
    // 1. Приветствие без null
    check(greeting("Маша") == "Привет, МАША") { "greeting(\"Маша\")" }
    check(greeting(null) == "Привет, Гость") { "greeting(null)" }
    check(greeting("иван") == "Привет, ИВАН") { "greeting(\"иван\")" }
    check(greeting("") == "Привет, ") { "greeting(\"\")" }
    check(greeting("Анна Петрова") == "Привет, АННА ПЕТРОВА") { "greeting(\"Анна Петрова\")" }
    println("1. Приветствие без null: ок")

    // 2. FizzBuzz
    val fizz = fizzBuzz()
    check(fizz.size == 20) { "fizzBuzz size" }
    check(fizz[8] == "Fizz") { "для 9 ожидалось Fizz" }
    check(fizz[9] == "Buzz") { "для 10 ожидалось Buzz" }
    check(fizz[14] == "FizzBuzz") { "для 15 ожидалось FizzBuzz" }
    check(fizz[6] == "7") { "для 7 ожидалось 7" }
    check(fizz[19] == "Buzz") { "для 20 ожидалось Buzz" }
    println("2. FizzBuzz: ок (1..20 -> ${fizz.take(5)}...)")

    // 3. Список цен
    val pricesOutput = captureOutput { printPrices(listOf(5, 4, 3, 5)) }
    check(pricesOutput == listOf("4", "5", "5", "4", "3", "5")) { "printPrices(5,4,3,5)" }
    check(captureOutput { printPrices(listOf(2)) } == listOf("1", "2", "2")) { "printPrices(2)" }
    check(captureOutput { printPrices(emptyList()) } == listOf("0", "Нет цен")) { "printPrices(empty)" }
    check(captureOutput { printPrices(listOf(3, 3, 4)) } == listOf("3", "3", "3", "3", "4")) { "printPrices(3,3,4)" }
    val longOutput = captureOutput { printPrices(listOf(1, 2, 3, 4, 5)) }
    check(longOutput == listOf("5", "1", "1", "2", "3", "4", "5")) { "printPrices(1..5)" }
    println("3. Список цен: ок")

    // 4. Обработка цен
    val filtered = filteredPrices(listOf(5, 2, 4, 3, 5))
    check(filtered == listOf("Цена: 5", "Цена: 5", "Цена: 4", "Цена: 3")) { "filteredPrices(5,2,4,3,5)" }
    check(filteredPrices(listOf(2, 1)) == emptyList<String>()) { "filteredPrices(2,1)" }
    check(filteredPrices(listOf(3)) == listOf("Цена: 3")) { "filteredPrices(3)" }
    check(filteredPrices(listOf(4, 5, 2)) == listOf("Цена: 5", "Цена: 4")) { "filteredPrices(4,5,2)" }
    check(filteredPrices(emptyList()) == emptyList<String>()) { "filteredPrices(empty)" }
    println("4. Обработка цен: ок")

    // 5. LeetCode 1480
    val runningSum = RunningSumSolution()
    check(runningSum.runningSum(intArrayOf(1, 2, 3, 4)).toList() == listOf(1, 3, 6, 10)) { "runningSum" }
    check(runningSum.runningSum(intArrayOf(1, 1, 1, 1, 1)).toList() == listOf(1, 2, 3, 4, 5)) { "runningSum 2" }
    check(runningSum.runningSum(intArrayOf(3, 1, 2, 10, 1)).toList() == listOf(3, 4, 6, 16, 17)) { "runningSum 3" }
    check(runningSum.runningSum(intArrayOf(-1, 2, -3)).toList() == listOf(-1, 1, -2)) { "runningSum 4" }
    check(runningSum.runningSum(intArrayOf(5)).toList() == listOf(5)) { "runningSum 5" }
    println("5. LeetCode 1480 (накопленная сумма): ок")

    // 6. LeetCode 1
    val twoSum = TwoSumSolution()
    check(twoSum.twoSum(intArrayOf(2, 7, 11, 15), 9).toList() == listOf(0, 1)) { "twoSum 1" }
    check(twoSum.twoSum(intArrayOf(3, 2, 4), 6).toList() == listOf(1, 2)) { "twoSum 2" }
    check(twoSum.twoSum(intArrayOf(3, 3), 6).toList() == listOf(0, 1)) { "twoSum 3" }
    check(twoSum.twoSum(intArrayOf(-1, -2, -3, -4, -5), -8).toList() == listOf(2, 4)) { "twoSum 4" }
    check(twoSum.twoSum(intArrayOf(0, 4, 3, 0), 0).toList() == listOf(0, 3)) { "twoSum 5" }
    println("6. LeetCode 1 (сумма двух чисел): ок")

    // 7. LeetCode 242
    val anagram = AnagramSolution()
    check(anagram.isAnagram("anagram", "nagaram")) { "anagram/nagaram" }
    check(!anagram.isAnagram("rat", "car")) { "rat/car" }
    check(anagram.isAnagram("listen", "silent")) { "listen/silent" }
    check(!anagram.isAnagram("aacc", "ccac")) { "aacc/ccac" }
    check(anagram.isAnagram("", "")) { "пустые строки" }
    println("7. LeetCode 242 (анаграмма): ок")

    // 8. LeetCode 350
    val intersection = IntersectionSolution()
    check(intersection.intersect(intArrayOf(1, 2, 2, 1), intArrayOf(2, 2)).toList() == listOf(2, 2)) { "intersect 1" }
    val second = intersection.intersect(intArrayOf(4, 9, 5), intArrayOf(9, 4, 9, 8, 4))
    check(second.sorted().toList() == listOf(4, 9)) { "intersect 2" }
    check(intersection.intersect(intArrayOf(1, 2, 2, 1), intArrayOf(2)).toList() == listOf(2)) { "intersect 3" }
    check(intersection.intersect(intArrayOf(1, 3, 3), intArrayOf(2, 4)).toList() == emptyList<Int>()) { "intersect 4" }
    val fifth = intersection.intersect(intArrayOf(5, 5, 5), intArrayOf(5, 5, 5, 5))
    check(fifth.toList() == listOf(5, 5, 5)) { "intersect 5" }
    println("8. LeetCode 350 (пересечение массивов): ок")

    println("Все проверки пройдены: 8 задач")
}
