# 细说 Lambda 表达式

## 1. 什么是 Lambda 表达式

Lambda 表达式，其实就是匿名函数。而函数其实就是功能（function），匿名函数，就是匿名的功能代码了。在 Kotlin 当中，函数也是作为类型的一种出现的，尽管在当前的版本中，函数类型的灵活性还不如 Python 这样的语言，不过它也是可以被赋值和传递的，这主要就体现在 Lambda 表达式上。

我们先来看一个 Lambda 表达式的例子：

```kotlin
 fun main(args: Array<String>) { 
     val lambda = { 
         left: Int, right: Int 
         -> 
         left + right 
     } 
     println(lambda(2, 3)) 
 } 
```
大家可以看到我们定义了一个变量 lambda，赋值为一个 Lambda 表达式。Lambda 表达式用一对大括号括起来，后面先依次写下参数及其类型，如果没有就不写，接着写下 -> ，这表明后面的是函数体了，函数体的最后一句的表达式结果就是 Lambda 表达式的返回值，比如这里的返回值就是参数求和的结果。

后面我们用 () 的形式调用这个 Lambda 表达式，其实这个 () 对应的是 invoke 方法，换句话说，我们在这里也可以这么写：

```kotlin
 println(lambda.invoke(2,3)) 
```
这两种调用的写法是完全等价的。

毫无疑问，这段代码的输出应该是 5。

## 2 简化 Lambda 表达式

我们再来看个例子：

```kotlin
 fun main(args: Array<String>) { 
     args.forEach { 
      	if(it == "q") return 
      	println(it) 
     } 
     println("The End") 
 } 
```
args 是一个数组，我们已经见过 for 循环迭代数组的例子，不过我们其实有更现代化的手段来迭代一个数组，比如上面这个例子。这没什么可怕的，一旦撕下它的面具，你就会发现你早就认识它了：

```kotlin
 public inline fun <T> forEach(action: (T) -> Unit): Unit { 
     for (element in this) action(element) 
 } 
```
这是一个扩展方法，扩展方法很容易理解，原有类没有这个方法，我们在外部给它扩展一个新的方法，这个新的方法就是扩展方法。大家都把它当做 Array 自己定义的方法就好，我们看到里面其实就是一个 for 循环对吧，for 循环干了什么呢？调用了我们传入的Lambda表达式，并传入了每个元素作为参数。所以我们调用 forEach 方法时应该怎么写呢？

```kotlin
 args.forEach({ 
     element -> println(element) 
 }) 
```

这相当于什么呢？

```kotlin
 for(element in args){ 
 	println(element) 
 } 
```
很容易理解吧？

接着，Kotlin 允许我们把函数的最后一个Lambda表达式参数移除括号外，也就是说，我们可以改下上面的 forEach 的写法：

```kotlin
 args.forEach(){ 
     element -> println(element) 
 } 
```
看上去有点儿像函数定义了，不过区别还是很明显的。这时候千万不能晕了，晕了的话我这儿有晕车药吃点儿吧。

事儿还没完呢，如果函数只有这么一个 Lambda 表达式参数，前面那个不就是么，剩下一个小括号也没什么用，干脆也丢掉吧：

```kotlin
 args.forEach{ 
     element -> println(element) 
 } 
```
大家还好吧？你以为这就结束了？nonono，如果传入的这个Lambda表达式只有一个参数，还是比如上面这位 forEach，参数只有一个 element ，于是我们也可以在调用的时候省略他，并且默认它叫 it，说得好有道理，它不就是 it 么，虽然人家其实是 iterator 的意思：

```kotlin
 args.forEach{ 
      println(it) 
 } 
```

嗯，差不多了。完了没，没有。还有完没啊？就剩这一个了。如果这个 Lambda 表达式里面只有一个函数调用，并且这个函数的参数也是这个Lambda表达式的参数，那么你还可以用函数引用的方式简化上面的代码：

```kotlin
 args.forEach(::println) 
```
这有没有点儿像 C 里面的函数指针？函数也是对象嘛，没什么大惊小怪的，只要实参比如 println 的入参和返回值与形参要求一致，那么就可以这么简化。

总结一下：

1. 最后一个Lambda可以移出去 
2. 只有一个Lambda，小括号可省略 
3. Lambda 只有一个参数可默认为 it 
4. 入参、返回值与形参一致的函数可以用函数引用的方式作为实参传入

这样我们之前给的那个例子就大致能够看懂了吧：

```kotlin
 fun main(args: Array<String>) { 
     args.forEach { 
      	if(it == "q") return 
      	println(it) 
     } 
     println("The End") 
 } 
```

## 3 从 Lambda 中返回

真看懂了吗？假设我输入的参数是 

```
 o p q r s t 
```
你知道输出什么吗？

```
 o 
 p 
 The End 
```
对吗？

不对，return 会直接结束 main 函数。为啥？Lambda 表达式，是个表达式啊，虽然看上去像函数，功能上也像函数，可它看起来也不过是个代码块罢了。这就像琅琊榜前期，靖王虽然获得了自由进宫拜见母妃的特权，但他当时并不是亲王，而只是一个郡王一样。

那，就没办法 return 了吗？当然不是，兵来将挡水来土掩：

```kotlin
 fun main(args: Array<String>) { 
     args.forEach forEachBlock@{ 
      	if(it == "q") return@forEachBlock 
      	println(it) 
     } 
     println("The End") 
 } 
```

定义一个标签就可以了。你还可以在 return@forEachBlock 后面加上你的返回值，如果需要的话。

## 4 Lambda 表达式的类型

好，前面说到 Lambda 表达式其实是函数类型，我们在前面的 forEach 方法中传入的 Lambda 表达式其实就是 forEach 方法的一个参数，我们再来看下 forEach 的定义：

```kotlin
 public inline fun <T> Array<out T>.forEach(action: (T) -> Unit): Unit { 
     for (element in this) action(element) 
 } 
```
注意到，action 这个形参的类型是 (T) -> Unit，这个是 Lambda 表达式的类型，或者说函数的类型，它表示这个函数接受一个 T  类型的参数，返回一个 Unit 类型的结果。我们再来看几个例子：

```kotlin
 () -> Int //无参，返回 Int  
 (Int, Int) -> String //两个整型参数，返回字符串类型 
 (()->Unit, Int) -> Unit //传入了一个 Lambda 表达式和一个整型，返回 Unit 
```
我们平时就用这样的形式来表示 Lambda 表达式的类型的。有人可能会说，既然人家都是类型了，怎么就没有个名字呢？或者说，它对应的是哪个类呢？

```kotlin
 public interface Function<out R> 
```
其实所有的 Lambda 表达式都是 Function 的实现，这时候如果你问我，那 invoke 方法呢？在哪儿定义的？说出来你还真别觉得搞笑，Kotlin 的开发人员给我们定义了 23 个 Function 的子接口，其中 FunctionN 表示 invoke 方法有 n 个参数。。

```kotlin
 public interface Function0<out R> : Function<R> { 
     public operator fun invoke(): R 
 } 
 public interface Function1<in P1, out R> : Function<R> { 
     public operator fun invoke(p1: P1): R 
 } 
 ... 
 public interface Function22<in P1, in P2, in P3, in P4, in P5, in P6, in P7, in P8, in P9, in P10, in P11, in P12, in P13, in P14, in P15, in P16, in P17, in P18, in P19, in P20, in P21, in P22, out R> : Function<R> { 
     public operator fun invoke(p1: P1, p2: P2, p3: P3, p4: P4, p5: P5, p6: P6, p7: P7, p8: P8, p9: P9, p10: P10, p11: P11, p12: P12, p13: P13, p14: P14, p15: P15, p16: P16, p17: P17, p18: P18, p19: P19, p20: P20, p21: P21, p22: P22): R 
 } 
```
说实在的，第一看到这个的时候，我直接笑喷了，Kotlin 的开发人员还真是黑色幽默啊。

这事儿不能这么完了，万一我真有一个函数，参数超过了 22 个，难道 Kotlin 就不支持了吗？

```kotlin
 fun hello2(action: (Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int) -> Unit) { 
     action(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22) 
 } 
```
于是我们定义一个参数有 23 个的 Lambda 表达式，调用方法也比较粗暴：

```kotlin
 hello2 { i0, i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12, i13, i14, i15, i16, i17, i18, i19, i20, i21, i22 -> 
     println("$i0, $i1, $i2, $i3, $i4, $i5, $i6, $i7, $i8, $i9, $i10, $i11, $i12, $i13, $i14, $i15, $i16, $i17, $i18, $i19, $i20, $i21, $i22,") 
 } 
```
编译运行结果：

```kotlin
 Exception in thread "main" java.lang.NoClassDefFoundError: kotlin/Function23 
 	at java.lang.Class.getDeclaredMethods0(Native Method) 
 	at java.lang.Class.privateGetDeclaredMethods(Class.java:2701) 
 	at java.lang.Class.privateGetMethodRecursive(Class.java:3048) 
 	at java.lang.Class.getMethod0(Class.java:3018) 
```
果然，虽然这个参数有 23 个的 Lambda 表达式被映射成 kotlin/Function23 这个类，不过，这个类却不存在，也就是说，对于超过 22 个参数的 Lambda 表达式，Kotlin 代码可以编译通过，但会抛运行时异常。这当然也不是个什么事儿了，毕竟有谁脑残到参数需要 22 个以上呢？

## 5 SAM 转换

看名字挺高大上，用起来炒鸡简单的东西你估计见了不少，这样的东西你可千万不要回避，多学会一个就能多一样拿出去唬人。

```kotlin
 val worker = Executors.newCachedThreadPool() 
 　 
 worker.execute { 
     println("Hello") 
 } 
```

本来我们应该传入一个 Runnable 的实例的，结果用一个 Lambda 表达式糊弄过去，Java 怎么看？

```
 GETSTATIC net/println/MainKt$main$1.INSTANCE : Lnet/println/MainKt$main$1; 
 CHECKCAST java/lang/Runnable 
 INVOKEINTERFACE java/util/concurrent/ExecutorService.execute (Ljava/lang/Runnable;)V 
```
Java 说介叫嘛事儿，介不就一 Lambda 么，转成 Runnable 在拿过来！

你看上面的这三句字节码，第一句拿到了一个类的实例，这个类一看就是一个匿名内部类：

```kotlin
 final class net/println/MainKt$main$1 implements java/lang/Runnable  { 
 	... 
 } 
```
这是这个类定义的字节码部分，实现了 Runnable 接口的一个类！

第二句，拿到这个类的实例以后做强转——还转啥，直接拿来用呗，肯定没问题呀。

那你说 SAM 转换有什么条件呢？

* 首先，调用者在 Kotlin 当中，被调用者是 Java 代码。如果前面的例子当中 worker.execute(...) 是定义在 Kotlin 中方法，那么我们是不能用 SAM 转换的。
* 其次，参数必须是 Java 接口，也就是说，Kotlin 接口和抽象类、Java 抽象类都不可以。
* 再次，参数的 Java 接口必须只有一个方法。

我们再来举个 Android 中常见的例子：

```kotlin
 view.setOnClickListener{ 
 	view -> 
 	... 
 } 
```
view.setOnClickListener(...) 是 Java 方法，参数 OnClickListener 是 Java 接口，并且只有一个方法：

```java
 public interface OnClickListener { 
     void onClick(View v); 
 } 
```

## 6 小结

Lambda 表达式就是这么简单，简单的让人有点儿害怕。不知道大家有没有过这样的感觉，越是简单的东西用起来越复杂，不相信你回去翻一翻高中物理课本的牛顿第二定律。Lambda 表达式就是这样的东西，它能够极大的简化代码的书写，尽管一旦有了 Lambda 表达式的掺和，代码本身理解起来可就要稍微困难一些了，不过，因噎废食的事情想必大家也是不喜欢做的，对吧？

![](../../arts/kotlin扫码关注.png)