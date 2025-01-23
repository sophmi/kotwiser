package rs.soph.kotwiser.ide.folding

import com.intellij.lang.ASTNode
import com.intellij.lang.folding.CustomFoldingBuilder
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.progress.ProgressManager
import com.intellij.psi.PsiElement
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.util.TextRange
import org.jetbrains.kotlin.idea.base.codeInsight.handlers.fixers.range
import org.jetbrains.kotlin.psi.KtBinaryExpression
import org.jetbrains.kotlin.psi.KtOperationReferenceExpression
import org.jetbrains.kotlin.psi.binaryExpressionRecursiveVisitor

internal class BitwiseFoldingBuilder : CustomFoldingBuilder(), DumbAware {

	override fun buildLanguageFoldRegions(
		descriptors: MutableList<FoldingDescriptor>,
		root: PsiElement,
		document: Document,
		quick: Boolean
	) {
		val visitor = binaryExpressionRecursiveVisitor { foldInfix(it)?.let(descriptors::add) }
		root.accept(visitor)
	}

	private fun foldInfix(expression: KtBinaryExpression): FoldingDescriptor? {
		ProgressManager.checkCanceled()

		val operator = expression.operationReference
		val replacement = FUNCTIONS[operator.text] ?: return null

		return createFold(operator, replacement)
	}

	private fun createFold(element: PsiElement, replacement: String): FoldingDescriptor {
		return FoldingDescriptor(
			element.node,
			element.range,
			/* group = */ null,
			/* dependencies = */ emptySet(),
			/* neverExpands = */ true,
			replacement,
			/* collapsedByDefault = */ true,
		)
	}

	// Never called, as this property is passed directly to the FoldingDescriptor
	override fun isRegionCollapsedByDefault(node: ASTNode): Boolean {
		return true
	}

	// Never called, as this property is passed directly to the FoldingDescriptor
	override fun getLanguagePlaceholderText(node: ASTNode, range: TextRange): String? {
		return when (node.psi) {
			is KtOperationReferenceExpression -> FUNCTIONS[node.text]
			else -> null
		}
	}
}

private val FUNCTIONS = mutableMapOf(
	"shl" to "<<",
	"shr" to ">>",
	"ushr" to ">>>",
	"and" to "&",
	"xor" to "^",
	"or" to "|",
)
