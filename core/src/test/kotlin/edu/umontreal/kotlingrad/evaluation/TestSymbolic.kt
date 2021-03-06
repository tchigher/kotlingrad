package edu.umontreal.kotlingrad.evaluation

import edu.umontreal.kotlingrad.*
import edu.umontreal.kotlingrad.experimental.*
import io.kotlintest.properties.assertAll
import io.kotlintest.specs.StringSpec
import javax.script.*
import javax.script.ScriptContext.ENGINE_SCOPE

@Suppress("NonAsciiCharacters")
class TestSymbolic : StringSpec({
  val engine = ScriptEngineManager().getEngineByExtension("kts")

  fun ktf(f: SFun<DReal>, vararg kgBnds: Pair<SVar<DReal>, Number>) =
    engine.run {
      try {
        val bnds = kgBnds.map { it.first.name to it.second.toDouble() }.toMap()
        setBindings(SimpleBindings(bnds), ENGINE_SCOPE)
        eval("import kotlin.math.*; $f")
      } catch (e: Exception) {
        System.err.println("Failed to evaluate expression: $f")
        throw e
      }
    }

  with(DoublePrecision) {
    "test symbolic evaluation" {
      TestExpressionGenerator(DoublePrecision).assertAll(20) { f: SFun<DReal> ->
        DoubleGenerator.assertAll(20) { ẋ, ẏ, ż ->
          f(x to ẋ, y to ẏ, z to ż) shouldBeAbout ktf(f, x to ẋ, y to ẏ, z to ż)
        }
      }
    }
  }
})