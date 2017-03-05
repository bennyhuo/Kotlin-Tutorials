# 遇见未来：Kotlin 1.1发布

上周一的文章里面提到 Kotlin 1.1 rc了，还没正式发布，我在周三的时候把文章转到掘金以后，好多小伙伴告诉我，1.1 已经发布了~

tips: 本文有较多外链，公众号阅读时无法跳转，如有需要，请大家点击"阅读原文"。

## 1、更新要点

### 1.1 Coroutine

1.1 最大的更新一定必须毫无疑问的要数 Coroutine 了，尽管在正式发版之前，Kotlin Team 突然虚了，决定把这个特性定为 Experimental，不过这似乎并没有改变什么。不就是改个包名么！！

早在春节放假那几天，我就在公众号连续两周发文介绍 Coroutine，本来还计划有第三篇的，不过开工以后个项目有点儿累，每天翻 Android 系统 C++ 层的代码翻到吐，也没精力去写第三篇文章，真是抱歉，如果大家有兴趣，可以参考前两篇：

[深入理解 Kotlin Coroutine (一)](https://mp.weixin.qq.com/s?__biz=MzIzMTYzOTYzNA==&mid=2247483875&idx=1&sn=b1b565f651ee1221d4bda19ab12009ce&chksm=e8a05ededfd7d7c878c1c483c577ec53bcf42ee4cb0fe5d13f29d12ff62a1e335c4afa616ffa#rd)

[深入理解 Kotlin Coroutine (二）](https://mp.weixin.qq.com/s?__biz=MzIzMTYzOTYzNA==&mid=2247483878&idx=1&sn=710189e6e22a13fc7d1ea67bc2dd9270&chksm=e8a05edbdfd7d7cd163ee1a2d5769fc2bf003e2d5a6d3f9c6382531b7efc22a6ab75300bb906#rd)

其中，第一篇文章写于 experimental 之前，不过大家只要在包名当中加上 experimental 就没问题了。

Kotlin 的 Coroutine 实现主要分为两个层面，第一个层面就是标准库以及语言特性的支持，这里面主要包括最基本的 suspend 关键字以及诸如 startCoroutine 这样的方法扩展，上述第一篇文章对此做了详细的介绍。第二层面则主要是基于前面的基础封装的库，目前主要是 kotlinx.coroutine ，其中封装了 runBlock、launch 这样方便的操作 Coroutine 的 api，这在第二篇文章做了详细地介绍。所以大家在了解 Coroutine 的时候，可以从这两个角度来入手，以免没有头绪。

我们再来简单说说 Coroutine 的运行机制。Coroutine 是用来解决并发问题的，它甚至有个中文名叫“协程”，它看上去跟线程似乎是并发问题的两种独立的解决方案，其实不然。要并发的执行任务，从根本上说，就是要解决 Cpu 的调度问题，Cpu 究竟是如何调度，取决于操作系统，我们在应用程序编写的过程中用到的 Thread 也好，Coroutine 也好，本质上也是对操作系统并发 api 的封装。知道了这一点，我们再来想想 Thread 是如何做到两个线程并发执行的呢？Java 虚拟机的实现主要采用了对内核线程映射的方式，换句话说，我们通常用到的 Thread 的真正直接调度者可以理解为是操作系统本身。那我们在 Kotlin 当中支持 Coroutine 是不是也要把每一个 Coroutine 映射到内核呢？显然不能，不然那跟 Thread 还有啥区别呢？再者，Coroutine 的核心在 Co 上，即各个 Coroutine 是协作运行的，有一种“你唱罢来我登场”的感觉，就是说，Coroutine 的调度权是要掌握在程序自己手中的。于是，如果你去了解 kotlinx.coroutine 的实现，你就会发现 CommonPool 这么个东西，它不是别的，它的背后正是线程池。

**线程是轻量级进程，而协程则是轻量级线程。**

Coroutine 的出现让 Kotlin 如虎添翼，如果你之前在写 Go，Lua，python，或者 C#，这回 Java 虚拟机家族可不会让你失望了。自从有了协程，你也可以写出这样的代码：

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

序列生成器，记得我刚学 python 那会儿看到这样的语法，简直惊呆了。

```kotlin
  val imageA = loadImage(urlA)  
  val imageB = loadImage(urlB)  
  onImageGet(imageA, imageB)  
```
这样的代码也是没有压力的，看上去就如同步代码一般，殊不知人家做的可是异步的事情呐。

**协程的出现，让我们可以用看似同步的代码做着异步的事情。**

这篇文章我们主要说说 1.1 的发版，Coroutine 的更多内容，建议大家直接点击前面的链接去读我的另外两篇文章~

### 1.2 JavaScript 支持

真是媳妇儿终于熬成婆，Js 终于被正式支持了。看官方的意思，他们已经用这一特性做了不少尝试，从 Kotlin 从头到尾写一个站点，似乎毫无压力，尽管类似反射这一的特性还没有支持，不过面包会有的嘛。

从我个人的角度来说，也可能我对前端了解太少吧，我觉得应用在前端比起移动端、服务端来说，Kotlin 的前景相对不明朗。我用 JavaScript 用得好好的，为啥要切换 Kotlin 呢？动态特性玩起来挺爽的，虽然回调写多了容易蛋疼，但这也不是不可以规避的。关于 Kotlin 开发前端这个问题，我需要多了解一下前端开发者的看法，相比他们是否愿意接触 Kotlin，我更关心有几个做前端的人知道这门语言。不瞒各位说，前几天跟一个支付宝客户端的大哥聊了一会儿，他问我这个 k o t 什么的，是干啥的。。。我当时在想，看来阿里人对 Kotlin 还不是很熟悉啊。

Whatever，Kotlin 现在都可以支持 node.js 了，还有什么不可能的呢？作为吃瓜群众，且让我观望一阵子。

### 1.3 中文支持

你放心，这一段内容你绝对在其他人那里看不到，因为没人会这么蛋疼。我前几天为了做一个案例用中文写了段代码，想着 Java 支持中文标识符，Kotlin 应该也问题不大。没曾想，写的时候一点儿问题的没，可编译的时候却直接狗带了。

```kotlin
 package 中国.北京.回龙观 
 　 
 class G6出口{ 
     fun 下高速(){ 
         println("前方堵死, 请开启飞行模式 :)") 
     } 
 } 
 　 
 fun main(args: Array<String>) { 
     val 回龙观出口 = G6出口() 
     回龙观出口.下高速() 
 } 
```
注意，包名、代码文件名都是中文的，如果用 1.0.6 版编译，结果就是万里江山一片红哇。

```
 Error:Kotlin: [Internal Error] java.io.FileNotFoundException: /Users/benny/temp/testKotlin/out/production/testKotlin/??/??/???/G6??.class (No such file or directory) 
 	at java.io.FileInputStream.open0(Native Method) 
 	at java.io.FileInputStream.open(FileInputStream.java:195) 
 	at java.io.FileInputStream.<init>(FileInputStream.java:138) 
 	at kotlin.io.FilesKt__FileReadWriteKt.readBytes(FileReadWrite.kt:52) 
 	at org.jetbrains.kotlin.incremental.LocalFileKotlinClass 
 	... 
```
注意到，汉字都变成了 ??，瞧瞧编译器那小眼神，真是看得我都醉了。

如果我们用 1.1 的编译器来编译这段代码，结果就可以正常输出：

```
 前方堵死, 请开启飞行模式。 
```

### 1.4 其他特性

1.1 还新增了不少特性，我在之前的一篇文章就做过介绍：[喜大普奔！Kotlin 1.1 Beta 降临~](https://mp.weixin.qq.com/s?__biz=MzIzMTYzOTYzNA==&mid=2247483827&idx=1&sn=9c800c2249808685f98b4431ff74e19a&chksm=e8a05e8edfd7d798719fe8b443019cd53f541597fd039b2b9098add86d186cd2ccea2b8d61e7#rd)

* tpyealias
* 绑定调用者的函数引用
* data class 可以继承其他类
* sealed class 子类定义的位置放宽
* _ 作为占位，替代不需要的变量
* provideDelegate



## 2、Kotlin 元年

2016 年是 Kotlin “元年（First year of Kotlin）”，官网给出了这样一幅图来展示它一年来的成绩：

![](https://d3nmt5vlzunoa1.cloudfront.net/kotlin/files/2017/03/GitHub-Stats-1.gif)

Github 上面的代码量都破千万了，使用 Kotlin 的公司也逐渐增多，除了 JetBrains 自己以外，我觉得在 Java 界比较有分量的就是 Square 了，如果 Google 能够稍微提一句 Kotlin ，显然这个故事就会有另外一个令人兴奋的版本——好啦，不要 yy 啦。

据说，比较著名的主要有Amazon Web Services, Pinterest, Coursera, Netflix, Uber, Square, Trello, Basecamp 这些公司将 Kotlin 投入了生产实践当中。国内资料较少，估计接触的人也不是很多，像百度、腾讯、阿里巴巴、滴滴、新美大、小米、京东这样的公司可能还没有太多的动力去将 Kotlin 应用到开发中，就算开始尝试，也多是在 Android 开发上面试水；而敢于尝试 Kotlin 的，更多是没有什么历史包袱且富于创新和挑战精神的创业团队，对于他们而言 Kotlin 为开发带来的效率是非常诱人的。

说到这里，有两个令人兴奋的消息需要同步给大家：

* Gradle 开始尝试用 Kotlin 作为其脚本语言，目前已经发到了 0.4.0。这个真的可以有，groovy 虽然是一门很灵活的语言，不过写配置的时候如果没有 IDE 的提示，实在是太痛苦了。大家有兴趣也可以关注一下这个项目：[gradle-script-kotlin](https://github.com/gradle/gradle-script-kotlin)
* [Spring 5.0 加入 Kotlin 支持](https://spring.io/blog/2017/01/04/introducing-kotlin-support-in-spring-framework-5-0)，Spring 的地位可想而知，Spring 为 Kotlin 站台，这分量还是很重的。

不知道 2017 年会发生什么，且让我们准备好爆米花饮料，拭目以待吧。

关于 Kotlin 的资料，英文版的图书已经出版了几本，主要有：

* [Kotlin in Action](https://manning.com/books/kotlin-in-action)：这部书已经有了纸质版，是官方自己人写的，算是一本比较权威的参考书了。
* [Kotlin for Android Developers](https://leanpub.com/kotlin-for-android-developers)：这本书也算是老资历了，稍微看几眼你就会为 Kotlin 有趣的特性所吸引。另外，它还有一个[中文的翻译版本](https://wangjiegulu.gitbooks.io/kotlin-for-android-developers-zh/content/wo_men_tong_guo_kotlin_de_dao_shi_yao.html)
* [Modern Web Development with Kotlin](https://leanpub.com/modern-web-development-with-kotlin)：这本书我没有读过，如果你需要用 Kotlin 开发 web 应用，它应该会给予你一些帮助。
* [Programming Kotlin](https://www.packtpub.com/application-development/programming-kotlin)：这本书涉及内容非常全面，内容也算是言简意赅，快速入门 Kotlin 可以选择它。

除了图书以外， Kotlin 的首席布道师 Hadi Hariri 已经在 O'Reilly 上面发布了两套视频教程：

* [Introduction to Kotlin Programming](http://shop.oreilly.com/product/0636920052982.do)
* [Advanced Kotlin Programming](http://shop.oreilly.com/product/0636920052999.do)

里面有免费的几段，且不说内容怎么样，反正考验大家英语听力的时候到了，嗯，老爷子讲得还是很清楚的。

国内的资料，很少。除了有个别小伙伴写的一些博客之外，较为系统的学习资料几乎没有。也难怪大家都不知道它呢。也正是为了弥补这一空白，我在 16 年 10 月的时候开始每周 10 分钟的节奏连续录了 15 期视频，如果你有 Java 基础，那么看这些视频基本上可以让你知道 Kotlin 是怎么一回事了。

* [Kotlin 中文视频教程](https://github.com/enbandari/Kotlin-Tutorials)

另外，如果你想要对 Kotlin 持续了解，建议你关注微信公众号 Kotlin，每周一推送的 Kotlin 的相关文章，基本上会覆盖了 Kotlin 的各种最新动态。也欢迎大家跟我交流开发中遇到的问题~

## 3、Kotlin 时代

1.1 的重要的更新其实就 Coroutine 以及 JavaScript 支持，毕竟 Kotlin 对 Java 的兼容支持已经做得非常不错了（别老提 apt 的事儿，1.0.4 之后的 kapt 不就基本上很好用了么）。别人问我，Kotlin 到底是写啥的，这个问题我通常说很官方的说，Kotlin 是一门运行在 Java虚拟机、Android、浏览器上的静态语言，可是，Kotlin Team 的节奏已经让这句话显得要过时了。他们用短短几年时间搞出这么个全栈的语言，各方面特性都还很棒，然而他们并不能感到满足，他们已经开始走 C++ 的路线，也许 Kotlin Native 要不了多久就会出现了。

第一次听到这消息的时候，我瞬间就凌乱了，那感觉就好像王者荣耀里面队友选了大乔一样，秒回泉水加满血，秒回战场收人头啊。

前不久，我很荣幸地跟一位创业公司 CEO 坐下来聊理想，他问我的第一句话就是：你觉得 Kotlin 是未来么？我当时就蒙了，不得不说，他对 Kotlin 的期待跟 Kotlin Team 如出一辙呀。我当时实在不知道该怎么回答他，回来仔细想了想，答案其实也是有的。

十几年前，东家缺钱，急需投资，投资人坐下来“拷问”小马哥：“这个东西（指当时的 OICQ）怎么赚钱？” 小马哥说自己只知道这个东西大家喜欢，但不知道向谁收钱。对于 Kotlin 来说，我只知道它好用，尽管大家都还看不太懂，不过它的时代正在悄悄的到来。

<center>![](../../arts/kotlin扫码关注.png)</center>