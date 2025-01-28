package rs.soph.kotwiser.ide.folding

import com.intellij.lang.LanguageExtension
import com.intellij.lang.folding.FoldingBuilder
import com.intellij.lang.folding.LanguageFolding.INSTANCE as LanguageFolding
import com.intellij.openapi.util.Key
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import org.jetbrains.kotlin.utils.findIsInstanceAnd
import kotlin.reflect.KClass
import org.jetbrains.kotlin.idea.KotlinLanguage.INSTANCE as KotlinLanguage
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible

/**
 * A workaround that (temporarily) removes the IDE's built-in code folding implementations, to prevent tests from
 * depending on their behaviour.
 */
abstract class KotlinFoldingTestCase<T: FoldingBuilder>(private val builder: KClass<T>) : BasePlatformTestCase() {

	private lateinit var cacheKeyField: KeyProperty<FoldingBuilder>

	override fun setUp() {
		super.setUp()

		cacheKeyField = LanguageExtension::class.declaredMemberProperties
			.findIsInstanceAnd<KeyProperty<FoldingBuilder>> { it.name == "cacheKey" }!!
			.apply { isAccessible = true }
	}

	protected fun testFolding(file: String) {
		val key = cacheKeyField.get(LanguageFolding)
		val composite = LanguageFolding.forLanguage(KotlinLanguage)

		try {
			// Use the IDE-created instance of the desired FoldingBuilder
			KotlinLanguage.putUserData(key, LanguageFolding.forKey(KotlinLanguage).first { it::class == builder })

			myFixture.testFolding("$testDataPath/$file")
		} finally {
			KotlinLanguage.putUserData(key, composite)
		}
	}

	override fun tearDown() {
		try {
			cacheKeyField.isAccessible = false
		} finally {
			super.tearDown() // Must be the final call: uses reflection to set every field to null
		}
	}

	override fun getTestDataPath() = "src/test/testData/folding/bitwise"
}

private typealias KeyProperty<T> = KProperty1<LanguageExtension<T>, Key<T>>
