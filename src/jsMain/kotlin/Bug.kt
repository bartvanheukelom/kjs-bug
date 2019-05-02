package foobar

// copied from .jsClass which is an error now, for reasons I don't get :)
val <T : Any> T.constructor: dynamic get() =
            when (jsTypeOf(this)) {
                "string" -> js("String")
                "number" -> js("Number")
                "boolean" -> js("Boolean")
                else -> js("Object").getPrototypeOf(this).constructor
            }

data class StringBox(val inside: String)

class BuggyClass(private val strings: List<String>) {

    init {
        strings.forEachIndexed { i, vv ->
            @Suppress("USELESS_IS_CHECK")
            if (vv !is String) throw IllegalArgumentException("${"${"strings"}[$i]"} should be ${String::class.simpleName} but is ${vv.constructor.name}")
        }
    }

    // making this constructor the primary makes the bug disappear
    constructor(s: List<StringBox>) : this(
            strings = s
                    .map(StringBox::inside) // using this line will make strings inside list containing PublicThingState BAD
//                    .map { it.inside }      // using this line will make strings inside list containing String           GOOD
    )
}

// using this instead of the secondary BuggyClass constructor works fine.
//fun fakeConstructor(s: List<StringBox>) = BuggyClass(
//    strings = s.map(StringBox::inside)
//)

@Suppress("unused") // called from index.js
object TestData {
    fun make() = BuggyClass(listOf(StringBox("Lorem ipsum")))
}
