# [Kotlin从入门到『放弃』](https://github.com/enbandari/Kotlin-Tutorials) [持续更新中]
随着Kotlin越来越成熟稳定，我已经开始在生产环境中使用它。考虑到目前国内资料较少，近期我开始筹划录制一套视频教程，并以此抛砖引玉，让 Kotlin 在国内火起来。

个人水平有限，不足之处欢迎大家发邮件到 [bennyhuo@println.net](mailto: bennyhuo@println.net)，谢谢大家！

# 发布计划

关注微信公众号：Kotlin， 获取最新视频更新动态

<img src="arts/Kotlin.jpg" width="250px"/>

从10月初开始，每期视频控制在10-20分钟，更新间隔视情况而定，尽量在每周能至少更新一期。

* 10月14日：思考了一下，我决定将视频的内容调整为几个案例，而不是传统的教条式的讲述，因为。。我录得时候发现我自己都困了哈哈

	[在线收看全部视频（腾讯视频）](http://v.qq.com/boke/gplay/903446d6231d8612d198c58fb86eb4dc_t6d000101bd9lx1.html)
	
	[下载全部视频（百度云）](http://pan.baidu.com/s/1nvGYAfB)

* 11月6日：大家有什么关心的技术点也可以在issue里面发出来一起讨论哈，我也会根据大家的需求来安排后面的内容。
* 11月28日：有朋友问我，为什么要『放弃』。。。额，这是一个用来自嘲的梗嘛，大家不信去搜搜 『Java 从入门到放弃』 ：）
* 12月2日：**有朋友说内容太少啦，不够看！我首先要表示一下感谢，谢谢诸位的关注和支持~这套视频是我在工作之余抽时间录制的，一个视频虽然只有10分钟，不过内容点却不太好想，通常一个视频我会尝试准备几个点，最终选择一个合适的录制，所以更新速度不会太快，不过，我会尽量保证在每周一更新一期，说实话我也挺着急，哈哈~还有一个事情就是，编程语言这个东西本身并不难，难的是它身后的一大家子（生态），Kotlin 的身后就是积累多年的 Java 生态，如果大家对视频有疑问，尽管发邮件或者直接开 issue 跟我讨论，我也可以考虑录一些『番外篇』来解答这些问题，谢谢大家的支持！**

# 目录

##第一部分 语言基础

####[01 Kotlin 简介](http://v.qq.com/page/z/u/9/z0337i7a3u9.html)

　　简要介绍下什么是 Kotlin，新语言太多了，大家为什么要接触 Kotlin 呢？因为它除了长得与 Java 不太像以外，其他的都差不多~
  
####[02 Hello World](http://v.qq.com/page/h/n/m/h0337jfa5nm.html)

　　千里之行，始于Hello World！
  
####[03 使用Gradle编写程序简介（可选）](http://v.qq.com/page/b/p/l/b03372ox4pl.html)

　　这年头，写 Java 系的代码，不知道 Gradle 怎么行呢？
  
####[04 集合遍历 map](http://v.qq.com/page/s/q/c/s033707mdqc.html)

　　放下 i++，你不知道 map 已经占领世界了么？以前我以为 map-reduce 很牛逼，后来才知道就是数据迭代处理嘛。
  
####[05 集合扁平化 flatMap](http://v.qq.com/page/h/u/7/h0337scgau7.html)

　　这个可以说是 map 的一个加强版，返回的仍然是开一个可迭代的集合，用哪个您自己看需求~
  
####[06 枚举类型与When表达式](http://v.qq.com/page/t/0/9/t0337iacg09.html)

　　Kotlin 丢掉了 switch，却引进了 when，这二者看上去极其相似，不过后者却要强大得多。至于枚举嘛，还是 Java 枚举的老样子。
  
####[07 在 RxJava 中使用 Lambda](http://v.qq.com/x/page/l0340boeng7.html)

　　这一期通过一个统计文章中字符数的小程序进一步给大家呈现 Lambda 的威力，也向大家展示一下如何在 Kotlin 当中优雅地使用 RxJava。我不做教科书，所以如果大家对概念感兴趣，可以直接阅读官方 [API](https://kotlinlang.org/docs/reference/lambdas.html)

　　[RxJava](https://github.com/ReactiveX/RxJava) 是一个非常流行的 Java Reactive 框架，函数式的数据操作使得 Lambda 表达式可以充分体现自己的优势，比起 Java 的冗长，你会看到一段非常漂亮简洁的代码。建议大家先阅读 [RxJava](https://github.com/ReactiveX/RxJava) 的官方文章以对其有一些基本的认识。

####[08 使用 Retrofit 发送 GET 请求](http://v.qq.com/x/page/t0342thu1al.html)
　　[Retrofit](https://square.github.io/retrofit/) 是 Square 的 Jake 大神开源的RESTful 网络请求框架，用它发送请求的感觉会让你感觉爽爆的。我这里还有几篇文章，以及一个我 hack 过的分支 [HackRetrofit](https://github.com/enbandari/HackRetrofit)，有兴趣的童鞋可以一起探讨下~


* [Android 下午茶：Hack Retrofit 之 增强参数](http://www.println.net/post/Android-Hack-Retrofit).

* [Android 下午茶：Hack Retrofit (2) 之 Mock Server](http://www.println.net/post/Android-Hack-Retrofit-Mock-Server)

* [深入浅出 Retrofit，这么牛逼的框架你们还不来看看？](http://www.println.net/post/deep-in-retrofit)

####[09 尾递归优化](http://v.qq.com/x/page/f0345wmuw2m.html)
　　尾递归，顾名思义，就是递归中调用自身的部分在函数体的最后一句。我们知道，递归调用对于栈大小的考验是非常大的，也经常会因为这个导致 StackOverflow，所以尾递归优化也是大家比较关注的一个话题。Kotlin 支持语法层面的尾递归优化，这在其他语言里面是不多见的。

####[10 单例](https://v.qq.com/x/page/f034839rf5q.html)

　　单例大家一定都不陌生，只要你动手写一个程序，就免不了要设计出一些全局存在且唯一的对象，他们就适合采用单例模式编写。在 Java 里面，单例模式的写法常见的有好几种，虽然简单却也是涉及了一些有意思的话题，那么在 Kotlin 当中我们要怎么设计单例程序呢？

####[11 Sealed Class](https://v.qq.com/x/page/f0350ioskzj.html)
　　枚举类型可以很好的限制一个类型的实例个数，比如 State 枚举有两种类型 IDLE 和 BUSY 两种状态，用枚举来描述再合适不过。不过，如果你想要设计子类个数有限的数据结构，比如指令，指令的类型通常是确定的，不过对于某些有参数的指令每一次都使用同一个实例反而不合适，这时候就需要 Sealed Class。
　　
####[12 Json数据引发的血案](https://v.qq.com/x/page/s035296s9nw.html) 
　　Json 数据可真是大红大紫一番，它实在是太容易理解了，随着 Js 的火爆它就更加『肆无忌惮』起来。我们在 Java/Kotlin 当中解析它的时候经常会用到 Gson 这个库，用它来解析数据究竟会遇到哪些问题？本期主要围绕 Json 解析的几个小例子，给大家展示一下 Java/Kotlin 的伪泛型设计的问题，以及不完整的数据的解析对语言本身特性的冲击。

####[13 kapt 以及它的小伙伴们]() **12.12 发布**
　　首先感谢 @CodingPapi 建议我做一期关于注解的视频。

　　Kotiln 对于注解的支持情况在今年（2016）取得了较大的成果，现在除了对 @Inherited 这个注解的支持还不够之外，试用了一下没有发现太大的问题。关于 kapt，官方的文章罗列下来，其中

* [kapt: Annotation Processing for Kotlin
](https://blog.jetbrains.com/kotlin/2015/05/kapt-annotation-processing-for-kotlin/) 已经过时了，大家可以阅读下了解其中提到的三个方案

* [Better Annotation Processing: Supporting Stubs in kapt
](https://blog.jetbrains.com/kotlin/2015/06/better-annotation-processing-supporting-stubs-in-kapt/) 提到的实现其实基本上就是现在的正式版

* [Kotlin 1.0.4 is here
](https://blog.jetbrains.com/kotlin/2016/09/kotlin-1-0-4-is-here/)提到了 kapt 的正式发布，需要注意的是，kapt 的使用方法有些变化，需要 ```apply plugin: 'kotlin-kapt'```

　　本期主要通过几个简单的 [Dagger2](https://google.github.io/dagger/) 实例给大家展示了注解在 Kotlin 当中的使用，看上去其实与在 Java 中使用区别不大，生成的源码也暂时是 Java 代码，不过这都不重要了，反正是要编译成 class 文件的。

　　后面我们又简单分析了一下 Dagger2 以及 [ButterKnife](https://github.com/JakeWharton/butterknife) 的源码（有兴趣的话也可以看下我直接对后者进行分析和 Hack 的一篇文章：[深入浅出 ButterKnife，听说你还在 findViewById？](http://www.println.net/post/Deep-in-ButterKnife-3)），其实自己实现一个注解处理器是非常容易的，类似的框架还有[androidannotations](https://github.com/androidannotations/androidannotations)，它的源码大家可以自行阅读。

****
通过这个例子，我们其实发现 kapt 还是有一些不完善的地方，主要是：

1. 不支持 @Inherited 
2. 生成的源码需要手动添加到 SourceSets 中 
3. 编译时有时候需要手动操作一下 gralde 的 build 才能生成源码（这一点大家注意下就行了，我在视频中并没有提到）

不过总体来讲，kapt 的现状还是不错的，相信不久的将来这些问题都将不是问题。
****


## 第二部分 使用 Kotlin 进行 Android 开发


# 打赏

录制这样一套视频确实是需要花费时间和心血的，如果您觉得它对您有帮助，可以通过微信和支付宝打赏，我将努力将视频做到最好！谢谢！

<img src="arts/contributes.jpg" width="450px"/>

