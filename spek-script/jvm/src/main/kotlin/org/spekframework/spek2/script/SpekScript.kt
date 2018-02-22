package org.spekframework.spek2.script

import org.spekframework.spek2.CreateWith
import org.spekframework.spek2.Spek
import org.spekframework.spek2.dsl.Root
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor
import kotlin.script.experimental.annotations.KotlinScript
import kotlin.script.experimental.annotations.KotlinScriptDefaultCompilationConfiguration
import kotlin.script.experimental.annotations.KotlinScriptFileExtension
import kotlin.script.experimental.api.*
import kotlin.script.experimental.misc.*
import kotlin.script.experimental.util.TypedKey

@KotlinScript
@KotlinScriptFileExtension("spek.kts")
@KotlinScriptDefaultCompilationConfiguration(SpekScriptConfiguration::class)
@CreateWith(SpekScriptFactory::class)
open class SpekScript(spekScriptKlassToWrap: KClass<SpekScript>?, private val specProxy: DynamicSpecProxy) : Spek({
    specProxy.proxy = this
    spekScriptKlassToWrap?.primaryConstructor?.call(null, specProxy)
}), Root by specProxy

object SpekScriptConfiguration : List<Pair<TypedKey<*>, Any?>> by ArrayList<Pair<TypedKey<*>, Any?>>(
    listOf(
        ScriptCompileConfigurationProperties.baseClass<SpekScript>(),
        ScriptCompileConfigurationProperties.defaultImports("org.spekframework.spek2.dsl.*")
    )
)
