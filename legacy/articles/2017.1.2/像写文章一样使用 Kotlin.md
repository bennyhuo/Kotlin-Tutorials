# 像写文章一样使用 Kotlin

> 我把所有文章和视频都放到了 [Github](https://github.com/enbandari/Kotlin-Tutorials) 上 ，如果你喜欢，请给个 Star，谢谢~

## 运算符重载

不知道大家有没有看到过下面的函数调用：

``` python
print "Hello World"
```

这样的感觉就好像在写文章，没有括号，让语言的表现力进一步增强。Groovy 和 Scala 就有这样的特性，那么 Kotlin 呢？

```kotlin 
for(i in 0..10){
	...
}
```

如果你在 Kotlin 中写下了这样的代码，你可能觉得很自然，不过你有没有想过 0..10 究竟是个什么？

“是个 IntRange 啊，这你都不知道。”你一脸嫌弃的回答道。

是啊，确实是个 IntRange，不过为什么是 0..10 返回个 IntRange，而不是 0->10 呢？

“我靠。。这是出厂设定，懂不懂。。”你开始变得更嫌弃了。。

额，其实我想说的是，你知不知道这其实是个运算符重载？！

```kotlin
public final operator 
	fun rangeTo(other: kotlin.Int)
	: kotlin.ranges.IntRange { 
	...
}
```
没错，Kotlin 允许我们对运算符进行重载，所以你甚至可以给 String 定义 rangeTo 方法。

## 去掉方法的括号

“毕竟运算符是有限的吧？如果说我想给 Person 增加个 say 方法，不带括号那种，怎么办？” 你不以为然地说。

这个嘛。。当然也是可以哒！

```kotlin
class Person(val name: String){
    infix fun 说(word: String){
        println("\"$name 说 $word\"")
    }
}

fun main(args: Array<String>) {
    val 老张 = Person("老张")
     老张 说 "小伙砸,你咋又调皮捏!"
}
```

这段代码执行结果是：

```
老张 说 "小伙砸,你咋又调皮捏!"
```

这个看上去有有点儿意思不？代码跟输出是一样滴！有人会说，中文变量和函数名真的大丈夫？是滴，全然大丈夫，这是因为 Java、Kotlin 的字节码都采用 Unicode，中文确实是可以的——不过，Java 还支持中文包名和文件名，这在 Kotlin 当中还是有些问题的。

接着说，通常我们的方法传参是需要括号的，为什么这里不需要了呢？因为 infix 这个关键字！这里跟 Scala 和 Groovy 不同，Kotlin 只有显示的声明为 infix 的只有一个参数的方法才可以这么玩，如果你不显示声明，想去括号门儿都没有。

## 聊聊 DSL 

好，抛开老张的例子不谈，毕竟真正生产环境下，谁会去用中文呢。什么情况下我们需要 infix？当然是 DSL 中。我们看一段 Gradle 配置：

```groovy
apply plugin: 'kotlin'
```
看上去很有表现力是吧，即使你不懂 groovy 语法，也能直接看懂这句话就是使用 kotlin 插件。可它本质上还是句 groovy 代码，所以它的结构是怎样的呢？

```java
void apply(Map<String, ?> config);
```

实际上，apply 是 PluginAware 的一个方法，后面的 plugin 呢？ 那不过是一个 k-v 对而已，在 groovy 当中， K:V 的形式可以表示一个键值对。那既然参数是 Map，那我要是多传几个参数是不是也可以呢？

```groovy
apply ([plugin: 'kotlin', 宝宝: "不开心"])
```

不过这样虽然多传了一个键值对，不过由于 gradle 并不关心 “宝宝”，所以 “宝宝：不开心”那也没有办法了，说了也白说。哈哈。

前面的 DSL 是 groovy 版本的，再回到 Kotlin 当中，如果我们编写 DSL 代码，据说 Kotlin 版本的 gradle 也已经在开发中了，那么我们猜猜它会长成啥样？

```kotlin
apply mapOf("plugin" to "kotlin")
```

用 Kotlin 的 K to V 的方式传入一个 Pair。这里的 apply 的声明就应该是：

```kotlin
infix fun apply(config: Map<String, Any>)
```

很丑？没关系，我们为什么不直接搞一个方法叫做 applyPlugin 呢？

```kotlin
infix fun applyPlugin(pluginName: String)
```
于是：

```kotlin
applyPlugin "kotlin"
```

当然，作为静态语言，与 groovy 当然不能照搬了，所以最理想的实现肯定是强类型约束，比如

```kotlin
apply<KotlinPlugin>()
```
不过，这个我们就不谈了，跑题了。

## 小结

infix 是一个非常有用的关键字，让你的代码看起来像一篇文章一样，你不比再为前后括号匹配着慌，每一个单词其实都是方法调用。