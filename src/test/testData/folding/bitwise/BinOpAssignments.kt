// @formatter:off -- breaks the XML tags
val int: Int = 42
val long: Long = 10L

val shlTwoConsts = 500 <fold text='<<'>shl</fold> 10
val shlConstLhs = 500 <fold text='<<'>shl</fold> int
val shlConstRhs = int <fold text='<<'>shl</fold> 10
val shlTwoVars = long <fold text='<<'>shl</fold> int
val shlChained = long <fold text='<<'>shl</fold> 32 <fold text='<<'>shl</fold> int

val shrTwoConsts = 500 <fold text='>>'>shr</fold> 10
val shrConstLhs = 500 <fold text='>>'>shr</fold> int
val shrConstRhs = int <fold text='>>'>shr</fold> 10
val shrTwoVars = long <fold text='>>'>shr</fold> int
val shrChained = long <fold text='>>'>shr</fold> 32 <fold text='>>'>shr</fold> int

val ushrTwoConsts = 500 <fold text='>>>'>ushr</fold> 10
val ushrConstLhs = 500 <fold text='>>>'>ushr</fold> int
val ushrConstRhs = int <fold text='>>>'>ushr</fold> 10
val ushrTwoVars = long <fold text='>>>'>ushr</fold> int
val ushrChained = long <fold text='>>>'>ushr</fold> 32 <fold text='>>>'>ushr</fold> int

val andTwoConsts = 500 <fold text='&'>and</fold> 10
val andConstLhs = 500 <fold text='&'>and</fold> int
val andConstRhs = int <fold text='&'>and</fold> 10
val andTwoVars = long <fold text='&'>and</fold> int
val andChained = long <fold text='&'>and</fold> 32 <fold text='&'>and</fold> int

val orTwoConsts = 500 <fold text='|'>or</fold> 10
val orConstLhs = 500 <fold text='|'>or</fold> int
val orConstRhs = int <fold text='|'>or</fold> 10
val orTwoVars = long <fold text='|'>or</fold> int
val orChained = long <fold text='|'>or</fold> 32 <fold text='|'>or</fold> int

val xorTwoConsts = 500 <fold text='^'>xor</fold> 10
val xorConstLhs = 500 <fold text='^'>xor</fold> int
val xorConstRhs = int <fold text='^'>xor</fold> 10
val xorTwoVars = long <fold text='^'>xor</fold> int
val xorChained = long <fold text='^'>xor</fold> 32 <fold text='^'>xor</fold> int
