[toc]

最近经常会收到一些 “用 Kotlin 怎么写” 的问题，作为有经验的程序员，我们已经掌握了一门或者多门语言，那么学 Kotlin 的时候就经常会有类似 “ ‘再见’用日语怎么说？”、“ ‘你好’ 用西班牙语怎么说？” 的问题，所以我决定把一些常用的语法对照列举出来，如果大家熟悉 Java，那么快速上手 Kotlin 会变得非常地容易。

这篇文章主要是写给需要快速上手 Kotlin 的 Java 程序员看的，这时候他们关注的是如何 Kotlin 写出类似某些 Java 的写法，所以本文基本不涉及 Kotlin 的高级特性。

## 1. 如何定义变量

Java 定义变量的写法：

```java
String string = "Hello";
```

基本等价的 Kotlin 定义变量的写法：

```kotlin
var string: String = "Hello"
```

Java 定义 final 变量的写法：

```java
final String string = "Hello";
```

注意到前面的是一个编译期常量，Kotlin 当中应该这么写：

```kotlin
const val string: String = "Hello"
```

同样是 final 变量，Java 这么写：

```java
final String string = getString();
```

注意到，这个不是编译期常量，Kotlin 这么写：

```kotlin
val string: String = getString()
```

另外， Kotlin 有类型推导的特性，因此上述变量定义基本上都可以省略掉类型 String。

## 2. 如何定义函数

Java 当中如何定义函数，也就是方法，需要定义到一个类当中：

```java
public boolean testString(String name){
	...
}
```

等价的 Kotlin 写法：

```kotlin
fun testString(name: String): Boolean {
	...
}
```

注意到返回值的位置放到了参数之后。

## 3. 如何定义静态变量、方法

Java 的静态方法或者变量只需要加一个 static 即可：

```java 
public class Singleton{
	private static Singleton instance = ...;

	public static Singleton getInstance(){
		...
		return instance;
	}
}
```

用 Kotlin 直译过来就是：

```kotlin
class KotlinSingleton{
    companion object{
        private val kotlinSingleton = KotlinSingleton()

        @JvmStatic
        fun getInstance() = kotlinSingleton

    }
}
```
注意 getInstance 的写法。 JvmStatic 这个注解会将 getInstance 这个方法编译成与 Java 的静态方法一样的签名，如果不加这个注解，Java 当中无法像调用 Java 静态方法那样调用这个方法。

另外，对于静态方法、变量的场景，在 Kotlin 当中建议使用包级函数。

## 4. 如何定义数组

Java 的数组非常简单，当然也有些抽象，毕竟是编译期生成的类：

```java
String[] names = new String[]{"Kyo", "Ryu", "Iory"};
String[] emptyStrings = new String[10];
```
Kotlin 的数组其实更真实一些，看上去更让人容易理解：

```kotlin
val names: Array<String> = arrayOf("Kyo", "Ryu", "Iory")
val emptyStrings: Array<String?> = arrayOfNulls(10)
```
注意到，Array<T> T 即数组元素的类型。另外，String? 表示可以为 null 的 String 类型。

数组的使用基本一致。需要注意的是，为了避免装箱和拆箱的开销，Kotlin 对基本类型包括 Int、Short、Byte、Long、Float、Double、Char 等基本类型提供了定制版数组类型，写法为 XArray，例如 Int 的定制版数组为 IntArray，如果我们要定义一个整型数组，写法如下：

```kotlin
val ints = intArrayOf(1, 3, 5)
```


## 5. 如何写变长参数

Java 的变长参数写法如下：

```java
void hello(String... names){
	...
}
```

Kotlin 的变长参数写法如下：

```kotlin
fun hello(vararg names: String){
    
}
```

## 6. 如何写三元运算符

Java 可以写三元运算符：

```java
int code = isSuccessfully? 200: 400;
```

很多人抱怨 Kotlin 为什么没有这个运算符。。。据说是因为 Kotlin 当中 : 使用的场景比 Java 复杂得多，因此如果加上这个三元运算符的话，会给语法解析器带来较多的麻烦，Scala 也是类似的情况。那么这中情况下，我们用 Kotlin 该怎么写呢？

```kotlin
int code = if(isSuccessfully) 200 else 400
```

注意到，if else 这样的语句也是表达式，这一点与 Java 不同。

## 7. 如何写 main 函数

Java 的写法只有一种：

```java
class Main{
	public static void main(String... args){
		...
	}
}
```
注意到参数可以是变长参数或者数组，这二者都可。

对应 Kotlin，main 函数的写法如下：

```kotlin
class KotlinMain{
    companion object{
        @JvmStatic
        fun main(args: Array<String>) {

        }
    }
}
```
Kotlin 可以有包级函数，因此我们并不需要声明一个类来包装 main 函数：

```kotlin
fun main(args: Array<String>){
	...
}
```

## 8. 如何实例化类

Java 和 C++ 这样的语言，在构造对象的时候经常需要用到 new 这个关键字，比如：

```java
Date date = new Date();
```

Kotlin 构造对象时，不需要 new 这个关键字，所以上述写法等价于：

```kotlin
val date = Date()
```

## 9. 如何写 Getter 和 Setter 方法

Java 的 Getter 和 Setter 是一种约定俗称，而不是语法特性，所以定义起来相对自由：

```java
public class GetterAndSetter{
    private int x = 0;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }
}
```

Kotlin 是有属性的：

```kotlin
class KotlinGetterAndSetter{
    var x: Int = 0
        set(value) { field = value }
        get() = field
}
```

注意看到，我们为 x 显式定义了 getter 和 setter，field 是 x 背后真正的变量，所以 setter 当中其实就是为 field 赋值，而 getter 则是返回 field。如果你想要对 x 的访问做控制，那么你就可以通过自定义 getter 和 setter 来实现了：

```kotlin
class KotlinGetterAndSetter{
    var x: Int = 0
        set(value) {
            val date = Calendar.getInstance().apply {
                set(2017, 2, 18)
            }
            if(System.currentTimeMillis() < date.timeInMillis){
                println("Cannot be set before 2017.3.18")
            }else{
                field = value
            }
        }
        get(){
            println("Get field x: $field")
            return field
        }   
}
```

## 10. 如何延迟初始化成员变量

Java 定义的类成员变量如果不初始化，那么基本类型被初始化为其默认值，比如 int 初始化为 0，boolean 初始化为 false，非基本类型的成员则会被初始化为 null。

```java
public class Hello{
	private String name;
}
```
类似的代码在 Kotlin 当中直译为：

```kotlin
class Hello{
    private var name: String? = null
}
```

使用了可空类型，副作用就是后面每次你想要用 name 的时候，都需要判断其是否为 null。如果不使用可控类型，需要加 lateinit 关键字：

```kotlin
class Hello{
    private lateinit var name: String
}
```
lateinit 是用来告诉编译器，name 这个变量后续会妥善处置的。

对于 final 的成员变量，Java 要求它们必须在构造方法或者构造块当中对他们进行初始化：

```java
public class Hello{
	private final String name = "Peter";
}
```
也就是说，如果我要想定义一个可以延迟到一定实际再使用并初始化的 final 变量，这在 Java 中是做不到的。

Kotlin 有办法，使用 lazy 这个 delegate 即可：

```kotlin
class Hello{
	private val name by lazy{
		NameProvider.getName() 
	}
}
```
只有使用到 name 这个属性的时候，lazy 后面的 Lambda 才会执行，name 的值才会真正计算出来。


## 11. 如何获得 class 的实例

Java 当中：

```java
public class Hello{
	...
}

...

Class<?> clazz = Hello.class;

Hello hello = new Hello();
Class<?> clazz2 = hello.getClass();
```

前面我们展示了两种获得 class 的途径，一种直接用类名，一种通过类实例。刚刚接触 Kotlin 的时候，获取 Java Class 的方法却是容易让人困惑。

```kotlin
class Hello

val clazz = Hello::class.java

val hello = Hello()
val clazz2 = hello.javaClass
```
同样效果的 Kotlin 代码看上去确实很奇怪，实际上 Hello::class 拿到的是 Kotlin 的 KClass，这个是 Kotlin 的类型，如果想要拿到 Java 的 Class 实例，那么就需要前面的办法了。

<center>![](../../arts/kotlin扫码关注.png)</center>