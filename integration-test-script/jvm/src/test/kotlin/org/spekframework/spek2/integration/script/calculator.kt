package org.spekframework.spek2.integration.script

import org.spekframework.spek2.style.specification.Suite
import kotlin.math.pow
import kotlin.test.assertEquals

open class Calculator {
    fun add(x: Int, y: Int) = x + y
}

class AdvancedCalculator: org.spekframework.spek2.integration.script.Calculator() {
    fun pow(x: Int, y: Int) = x.toDouble().pow(y).toInt()
}


fun Suite.behavesLikeACalculator() {
    val calculator by memoized<org.spekframework.spek2.integration.script.Calculator>()

    it("1 + 2 = 3") {
        assertEquals(3, calculator.add(1, 2))
    }
}

