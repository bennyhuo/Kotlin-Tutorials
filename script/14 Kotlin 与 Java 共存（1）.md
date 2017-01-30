# Kt14 Kotlin 与 Java 共存(一)

大家好，经过前面的课程，相信大家对 Kotlin 已经有了一个初步的认识，那么我们在项目中究竟应该怎么应用 Kotlin 呢？

首先，我们的项目基本上都是使用 Java 编写的，我们没有精力也没有必要去全部用 Kotlin 重写。其次，Java 作为一门历经考验的语言，自然有它存在的道理，Kotlin 作为崭露头角的新秀，自然也有它发力的方向，我们没必要舍弃哪个，而是让他们共存，各取所长。正像 Kotlin 的设计理念一样：

>100% interoperable with Java™

比如，在编写 JavaBean 或者说数据结构类时，用 Java 写起来就要繁琐一些，这样的类我比较倾向于用 Kotlin 编写；编写上层代码时，经常会用到一些接口回调，通常这些回调也只有一个方法，于是我也倾向于使用 Kotlin 编写 —— 而这在 Android 的 UI 层代码中体现的尤为明显；编写 TestCase 也是比较倾向于用 Kotlin 的，我最早认识 Kotlin 就是在编译 Intellij 的源码的时候，他们在两三年前的源码中就开始大量使用 Kotlin 编写 TestCase 了。

再比如，对于一些较为底层的框架性代码，涉及到较多比较 Tricky 的代码时，我更喜欢用 Java 写，因为 Java 对于变量类型、构造方法、泛型参数的限制要小一些等等。

总体来讲就是，你想要追求代码简洁、美观、精致，你应该倾向于使用 Kotlin，而如果你想要追求代码的功能强大，甚至有些黑科技的感觉，那 Java 还是当仁不让的。

说了这么多，还是那句话，让他们共存，各取所长。

那么问题来了，怎么共存呢？虽然一说理论我们都知道，跑在 Jvm 上面的语言最终都是要编成 class 文件的，在这个层面大家都是 Java 虚拟机的字节码，可他们在编译之前毕竟还是有不少差异的，这可如何是好？

正所谓兵来将挡水来土掩，有多少差异，就要有多少对策，这一期我们先讲**在 Java 中调用 Kotlin**

##1 属性
我们在 Kotlin 中编写了一个类，当中有一个属性：

``` kotlin
data class Person(var name: String)
```

我们在 Java 中要怎么使用呢？

```java
Person person = new Person("benny");
System.out.println(person.getName());//benny
person.setName("andy");
System.out.println(person.getName());//andy
```
当然，在 Java 看来，name 这个成员是可以为 null 的，不过如果你胆敢设置一个 null 进去，信不信 Kotlin 跟你抱怨：

```bash
Exception in thread "main" java.lang.IllegalArgumentException: Parameter specified as non-null is null: method net.println.kt14.Person.setName, parameter <set-?>
	at net.println.kt14.Person.setName(Person.kt)
	at net.println.kt14.PersonMain.main(PersonMain.java:13)
```

其实，对于 null 的检查也没什么特别的，用我之前教大家的办法看下 Kotlin 的字节码：

```bytecode
INVOKESTATIC kotlin/jvm/internal/Intrinsics.checkParameterIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V

```
原来是调用了 Intrinsics.checkParameterIsNotNull 方法，这方法很简单的，大家都可以写得出来：

```java
public static void checkParameterIsNotNull(Object value, String paramName) {
    if (value == null) {
        throwParameterIsNullException(paramName);
    }
}
```

其实所有空安全的秘密都在这个类里面了，看到 Kotlin 的背后站着强大的 Java，我真是感到欣慰。

那么话说究竟能不能像在 Java 当中那样直接访问 Kotlin 的属性呢？

```kotlin
data class Person(var name: String, @JvmField var age: Int)
```
我们看到我们的 Person 有年龄啦，不过它与 name 不太一样，多了一个 @JvmField 的注解。

```java
Person person = new Person("benny", 27);
System.out.println(person.getName() + " is " + person.age); //benny is 27
person.setName("andy");
person.age = 26;
System.out.println(person.getName() + " is " + person.age); //andy is 26
```

看，这就跟在 Java 当中的一样了。所谓有得必有失，用 @JvmField 标注的属性是不可以声明为 private 的，同时也是不可以像其他 Kotlin 属性那样直接自定义 getter 和 setter 的，你只能像 Java 那样自己写：

```kotlin
...
fun getAge(): Int = age

fun setAge(value: Int){
	age = value
}
...
```

仔细想想，这实在是多此一举了。

##2 object
我们知道在 Kotlin 当中最简单的单例就是 object 了，可是 Java 并没有这样的特性。那我们要怎么访问 object 呢？

```kotlin
object Singleton{
	fun printHello(){
		println("Hello")
	}
}
```

如果大家看过之前的单例那一期，我们给大家看了 object 的字节码：

```bytecode
public final static Lnet/println/kt14/Singleton; INSTANCE
```

它实际上生成了一个静态实例 INSTANCE，所以我们在 Java 当中访问一个 Kotlint object 也就很简单了：

```java
Singleton.INSTANCE.printHello();

```

##3 默认参数的方法

Kotlin 的方法可以有默认参数，这样可以省掉很多方法的重载（我们把重写继承自父类的方法叫做覆写 override，名字相同参数不同的方法叫做重载 overload），可 Java 是没有这个特性的。Kotlin 的默认参数通常在 Java 当中是被忽略掉的，例如我们定义这样一个 Kotlin 类：

```kotlin
class Overloads{

	fun overloaded(a: Int, b: Int = 0, c: Int = 1){
		...
	}

}
```
在 Java 中访问它的带有默认参数的方法时，必须传入完整的实参列表。

```java
new Overloads().overloaded(0, 0, 1);
```

不过，现实也不是如此的残酷，如果我们稍作修改，事情就会好起来：


```kotlin
class Overloads{
	
	@JvmOverloads
	fun overloaded(a: Int, b: Int = 0, c: Int = 1){
		...
	}

}
```

这样的话，在 Java 看来 overloaded 方法就多了两个小兄弟，分别是：

```kotlin
...
fun overloaded(a: Int, b: Int = 0)

fun overloaded(a: Int)
...

```

这样的话，我们在 Java 中也可以愉快地使用默认参数带来的便利了。

##4 包方法

Java 没有包方法，如果有的话，倒也没那么多事儿了。 Kotlin 的包方法会被**默认**编译到一个名为：包名+KT 的类当中，比如：

**Package.kt**

``` kotlin
package net.println.kt14

fun printHello(){
    println("Hello")
}
```
编译完之后的字节码反编译成 Java 之后是：

```java
package net.println.kt14;

import kotlin.Metadata;

@Metadata(
   mv = {1, 1, 1},
   bv = {1, 0, 0},
   k = 2,
   d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u001a\u0006\u0010\u0000\u001a\u00020\u0001¨\u0006\u0002"},
   d2 = {"printHello", "", "production sources for module Kt14_main"}
)
public final class PackageKt {
   public static final void printHello() {
      String var0 = "Hello";
      System.out.println(var0);
   }
}
```
所以如果我们想要调用这样的方法，就可以像普通静态方法一样引用就可以了：

```java
public class CallPackageMethod {
    public static void main(String... args) {
        PackageKt.printHello();
    }
}
```

## 5 扩展方法

扩展方法实际上更像是一种语法糖，本质上其实是第一个参数为扩展类的实例而已。比如我们为 String 写了一个扩展方法：

**ExtensionMethod.kt**

```kotlin
fun String.notEmpty(): Boolean{
    return this != "" //注意 Kotlin 的字符串比较与 Java 的差别
}
```

我们在 Java 当中怎么访问这个方法呢？

```java
public class CallExtenstionMethod {
    public static void main(String... args) {
        System.out.println(ExtensionMethodKt.notEmpty("Hello"));
    }
}
```


## 6 Internal 的类和成员

我在之前的视频当中一直没有提到过的一个点：Kotlin 其实对访问权限做了调整。除了把默认访问权限改为 public 之外，还提供了一个模块内可见的 internal。一旦某个成员或者类被标记为 internal，那么模块之外的类是无法访问到这个成员或者类的，而对于模块内的其他成员或者类来说，它们则相当于 public —— 这个特性对于 sdk 开发者来说是相当友好的，举个例子，Android 源码中经常会有被标注为 @Hide 的成员，这些成员在 android.jar 当中不会有，不过他们却存在于 framework 的源码中，如果 Java 有 internal 这样的访问控制能力，那么 Android SDK 的开发者大可不必费尽周折搞出个 @Hide 注解并在打包 android.jar 的时候去掉这些成员。

介绍完 internal 之后，我们来看看模块内的 Java 代码如何访问 Kotlin 中的 internal 成员或者类，首先我们定义一个类用 internal 修饰。

```kotlin
internal class InternalClass {
    fun printHello(){
        println("Hello")
    }
}
```
在模块内写一些 Java 方法来访问它：

```java
public class CallInternalClass {
    public static void main(String... args) {
        InternalClass internalClass = new InternalClass();
        internalClass.printHello();
    }
}
```
没有问题的，那模块外呢？我们把同样的代码放到了另外一个模块当中，这个模块依赖了我们之前的模块，发现这段 Java 代码仍然可以用，当然，Kotlin 只有在模块内才可以访问到 InternalClass 这个类。那这是不是说 Kotlin 在兼容 Java 方面有缺陷呢？结论不要下的太早，你就算能访问到这个类，又有什么用呢？

如果我们稍微改一下 InternalClass：

```kotlin
internal class InternalClass {
    internal fun printHello(){
        println("Hello")
    }
}
```
结果我们发现，Java 代码无论在模块内还是模块外，都无法访问到 printHello 方法（尽管编译器提示有个叫 printHello$production_sources_for_module_Kt14_Kt14_main
，字节码当中也确实有这个方法，不过我们还是无法编译通过。

**结论就是，internal 修饰符与 Java 的兼容性方法比较差，如果你的项目中有 Java 代码依赖 Kotlin 代码，那么被依赖的部分需要慎用 internal。**

## 7 小结

总体来讲，Java 依赖 Kotlin 的代码并不是件难事，绝大多数的场景我们并不会觉得二者混用在一起会有什么不舒服，相反，时间久了，你甚至会觉察不到二者的共存。这一期视频就到这里，下一期，我们讲如何在 Kotlin 当中调用 Java，谢谢大家。




