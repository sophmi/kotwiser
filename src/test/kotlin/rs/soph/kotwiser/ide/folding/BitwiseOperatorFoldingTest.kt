package rs.soph.kotwiser.ide.folding

import com.intellij.testFramework.TestDataPath

@TestDataPath("\$CONTENT_ROOT/testData/folding/bitwise")
internal class BitwiseOperatorFoldingTest : KotlinFoldingTestCase<BitwiseFoldingBuilder>(BitwiseFoldingBuilder::class) {

	fun testBinOpAssignments() {
		testFolding("BinOpAssignments.kt")
	}

	fun testBinOpFunctions() {
		testFolding("BinOpFunctions.kt")
	}
}
