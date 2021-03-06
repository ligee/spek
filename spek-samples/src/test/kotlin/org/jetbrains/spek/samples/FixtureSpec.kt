package org.jetbrains.spek.samples

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it

/**
 * @author Ranie Jade Ramiso
 */
class FixtureSpec: Spek({
    var counter = 0
    beforeEach {
        counter++
    }

    describe("a number") {
        beforeEach {
            counter++
        }

        it("should be 2") {
            assertThat(counter, equalTo(2))
        }

        it("should be 2 as well") {
            assertThat(counter, equalTo(2))
        }

        afterEach {
            counter--
        }
    }

    afterEach {
        counter--
    }
})
