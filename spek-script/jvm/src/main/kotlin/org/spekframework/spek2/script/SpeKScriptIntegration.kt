package org.spekframework.spek2.script

import org.spekframework.spek2.Spek
import org.spekframework.spek2.dsl.*
import org.spekframework.spek2.lifecycle.CachingMode
import org.spekframework.spek2.lifecycle.InstanceFactory
import org.spekframework.spek2.lifecycle.LifecycleListener
import org.spekframework.spek2.lifecycle.MemoizedValue
import kotlin.reflect.KClass

open class DynamicSpecProxy: Root {

    private var _proxy: Root? = null
    //    @Synchronized
    var proxy: Root
        get() = _proxy ?: throw IllegalStateException("Spec proxy is not initialized yet")
        set(value) { _proxy = value }

    override fun registerListener(listener: LifecycleListener) {
        proxy.registerListener(listener)
    }

    override fun group(description: String, pending: Pending, body: GroupBody.() -> Unit) {
        proxy.group(description, pending, body)
    }

    override fun action(description: String, pending: Pending, body: ActionBody.() -> Unit) {
        proxy.action(description, pending, body)
    }

    override fun <T> memoized(mode: CachingMode, factory: () -> T, destructor: (T) -> Unit): MemoizedValue<T> =
        proxy.memoized(mode, factory, destructor)

    override fun <T> memoized(mode: CachingMode, factory: () -> T): MemoizedValue<T> =
        proxy.memoized(mode, factory)

    override fun <T> memoized(): MemoizedValue<T> =
        proxy.memoized()

    override fun beforeEachTest(callback: () -> Unit) {
        proxy.beforeEachTest(callback)
    }

    override fun afterEachTest(callback: () -> Unit) {
        proxy.afterEachTest(callback)
    }

    override fun beforeGroup(callback: () -> Unit) {
        proxy.beforeGroup(callback)
    }

    override fun afterGroup(callback: () -> Unit) {
        proxy.afterGroup(callback)
    }

    override fun test(description: String, pending: Pending, body: TestBody.() -> Unit) {
        proxy.test(description, pending, body)
    }
}

class SpekScriptFactory : InstanceFactory {
    override fun <T : Spek> create(spek: KClass<T>): T {
        val targetClass = spek as KClass<SpekScript>
        return SpekScript(targetClass, DynamicSpecProxy()) as T
    }
}
