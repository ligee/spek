package org.spekframework.spek2.script

import org.spekframework.spek2.CreateWith
import org.spekframework.spek2.Spek
import org.spekframework.spek2.dsl.Root
import org.spekframework.spek2.lifecycle.InstanceFactory
import kotlin.reflect.KClass
import kotlin.script.experimental.annotations.KotlinScript
import kotlin.script.experimental.api.ScriptCompilationConfiguration
import kotlin.script.experimental.api.defaultImports
import kotlin.script.experimental.api.implicitReceivers

@KotlinScript(fileExtension = "spek", compilationConfiguration = SpekScriptConfiguration::class)
@CreateWith(SpekScriptFactory::class)
open class SpekScript(spekScriptClassToWrap: KClass<out SpekScript>?) : Spek({
    if (spekScriptClassToWrap != SpekScript::class) {
        spekScriptClassToWrap?.java?.constructors?.get(0)?.let {
            it.newInstance(null, this)
        }
    }
})

object SpekScriptConfiguration : ScriptCompilationConfiguration({
    implicitReceivers(Root::class)
    defaultImports("org.spekframework.spek2.dsl.*")
})

class SpekScriptFactory : InstanceFactory {
    override fun <T : Spek> create(spek: KClass<T>): T {
        val targetClass = spek as KClass<SpekScript>
        return SpekScript(targetClass) as T
    }
}
