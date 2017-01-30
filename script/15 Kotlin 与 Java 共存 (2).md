# Kt15 Kotlin 与 Java 共存(二)

上一期我们简单讨论了几个 Java 调用 Kotlin 的场景，这一期我们主要讨论相反的情况。

## 1 属性

如果 Java 类存在类似 setXXX 和 getXXX 的方法，Kotlin 会聪明地把他们当做属性来使用，例如：

```java
public class DataClass {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
```
Kotlin 的访问也非常简单：

```kotlin
fun main(args: Array<String>) {
    val dataClass = DataClass()
    dataClass.id = 0
    println(dataClass.id)
}
```

## 2 空安全

空安全这个特性使得 Kotlin 对变量的值要求更严格了，由于 Java 变量通常没有这样的信息，因此 Kotlin 在访问 Java 变量或者 Java 方法时，变量、方法的参数和返回值的空与否由我们自己决定，编译器不再进行约束。例如定义这么一个 Java 类：

```java
public class NullSafetyJava {
    public String getData(){
        return null;
    }
}
```
在 Kotlin 当中访问它：

```kotlin
fun main(args: Array<String>) {
    val nullSafetyJava = NullSafetyJava()
    val data = nullSafetyJava.data
}
```
这个 data 的类型被称作 『平台类型（Platform Type）』，它既可以为可空类型，也可以为不可空类型，这一切取决于我们自己的决定。

```kotlin
val dataCanBeNull: String? = data
val dataCannotBeNull: String = data
```
这样在编译期是允许的，不过如果在运行时由于 data 为 null，那你就会遇到异常：

```bash
Exception in thread "main" java.lang.IllegalStateException: data must not be null
	at net.println.kt15.NullSafetyKt.main(NullSafety.kt:10)
```

如果继承 Java 类，父类的方法参数也是平台类型，也需要我们根据实际情况来判断是否可空。例如：

```java
public abstract class NullSafetyAbsClass {
    public abstract String formatDate(Date date);
}
```
在Kotlin中继承这个类时，formatDate 方法的参数和返回值的类型我们根据情况写：

```kotlin
class NullSafetySubClass : NullSafetyAbsClass(){
    override fun formatDate(date: Date?): String? {
        return date?.toString()
    }
}

fun main(args: Array<String>) {
    val nullSafetySubClass = NullSafetySubClass()
    val formattedDate: String? = nullSafetySubClass.formatDate(Date())
    println(formattedDate)
}
```
这样直接写可控类型当然是没有问题的。如果你确信它可以不为空，那么你也可以直接写不可控类型：

```kotlin
class NullSafetySubClass : NullSafetyAbsClass(){
    override fun formatDate(date: Date): String {
        return date.toString()
    }
}

fun main(args: Array<String>) {
    val nullSafetySubClass = NullSafetySubClass()
    val formattedDate: String = nullSafetySubClass.formatDate(Date())
    println(formattedDate)
}
```

当然，对于这种情况，我们在 Java 也已经开始采用一些『曲线救国』的方式来弥补这一不足，比如 采用 JetBrains 的 @Nullable 和 @NotNull 注解以及 Android 当中类似的注解支持，我们可以把我们的 Java 代码改写成这样：

```java
public abstract class NullSafetyAbsClass {
	public abstract @NotNull String formatDate(@NotNull Date date);
}
```

## 3 泛型

整体上来讲，Kotlin 的泛型与 Java 的没什么大的差别，Java 的 ? 在 Kotlin 中变成了 *，毕竟 ? 在 Kotlin 当中用处大着呢。另外，Java 泛型的上限下限的表示法在 Kotlin 当中变成了 out 和 in。

不过，由于 Java 1.5 之前并没有泛型的概念，为了兼容旧版本，1.5 之后的 Java 仍然允许下面的写法存在：

```java
List list = new ArrayList();
list.add("Hello");
list.add(1);
for (Object o : list) {
    System.out.println(o);
}
```
而对应的，在 Kotlin 当中要用 List<*> 来表示 Java 中的 List —— 这本身没什么问题。那么我们现在再看一段代码：

```java
public abstract class View<P extends Presenter>{
    protected P presenter;
}

public abstract class Presenter<V extends View>{
    protected V view;
}
```
这个其实是现在比较流行的 MVP 设计模式的一个简单的接口，我希望通过泛型来绑定 V-P，并且我可以通过泛型参数在运行时直接反射实例化一个 presenter 完成 V-P 的实例注入，这在 Java 当中是没有问题的。

那么在 Kotlin 当中呢？ 因为我们知道 View 和 Presenter 都有泛型参数的，所以我们在 Kotlin 当中就要这么写：

```kotlin
abstract class View<P : Presenter<out View<out Presenter<out View<...>>{
    protected abstract val presenter: P
}

abstract class Presenter<out V : View<out Presenter<out View<...>>>>{
    protected abstract val view: V
}
```
一直写下去，最终陷入了死循环。编译器给出的解释是：

>This type parameter violates the Finite Bound Restriction.

在 @zhangdatou 给我发邮件之前，我曾一直对此耿耿于怀，Kotlin 这么优秀的语言怎么还会有做不到的事情呢。原来不是做不到，而是我没有想到：

```kotlin
abstract class View<out P: Presenter<View<P>>>
abstract class Presenter<out V: View<Presenter<V>>>

class MyView: View<MyPresenter>()
class MyPresenter: Presenter<MyView>()
```
实际上我们需要 View 的泛型参数 P 只要是 Presenter 的子类即可，并且要求这个泛型参数 P 本身对应的泛型参数也需要是 View 的子类，而这个 View 其实就是最初的那个 View，那么这个 View 的泛型参数当然就是 P 了。有点儿绕，但这么写出来明显感觉比 Java 安全靠谱多了。



## 4 Synchronized 和 volatile

在 Kotlin 当中，这两个关键字被削去了王位，成为平民。也许是设计者们觉得这二位作为关键字出现有点儿太重了，虽然不再身居要职，不过却也是不可或缺。

Synchronized 有两个版本，用于函数的版本是个注解：

```kotlin
var num: Int = 0

@Synchronized fun count(){
    num++
}
```
用于代码块的则是一个函数：

```kotlin
var num: Int = 0

fun count(){
    synchronized(num){
        num++
    }
}
```

```kotlin
@kotlin.internal.InlineOnly
public inline fun <R> synchronized(lock: Any, block: () -> R): R {
    @Suppress("NON_PUBLIC_CALL_FROM_PUBLIC_INLINE", "INVISIBLE_MEMBER")
    monitorEnter(lock)
    try {
        return block()
    }
    finally {
        @Suppress("NON_PUBLIC_CALL_FROM_PUBLIC_INLINE", "INVISIBLE_MEMBER")
        monitorExit(lock)
    }
}

```

volatile 的命运差不多，也变成了一个注解：

```kotlin
@Volatile var num: Int = 0
```

## 5 SAM 转换
看名字一头雾水，其实就是对于只有一个方法的 Java 接口，Kotlin 可以用一个 Lambda 表达式来简化它的书写，例如：

```kotlin
fun main(args: Array<String>) {
    val worker = Executors.newCachedThreadPool()
    worker.execute {
        println(System.currentTimeMillis())
    }
}
```
execute 方法传入了一个 Runnable 接口的实例，不过看样子却是一个 Lambda 表达式。不过这里也是有一个需要注意的点的，既然是『转换』，那么 Java 的 execute 方法接受到的实例就一定不是我们看到的这个 Lambda 表达式实例了，换句话说，我们就算每次传入同一个 Lambda 表达式实例，那么 Java 的 execute 方法收到的也并不是同一个对象。举个例子：

```java
public class SAMInJava {
    private ArrayList<Runnable> runnables = new ArrayList<Runnable>();

    public void addTask(Runnable task){
        runnables.add(task);
        System.out.println("after add: " + task + ", we " + runnables.size() + " in all.");
    }

    public void removeTask(Runnable task){
        runnables.remove(task);
        System.out.println("after remove: " + task + ", only " + runnables.size() + " left.");
    }
}
```
然后我们在 Kotlin 当中这么写：

```kotlin
fun main(args: Array<String>) {
    val samInJava = SAMInJava()
    val lambda = {
        println("Hello")
    }

    samInJava.addTask(lambda)
    samInJava.addTask(lambda)
    samInJava.addTask(lambda)
    samInJava.addTask(lambda)

    samInJava.removeTask(lambda)
    samInJava.removeTask(lambda)
    samInJava.removeTask(lambda)
    samInJava.removeTask(lambda)
}
```
运行结果呢？

```
after add: net.println.kt15.SAMConversionKt$sam$Runnable$9855366b@6ce253f1, we have 1 in all.
after add: net.println.kt15.SAMConversionKt$sam$Runnable$9855366b@53d8d10a, we have 2 in all.
after add: net.println.kt15.SAMConversionKt$sam$Runnable$9855366b@e9e54c2, we have 3 in all.
after add: net.println.kt15.SAMConversionKt$sam$Runnable$9855366b@65ab7765, we have 4 in all.
after remove: net.println.kt15.SAMConversionKt$sam$Runnable$9855366b@1b28cdfa, only 4 left.
after remove: net.println.kt15.SAMConversionKt$sam$Runnable$9855366b@eed1f14, only 4 left.
after remove: net.println.kt15.SAMConversionKt$sam$Runnable$9855366b@7229724f, only 4 left.
after remove: net.println.kt15.SAMConversionKt$sam$Runnable$9855366b@4c873330, only 4 left.
```
每次 Java 的 add 和 remove 方法收到的都是不同的实例，所以 remove 方法根本没有起到作用。

## 6 小结

除了这些之外还有一些细节，比如异常的捕获，集合类型的映射等等，大家可以自行参考官方文档即可。在了解了这些之后，你就可以放心大胆的在你的项目中慢慢渗透 Kotlin，让你的代码逐渐走向简洁与精致了。

最后，作为这一系列视频的最后一集，我还想要告诉大家有关 Android 开发的视频可能会在年后开始筹备，公众号在后续的这段时间内会推送我为大家准备的 Kotlin 的一些有意思的文章，请大家继续关注，并与我互动，谢谢大家！