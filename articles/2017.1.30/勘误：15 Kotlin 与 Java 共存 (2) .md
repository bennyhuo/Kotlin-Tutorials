# 勘误：15 Kotlin 与 Java 共存 (2) 

在过去推送过的第15期视频中，涉及到一个泛型对比的点，当时文中提到“对于循环引用泛型参数的情况，Kotlin 无法实现”的结论是有问题的。感谢 @zhangdatou 指正，对应部分的内容修改如下：

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

