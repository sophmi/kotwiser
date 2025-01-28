// @formatter:off -- breaks the XML tags
public class BinOpFunctions.kt {
	val int: Int = 42
	val long: Long = 10L

	fun shlTwoConsts(): Int = 500 <fold text='<<'>shl</fold> 10
	fun shlConstLhs(): Int = 500 <fold text='<<'>shl</fold> int
	fun shlConstRhs(): Int = int <fold text='<<'>shl</fold> 10
	fun shlTwoVars(): Long = long <fold text='<<'>shl</fold> int
	fun shlChained(): Long = long <fold text='<<'>shl</fold> 32 <fold text='<<'>shl</fold> int

	fun shrTwoConsts(): Int = 500 <fold text='>>'>shr</fold> 10
	fun shrConstLhs(): Int = 500 <fold text='>>'>shr</fold> int
	fun shrConstRhs(): Int = int <fold text='>>'>shr</fold> 10
	fun shrTwoVars(): Long = long <fold text='>>'>shr</fold> int
	fun shrChained(): Long = long <fold text='>>'>shr</fold> 32 <fold text='>>'>shr</fold> int

	fun ushrTwoConsts(): Int = 500 <fold text='>>>'>ushr</fold> 10
	fun ushrConstLhs(): Int = 500 <fold text='>>>'>ushr</fold> int
	fun ushrConstRhs(): Int = int <fold text='>>>'>ushr</fold> 10
	fun ushrTwoVars(): Long = long <fold text='>>>'>ushr</fold> int
	fun ushrChained(): Long = long <fold text='>>>'>ushr</fold> 32 <fold text='>>>'>ushr</fold> int

	fun andTwoConsts(): Int = 500 <fold text='&'>and</fold> 10
	fun andConstLhs(): Int = 500 <fold text='&'>and</fold> int
	fun andConstRhs(): Int = int <fold text='&'>and</fold> 10
	fun andTwoVars(): Long = long <fold text='&'>and</fold> int
	fun andChained(): Long = long <fold text='&'>and</fold> 32 <fold text='&'>and</fold> int

	fun orTwoConsts(): Int = 500 <fold text='|'>or</fold> 10
	fun orConstLhs(): Int = 500 <fold text='|'>or</fold> int
	fun orConstRhs(): Int = int <fold text='|'>or</fold> 10
	fun orTwoVars(): Long = long <fold text='|'>or</fold> int
	fun orChained(): Long = long <fold text='|'>or</fold> 32 <fold text='|'>or</fold> int

	fun xorTwoConsts(): Int = 500 <fold text='^'>xor</fold> 10
	fun xorConstLhs(): Int = 500 <fold text='^'>xor</fold> int
	fun xorConstRhs(): Int = int <fold text='^'>xor</fold> 10
	fun xorTwoVars(): Long = long <fold text='^'>xor</fold> int
	fun xorChained(): Long = long <fold text='^'>xor</fold> 32 <fold text='^'>xor</fold> int
}
