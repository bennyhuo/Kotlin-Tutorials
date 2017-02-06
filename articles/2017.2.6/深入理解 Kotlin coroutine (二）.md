# 深入理解 Kotlin coroutine (二）

上周我们把 Kotlin Coroutine 的基本 API 挨个讲了一下，也给出了一些简单的封装。

真是不要太给脸，就在前几天发布的 1.1 Beta 2 当中，所有协程的 API 包名后面都加了一个 experimental，这意味着 Kotlin 官方在 1.1 当中还是倾向于将 Coroutine 作为一个实验性质的特性的，不过，这也没关系，我们学习的心不以外界的变化而变化不是？

这一篇我们基于前面的基础来了解一下 Kotlinx.coroutines 这个库的使用，如果大家对它的实现原理有兴趣，可以再读一读上一篇文章，我们也可以在后面继续写一些文章来给深入地大家介绍。

## 1. 准备工作

就像前面我们说到的，1.1 Beta 2 当中协程相关的基础库的包名都增加了 experimental，所以我们在选择 kotlinx.coroutines 的版本的时候也一定要对应好编译器的版本，不然。。。你自己想哈哈。

我们强调一下，kotlin 的版本选择 1.1.0-beta-38，kotlinx.coroutines 的版本选择 0.6-beta，如果你恰好使用 gradle，那么告诉你一个好消息，我会直接告诉你怎么配置：

```kotlin
 buildscript { 
     ext.kotlin_version = '1.1.0-beta-38' 
 　 
     repositories { 
     	jcenter() 
 　 
 	    maven { 
 	        url "http://dl.bintray.com/kotlin/kotlin-eap-1.1" 
 	    } 
 	} 
 　 
 	... 
 } 
 　 
 repositories { 
 	jcenter() 
 　 
     maven { 
         url "http://dl.bintray.com/kotlin/kotlin-eap-1.1" 
     } 
 } 
 　 
 kotlin { 
     experimental { 
         coroutines 'enable' 
     } 
 } 
 　 
 dependencies { 
     compile "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version" 
     compile 'org.jetbrains.kotlinx:kotlinx-coroutines-core:0.6-beta' 
 } 
```

## 2. 一个基本的协程的例子

这个例子是 kotlinx.coroutines 的第一个小例子。

```kotlin
 fun main(args: Array<String>) { 
     launch(CommonPool) { // create new coroutine in common thread pool 
         delay(1000L) // non-blocking delay for 1 second (default time unit is ms) 
         println("World!") // print after delay 
     } 
     println("Hello,") // main function continues while coroutine is delayed 
     Thread.sleep(2000L) // block main thread for 2 seconds to keep JVM alive 
 } 
```
这个例子的运行结果是：

```
 Hello, 
 World! 
```
其实有了上一篇文章的基础我们很容易知道，launch 方法启动了一个协程，CommonPool 是一个有线程池的上下文，它可以负责把协程的执行分配到合适的线程上。所以从线程的角度来看，打印的这两句是在不同的线程上的。

```kotlin
 20170206-063015.015 [main] Hello, 
 20170206-063016.016 [ForkJoinPool.commonPool-worker-1] World! 
```
这段代码的执行效果与线程的版本看上去是一样的：

```kotlin
 thread(name = "MyThread") {  
     Thread.sleep(1000L)  
     log("World!")  
 } 
 log("Hello,")  
 Thread.sleep(2000L)  
```

## 3. 主线程上的协程

我们刚才通过 launch 创建的协程是在 CommonPool 的线程池上面的，所以协程的运行并不在主线程。如果我们希望直接在主线程上面创建协程，那怎么办？

```kotlin
 fun main(args: Array<String>) = runBlocking<Unit> {  
     launch(CommonPool) {  
         delay(1000L) 
         println("World!") 
     } 
     println("Hello,")  
     delay(2000L)  
 } 
```

这个还是 kotlinx.coroutines 的例子，我们来分析一下。runBlocking 实际上也跟 launch 一样，启动一个协程，只不过它传入的 context 不会进行线程切换，也就是说，由它创建的协程会直接运行在当前线程上。

在 runBlocking 当中通过 launch 再创建一个协程，显然，这段代码的运行结果与上一个例子是完全一样的。需要注意的是，尽管我们可以在协程中通过 launch 这样的方法创建协程，但不要再协程当中通过 runBlocking 再来创建协程，因为这样做虽然一般来说不会导致程序异常，不过，这样的程序也没有多大意义：

```kotlin
 fun main(args: Array<String>) = runBlocking<Unit> { 
     runBlocking { 
         delay(1000L) 
         println("World!") 
     } 
     println("Hello,") 
 } 
```
运行结果：

```kotlin
 World! 
 Hello, 
```
大家看到了，嵌套的 runBlocking 实际上仍然只是一段顺序代码而已。

那么，让我们再仔细看看前面的例子，不知道大家有没有问题：如果我在 launch 创建的协程当中多磨叽一会儿，主线程上的协程 delay(2000L) 好像也没多大用啊。有没有什么方法保证协程执行完？

## 4. 外部控制协程

我们在上一篇文章当中只是对内置的基础 API 进行了简单的封装，而 kotlinx.coroutines 却为我们做了非常多的事情。比如，每一个协程都看做一个 Job，我们在一个协程的外部也可以控制它的运行。

```kotlin
 fun main(args: Array<String>) = runBlocking<Unit> { 
     val job = launch(CommonPool) {  
         delay(1000L) 
         println("World!") 
     } 
     println("Hello,") 
     job.join()  
 } 
```
job.join 其实就是要求当前协程等待 job 执行完成之后再继续执行。

其实，我们还可以取消协程，让他直接停止执行：

```kotlin
 fun main(args: Array<String>) = runBlocking<Unit> { 
     val job = launch(CommonPool) {  
         delay(1000L) 
         println("World!") 
     } 
     println("Hello,") 
     job.cancel()  
 } 
```

job.cancel 会直接终止 job 的执行。如果 job 已经执行完毕，那么 job.cancel 的执行时没有意义的。我们也可以根据 cancel 的返回值来判断是否取消成功。

另外，cancel 还可以提供原因：

```kotlin
 job.cancel(IllegalAccessException("World!")) 
```
如果我们提供了这个原因，那么被取消的协程会将它打印出来。

```kotlin
 Hello, 
 Exception in thread "main" java.lang.IllegalAccessException: World! 
 	at example13.Example_13Kt$main$1.doResume(example-13.kt:14) 
 	at kotlin.coroutines.experimental.jvm.internal.CoroutineImpl.resume(CoroutineImpl.kt:53) 
 	at kotlinx.coroutines.experimental.DispatchedContinuation$resume$1.run(CoroutineDispatcher.kt:57) 
```

其实，如果你自己做过对线程任务的取消，你大概会知道除非被取消的线程自己去检查取消的标志位，或者被 interrupt，否则取消是无法实现的，这有点儿像一个人执意要做一件事儿，另一个人说你别做啦，结果人家压根儿没听见，你说他能停下来吗？那么我们前面的取消到底是谁去监听了这个 cancel 操作呢？

当然是 delay 这个操作了。其实所有 kotlinx.coroutines 当中定义的操作都可以做到这一点，我们对代码稍加改动，你就会发现异常来自何处了：

```kotlin
     val job = launch(CommonPool) {  
         try { 
             delay(1000L) 
             println("World!") 
         } catch(e: Exception) { 
             e.printStackTrace() 
         }finally { 
             println("finally....") 
         } 
     } 
     println("Hello,") 
     job.cancel(IllegalAccessException("World!"))  
```
是的，你没看错，我们居然可以在协程里面对 cancel 进行捕获，如果你愿意的话，你甚至可以继续在这个协程里面运行代码，但请不要这样做，下面的示例破坏了 cancel 的设计本意，所以请勿模仿：

```kotlin
 val job = launch(CommonPool) {  
     try { 
 		... 
     }finally { 
         println("finally....") 
     } 
     println("I'm an EVIL!!! Hahahaha") 
 } 
```
说这个是什么意思呢？在协程被 cancel 掉的时候，我们应该做的其实是把战场打扫干净，比如：

```kotlin	
val job = launch(CommonPool) { 
	val inputStream = ...
    try {
		...
    }finally {
        inputStream.close()
    }
}
```
 　 
 我们再来考虑下面的情形： 
 　 
 ```kotlin 
 fun main(args: Array<String>) = runBlocking<Unit> { 
     val job = launch(CommonPool) { 
         var nextPrintTime = 0L 
         var i = 0 
         while (true) { // computation loop 
             val currentTime = System.currentTimeMillis() 
             if (currentTime >= nextPrintTime) { 
                 println("I'm sleeping ${i++} ...") 
                 nextPrintTime = currentTime + 500L 
             } 
         } 
     } 
     delay(1300L) // delay a bit 
     println("main: I'm tired of waiting!") 
     job.cancel() // cancels the job 
     delay(1300L) // delay a bit to see if it was cancelled.... 
     println("main: Now I can quit.") 
 } 
```

不得不说，kotlinx.coroutines 在几天前刚刚更新的文档和示例非常的棒。我们看到这个例子，while(true) 会让这个协程不断运行来模拟耗时计算，尽管外部调用了 job.cancel()，但由于内部并没有 care 自己是否被 cancel，所以这个 cancel 显然有点儿失败。如果你想要在类似这种耗时计算当中检测当前协程是否被取消的话，你可以这么写：

```kotlin
 ... 
 while (isActive) { // computation loop 
    ... 
 } 
 ...       
```
isActive 会在 cancel 之后被置为 false。

其实，通过这几个示例大家就会发现协程的取消，与我们通常取消线程操作的思路非常类似，只不过人家封装的比较好，而我们呢，每次还得自己搞一个 CancelableTask 来实现 Runnable 接口去承载自己的异步操作，想想也是够原始呢。

## 5. 轻量级线程

协程时轻量级的，它拥有自己的运行状态，但它对资源的消耗却非常的小。其实能做到这一点的本质原因，我们已经在上一篇文章当中提到过，一台服务器开 1k 线程和 1k 协程来响应服务，前者对资源的消耗必然很大，而后者可能只是基于很少的几个或几十个线程来工作的，随着请求数量的增加，协程的优势可能会体现的更加明显。

我们来看个比较简单的例子：

```kotlin
 fun main(args: Array<String>) = runBlocking<Unit> { 
     val jobs = List(100_000) {  
         launch(CommonPool) { 
             delay(1000L) 
             print(".") 
         } 
     } 
     jobs.forEach { it.join() } //这里不能用 jobs.forEach(Job::join)，因为 Job.join 是 suspend 方法 
 } 
```
通过 List 这个方法，我们可以瞬间创建出很多对象放入返回的 List，注意到这里的 jobs 其实就是协程的一个 List。

运行上面的代码，我们发现 CommonPool 当中的线程池的线程数量基本上维持在三四个就足够了，如果我们用线程来写上面的代码会是什么感觉？

```kotlin
 fun main(args: Array<String>) = runBlocking<Unit> { 
     val jobs = List(100_000) {  
         thread { 
             Thread.sleep(1000L) 
             log(".") 
         } 
     } 
     jobs.forEach(Thread::join) // Thread::join 说起来也是 1.1 的新特性呢！ 
 }  
```

运行时，在创建了 1k 多个线程之后，就抛出了异常：

```kotlin
 Exception in thread "main" java.lang.OutOfMemoryError: unable to create new native thread 
 	at java.lang.Thread.start0(Native Method) 
```
嗯，又多了一个用协程的理由，对不对？

## 6. 携带值的 Job

我们前面说了，通过携程返回的 Job，我们可以控制携程的运行。可有时候我们更关注协程运行的结果，比如从网络加载一张图片：

```kotlin
 suspend fun loadImage(url: String): Bitmap { 
     ... 
     return ... 
 } 
```
没错，我们更关注它的结果，这种情况我们该怎么办呢？如果 loadImage 不是 suspend 方法，那么我们在非 UI 线程当中直接获取他们：

```kotlin
 val imageA = loadImage(urlA) 
 val imageB = loadImage(urlB) 
 onImageGet(imageA, imageB) 
```

这样的操作有什么问题？顺序获取两张图片，耗时，不经济。所以传统的做法就是开两个线程做这件事儿，这意味着你会看到两个回调，并且还要同步这两个回调，想想都头疼。

不过我们现在有更好的办法：

```kotlin
 val imageA = defer(CommonPool) { loadImage(urlA) } 
 val imageB = defer(CommonPool) { loadImage(urlB) } 
 onImageGet(imageA.await(),imageB.await()) 
```
代码量几乎没有增加，不过我们却做到了两张图片异步获取，并同时传给 onImageGet 以便继续后面的操作。

defer 到底是个什么东西？其实大家大可不必看到新词就感到恐慌，这东西用法几乎跟 launch 一样，只不过它返回的 Deferred 功能比 Job 多了一样：携带返回值。我们前面看到的 imageA 其实就是一个 Deferred 实例，而它的 await 方法返回的则是 Bitmap 类型，也即 loadImage(urlA) 的返回值。

所以如果你对协程运行的结果感兴趣，直接使用 defer 来替换你的 launch 就可以了。需要注意的是，即便你不调用 await，defer 启动的协程也会立即运行，如果你希望你的协程能够按需启动，例如只有你调用 await 之后再启动，那么你可以用 lazyDefer：

```kotlin
 val imageA = lazyDefer(CommonPool) { loadImage(urlA) } 
 val imageB = lazyDefer(CommonPool) { loadImage(urlB) } 
 onImageGet(imageA.await(),imageB.await()) //这时候才开始真正去加载图片 
```

## 7. 生成器

不知道大家对 python 的生成器有没有了解，这个感觉就好似延迟计算一样。

假设我们要计算 fibonacci 数列，这个大家都知道，也非常容易写，你可能分分钟写出一个递归的函数来求得这个序列，不过你应该知道递归的层级越多，stackOverflow 的可能性越大吧？另外，如果我们只是用到其中的几个，那么递归的函数一下子都给求出来，而且每次调用也没有记忆性导致同一个值计算多次，非常不经济。大家看一个 python 的例子：

```py
 def fibonacci(): 
     yield 1 # 直接返回 1， 并且在此处暂停 
     first = 1 
     second = 1 
     while True: 
         yield first 
         first, second = first + second, first 
 　 
 　 
 a = fibonacci() 
 for x in a: 
     print x 
     if x > 100: break 
```
前面给出的这种计算方法，fibonacci 函数返回一个可迭代的对象，这个对象其实就是生成器，只有我们在迭代它的时候，它才会去真正执行计算，只要遇到 yield，那么这一次迭代到的值就是 yield 后面的值，比如，我们第一次调用 fibonacci 这个函数的时候，得到的值就是 1，后面依次类推。

Kotlin 在添加了协程这个功能之后，也可以这么搞了：

```kotlin
 val fibonacci = buildSequence { 
     yield(1) // first Fibonacci number 
     var cur = 1 
     var next = 1 
     while (true) { 
         yield(next) // next Fibonacci number 
         val tmp = cur + next 
         cur = next 
         next = tmp 
     } 
 } 
 ... 
 for (i in fibonacci){ 
     println(i) 
     if(i > 100) break //大于100就停止循环 
 } 
```
可以这么说，这段代码与前面的 python 版本功能是完全相同的，在 yield 方法调用时，传入的值就是本次迭代的值。

fibonacci 这个变量的类型如下：

```kotlin
 public interface Sequence<out T> { 
    public operator fun iterator(): Iterator<T> 
 } 
```
既然有 iterator 方法，那么我们可以直接对 fibonacci 进行迭代也就没什么大惊小怪的了。这个 iterator 保证每次迭代的时候去执行 buildSequence 后面的 Lambda 的代码，从上一个 yield 之后开始到下一个 yield 结束，yield 传入的值就是 iterator 的 next 的返回值。

有了这个特性，我们就可以构造许多“懒”序列，只有在用到的时候才去真正计算每一个元素的值，而且运算状态可以保存，每次计算的结果都不会浪费。

注：这个特性是被 Kotlin 标准库收录了的，并不存在于 kotlinx.coroutines 当中，不过这也没关系啦，kotlinx.coroutines 的 API 会不会在不久的将来也作为 Kotlin 标准库的内容出现呢？

## 8. 小结

这一篇的内容其实相对上一篇要简单一些，面对 kotlinx.coroutines 这样的框架，我们直接通过分析案例，将 coroutine 这么理论化的东西投入实际场景，让大家从感性上对其有个更加深入的认识。

当然，我们并没有深入其中了解其原理，原因就是上一篇我们为此做了足够的准备 —— kotlinx.coroutines 作为官方的框架，自然要实现得完善一些，但也是万变不离其宗。

写到这里，我想，我们还是需要有一篇文章再来介绍一些协程使用的一些注意事项，那么我们下一篇再见吧。


