# 为什么不直接使用 ```Array<Int>``` 而是 IntArray ？

## 基本类型居然有特殊待遇

其实我们用数组的场景不太多。有一天在做demo的时候，我突然意识到有 ```Array<Int>``` 和 IntArray 这两种完全不同的存在，当时我感觉非常的奇怪，为什么要再搞一个 IntArray 呢？不仅 IntArray，还有 DoubleArray、LongArray 等等，所有基本类型都有一个专属的 Array 版本，这背后一定有什么原因的。

偏偏巧了，kotlin-runtime 这个 jar 包里面对应的 kotlin.kotlin_builtins 这个文件没有源码，所以直接跳进去看到的 IntArray 的声明什么都没有。。

```kotlin
public final class IntArray public constructor(size: kotlin.Int) : kotlin.Cloneable {
    public constructor(size: kotlin.Int, init: (kotlin.Int) -> kotlin.Int) { /* compiled code */ }

    public final val size: kotlin.Int /* compiled code */

    public open fun clone(): kotlin.IntArray { /* compiled code */ }

    public final operator fun get(index: kotlin.Int): kotlin.Int { /* compiled code */ }

    public final operator fun iterator(): kotlin.collections.IntIterator { /* compiled code */ }

    public final operator fun set(index: kotlin.Int, value: kotlin.Int): kotlin.Unit { /* compiled code */ }
}
```

当时我就很纳闷，想来想去可能也就 IntIterator 特殊一点儿，点进去看看吧。

```kotlin
/** An iterator over a sequence of values of type `Int`. */
public abstract class IntIterator : Iterator<Int> {
    override final fun next() = nextInt()

    /** Returns the next value in the sequence without boxing. */
    public abstract fun nextInt(): Int
}
```

注意看，“without boxing” 这一句，如果大家对 Java 基本类型的装箱有所了解，看到这个肯定会立马明白什么的——Kotlin 为基本类型定制 Array 的目的是为了避免装箱？

## 真相总是藏在字节码里面

如果真是这个理由，那么这明显非常说得过去，毕竟装箱是有开销的。为了证实这个答案，我们只需要看下字节码就好了。我们分别用 kotlin 和 Java 写下下面的等价代码：

```kotlin
val intArray = intArrayOf(1,3,4)
```

```java
int[] array = new int[]{1,3,4};
```

使用 ASM Bytecode Outline 插件查看字节码，发现二者的结果几乎是完全一样的（当然除了行号之类）：

```
    LINENUMBER 7 L1
    ICONST_3
    NEWARRAY T_INT // 注意这里是构造int的数组
    DUP
    ICONST_0
    ICONST_1
    IASTORE
    DUP
    ICONST_1
    ICONST_3
    IASTORE
    DUP
    ICONST_2
    ICONST_4
    IASTORE
    ASTORE 1
    L2
```

而换个方式，如果我们用 ```Array<Int>``` ，会发生什么呢？

```kotlin
val arrayOfInt = arrayOf(1,3,4)
```

``` 
    LINENUMBER 8 L2
    ICONST_3
    ANEWARRAY java/lang/Integer
    DUP
    ICONST_0
    ICONST_1
    INVOKESTATIC java/lang/Integer.valueOf (I)Ljava/lang/Integer;
    AASTORE
    DUP
    ICONST_1
    ICONST_3
    INVOKESTATIC java/lang/Integer.valueOf (I)Ljava/lang/Integer;
    AASTORE
    DUP
    ICONST_2
    ICONST_4
    INVOKESTATIC java/lang/Integer.valueOf (I)Ljava/lang/Integer;
    AASTORE
    ASTORE 3
    NOP
    L3
```

这时候我们就能看到，其实是构造了一个 Integer[] 出来，每添加一个数字，就会调用 ```Integer.valueOf```，这就产生了装箱的开销。

## 编译器的答案

故事本来发展到这儿就应该结束了吧，毕竟我们得到了一个说得过去的答案，可偏偏我是挖掘机专业毕业的，我就是很好奇 Kotlin 运行的时候把 IntArray 给搞成了什么样子，于是我打开了 Kotlin 的源码（1.0.5），并在 core/builtins/native/kotlin/Arrays.kt 当中找到了 IntArray 的真身：

```kotlin
/**
 * An array of ints. When targeting the JVM, instances of this class are represented as `int[]`.
 * @constructor Creates a new array of the specified [size], with all elements initialized to zero.
 */
public class IntArray(size: Int) : Cloneable {
    /**
     * Creates a new array of the specified [size], where each element is calculated by calling the specified
     * [init] function. The [init] function returns an array element given its index.
     */
    public inline constructor(size: Int, init: (Int) -> Int)

    /** Returns the array element at the given [index]. This method can be called using the index operator. */
    public operator fun get(index: Int): Int
    /** Sets the element at the given [index] to the given [value]. This method can be called using the index operator. */
    public operator fun set(index: Int, value: Int): Unit

    /** Returns the number of elements in the array. */
    public val size: Int

    /** Creates an iterator over the elements of the array. */
    public operator fun iterator(): IntIterator

    public override fun clone(): IntArray
}
```

果然，这里面的注释明确地告诉我们 IntArray 最终是要编译为 int[] 的。可是，现在骗子这么多，我怎么知道注释不是逗我玩呢？

于是我开始翻 Kotlin 的源码，主要是编译器相关的代码。关于这块儿呢，目前我还没有理解的很透彻，不过答案应该是有了的。

Kotlin 的编译器运行的时候，主要依靠一个叫做 ```FqName``` 的类来识别我们代码运行时的类型，所谓 FqName，其实就是 Full Qualified Name，简单的说就是代码元素（主要是类）的全名，比如 String -> java.lang.String。

下面是 Kotlin 映射 ```IntArray``` 的一段代码：

**typeSignatureMapping.kt，mapBuiltInType方法**

```kotlin
    val arrayElementType = KotlinBuiltIns.getPrimitiveTypeByArrayClassFqName(fqName)
    if (arrayElementType != null) {
        return typeFactory.createFromString("[" + JvmPrimitiveType.get(arrayElementType).desc)
    }
```
在编译时，Kotlin 编译器如果遇到 IntArray，就会尝试映射这个类，运行到上面的代码时，fqName 的值是 "kotlin.IntArray"，而 arrayElementType 是 ```PrimitiveType``` 的一个实例 ```INT```，而这个 ```PrimitiveType``` 又是什么呢？

```java
public enum PrimitiveType {
    BOOLEAN("Boolean"),
    CHAR("Char"),
    BYTE("Byte"),
    SHORT("Short"),
    INT("Int"),
    FLOAT("Float"),
    LONG("Long"),
    DOUBLE("Double"),
    ;
	...
}
```

是 Kotlin 基本类型的一个枚举。

我们还是看刚才的映射代码，返回的究竟是什么呢？由于我们已经知道 ```arrayElementType``` 的值，也就不难得到 ```JvmPrimitiveType.get(arrayElementType).desc``` 实际上是 ```I```，这么说来返回的类型应该是 ```[I```——而这个，正是 ```int[]``` 的字节码类型。

## 小结

其实 Kotlin 中的不少类型都是对 Java 原有类型的扩展，当然有时候扩展不方便的话就直接在编译期做映射，类似的例子还有 kotlin.String -> Java.lang.String，kotlin.collections.MutableMap -> java.util.Map 等等。
如果跟高大上的设计模式、应用框架比起来，基本类型的数组问题，算不上一个“大”问题。不过，我希望通过本文传达给大家的一个信息是：“Kotlin 是开源哒，源码面前，了无秘密”。
