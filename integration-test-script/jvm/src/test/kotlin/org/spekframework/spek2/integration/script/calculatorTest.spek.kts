
package org.spekframework.spek2.integration.script

import kotlin.test.assertEquals
import org.spekframework.spek2.style.specification.describe

describe("Calculator") {
    val calculator by memoized { org.spekframework.spek2.integration.script.Calculator() }

    behavesLikeACalculator()
}

describe("AdvancedCalculator") {
    val calculator by memoized { org.spekframework.spek2.integration.script.AdvancedCalculator() }

    behavesLikeACalculator()

    it("2 ^ 3 = 8") {
        assertEquals(8, calculator.pow(2, 3))
    }
}
