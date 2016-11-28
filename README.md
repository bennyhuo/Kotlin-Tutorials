# [Kotlin从入门到『放弃』](https://github.com/enbandari/Kotlin-Tutorials)
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
* 11月28日：**有朋友问我，为什么要『放弃』。。。额，这是一个用来自嘲的梗嘛，大家不信去搜搜 『Java 从入门到放弃』 ：）**

# 目录

##第一部分 语言基础

####[01 Kotlin 简介](http://v.qq.com/page/z/u/9/z0337i7a3u9.html)

####[02 Hello World](http://v.qq.com/page/h/n/m/h0337jfa5nm.html)

####[03 使用Gradle编写程序简介（可选）](http://v.qq.com/page/b/p/l/b03372ox4pl.html)

####[04 集合遍历 map](http://v.qq.com/page/s/q/c/s033707mdqc.html)

####[05 集合扁平化 flatMap](http://v.qq.com/page/h/u/7/h0337scgau7.html)

####[06 枚举类型与When表达式](http://v.qq.com/page/t/0/9/t0337iacg09.html)

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


## 第二部分 使用 Kotlin 进行 Android 开发


# 打赏

录制这样一套视频确实是需要花费时间和心血的，如果您觉得它对您有帮助，可以通过微信和支付宝打赏，我将努力将视频做到最好！谢谢！

<img src="arts/contributes.jpg" width="450px"/>

