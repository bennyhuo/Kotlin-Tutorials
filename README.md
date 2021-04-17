[![GitHub license](https://img.shields.io/badge/license-CC%20BY--NC--ND%204.0-blue.svg)](https://creativecommons.org/licenses/by-nc-nd/4.0/)

# 2021 新计划

准备做一个长期的 Kotlin 基础教程的编撰工作，作为官方文档的补充，大家有想法欢迎开 issue 跟我一起讨论。

### 内容目标(暂定)：

1. 主线内容涵盖 Kotlin common、Kotlin Jvm、Kotlin js、Kotlin Native，通用部分则不依赖 Kotlin Jvm（不像过去大家习惯的方式）
2. Kotlin 的常见库的使用方法
3. 编译器配置、Gradle DSL 等等

### 输出形式：

1. GitBook（主要目标，配合官网）
2. 纸质书（不排除可能性）

### 时间安排

1. 大纲梳理，整体初步构思 - 2021.4 之前
2. 基础部分 - 待细化
3. Jvm 部分
4. Js 部分
5. Native 部分
6. 框架部分（ktor、datetime、serialization...）

# [新书《深入理解 Kotlin 协程》](https://www.bennyhuo.com/project/kotlin-coroutines.html)

Kotlin 协程可以说是截止目前为止 Kotlin 中最让人困惑的一部分内容了，官方文档也比较简略，对于初学者不够友好。今年我把过去在公众号和博客发布的协程相关的文章进行了整理和扩充，进一步完善了诸多细节编写了本书，有兴趣可以留意一下。

## 随书源码

本书**源码地址**：[《深入理解 Kotlin 协程》源码](https://github.com/enbandari/DiveIntoKotlinCoroutines-Sources)

## 购买途径

* 京东自营：[深入理解Kotlin协程](https://item.jd.com/12898592.html)
* 当当自营：[深入理解Kotlin协程](http://product.dangdang.com/28973005.html)

# [Kotlin 从入门到精通 视频教程](http://coding.imooc.com/class/398.html)

Kotlin 1.3 是一个相对成熟和稳定的版本，这次基于  Kotlin 1.3.50 重新制作入门到精通的课程，相比之下新课知识点梳理更详细，内容干货更多，讲法也更成熟，希望能对大家有帮助。

**视频地址：** [http://coding.imooc.com/class/398.html](http://coding.imooc.com/class/398.html)


# [破解 Retrofit](https://www.imooc.com/learn/1128?mc_marking=5487b137ad904bd13590a053ede6da2f&mc_channel=syb19) 

最近不少朋友反馈希望能看到一些深入分析框架的课程，正好前段时间对 Retrofit 又做了一次深入的分析，整理了一套免费视频分享给大家~ 

# [破解Android高级面试](https://s.imooc.com/SBS30PR)

我一直做 Kotlin 的推广和宣传，发现大多数学习 Kotlin 的同学都是被行业“胁迫”不得不学。大家的诉求更多是怎么样找到一份好的工作，考虑到学习 Kotlin 的同学大多都是 Android 开发者，我就花了半年的功夫仔细整理了这一套视频出来，题目看上去不多，但每一个题目背后能引出的知识点可一点儿都不少。不求面面俱到，只求精准打击，面试过程中只要你能给面试官留下深刻的印象，那么这事儿就成了。

# [注解处理器开发教程](https://github.com/enbandari/Apt-Tutorials)

我在做 [基于 GitHub App 业务深度讲解 Kotlin1.2高级特性与框架设计](https://coding.imooc.com/class/232.html) 这门课的时候，顺便做了一个注解处理器的框架，叫 [Tieguanyin(铁观音)](https://github.com/enbandari/TieGuanYin)，这个框架主要是用来解决 Activity 跳转时传参的问题，我们知道 Activity 如果需要参数，那么我们只能非常繁琐的使用 `Intent` 来传递，有了这个框架我们就可以省去这个麻烦的步骤。

在这里，框架的内容其实不是重点，重点是，它是一个注解处理器的项目。为了让它的作用尽可能的放大，我对原框架做了简化，做了这套课程。

# [基于 GitHub App 业务深度讲解 Kotlin1.2高级特性与框架设计](https://coding.imooc.com/class/232.html)

我们在 2018年6月基于 Android 的开发环境推出了一套深入讲解 Kotlin 特性的课程，这套课程以大家最为熟悉的 GitHub App 开发作为整套课程的线索，将 Kotlin 的高级特性一一融入其中予以讲解，这其中我们对泛型、反射、属性代理、协程、Dsl 做了详尽的讲解和框架设计与开发，力求让大家能够对这些技能灵活运用。


# [Kotlin从入门到『放弃』系列 视频教程](https://github.com/enbandari/Kotlin-Tutorials) 

**发现有人在淘宝店上卖这套视频哈，大家不要去买。。。直接在这里下载多好**

随着Kotlin越来越成熟稳定，我已经开始在生产环境中使用它。考虑到目前国内资料较少，我录制了一套视频教程，希望以此抛砖引玉，让 Kotlin 在国内火起来。

个人水平有限，不足之处欢迎大家发邮件到 [bennyhuo@println.net](mailto: bennyhuo@println.net) ，谢谢大家！

# 介绍

视频从2016年10月初开始发布，更新至12月中旬完结。精力有限，后续暂时每周一通过公众号推送 Kotlin 及 Java 生态的一些文章，大家有选题也可以直接联系我，目前已经有两篇是基于大家的反馈撰写的，反馈也比较不错，谢谢大家的关注与支持。

[在线收看全部视频（腾讯视频）](http://v.qq.com/boke/gplay/903446d6231d8612d198c58fb86eb4dc_t6d000101bd9lx1.html)
	
[下载全部视频（百度云）](http://pan.baidu.com/s/1nvGYAfB)

# 目录

#### [01 Kotlin 简介](http://v.qq.com/page/z/u/9/z0337i7a3u9.html)

　　简要介绍下什么是 Kotlin，新语言太多了，大家为什么要接触 Kotlin 呢？因为它除了长得与 Java 不太像以外，其他的都差不多~
　　
  
#### [02 Hello World](http://v.qq.com/page/h/n/m/h0337jfa5nm.html)

　　千里之行，始于Hello World！
　　
  
#### [03 使用Gradle编写程序简介（可选）](http://v.qq.com/page/b/p/l/b03372ox4pl.html)

　　这年头，写 Java 系的代码，不知道 Gradle 怎么行呢？
　　
  
#### [04 集合遍历 map](http://v.qq.com/page/s/q/c/s033707mdqc.html)

　　放下 i++，你不知道 map 已经占领世界了么？以前我以为 map-reduce 很牛逼，后来才知道就是数据迭代处理嘛。
　　
  
#### [05 集合扁平化 flatMap](http://v.qq.com/page/h/u/7/h0337scgau7.html)

　　这个可以说是 map 的一个加强版，返回的仍然是开一个可迭代的集合，用哪个您自己看需求~
　　
  
#### [06 枚举类型与When表达式](http://v.qq.com/page/t/0/9/t0337iacg09.html)

　　Kotlin 丢掉了 switch，却引进了 when，这二者看上去极其相似，不过后者却要强大得多。至于枚举嘛，还是 Java 枚举的老样子。
  
  
#### [07 在 RxJava 中使用 Lambda](http://v.qq.com/x/page/l0340boeng7.html)

　　这一期通过一个统计文章中字符数的小程序进一步给大家呈现 Lambda 的威力，也向大家展示一下如何在 Kotlin 当中优雅地使用 RxJava。我不做教科书，所以如果大家对概念感兴趣，可以直接阅读官方 [API](https://kotlinlang.org/docs/reference/lambdas.html)

　　[RxJava](https://github.com/ReactiveX/RxJava) 是一个非常流行的 Java Reactive 框架，函数式的数据操作使得 Lambda 表达式可以充分体现自己的优势，比起 Java 的冗长，你会看到一段非常漂亮简洁的代码。建议大家先阅读 [RxJava](https://github.com/ReactiveX/RxJava) 的官方文章以对其有一些基本的认识。
　　

#### [08 使用 Retrofit 发送 GET 请求](http://v.qq.com/x/page/t0342thu1al.html)

　　[Retrofit](https://square.github.io/retrofit/) 是 Square 的 Jake 大神开源的RESTful 网络请求框架，用它发送请求的感觉会让你感觉爽爆的。我这里还有几篇文章，以及一个我 hack 过的分支 [HackRetrofit](https://github.com/enbandari/HackRetrofit)，有兴趣的童鞋可以一起探讨下~


* [Android 下午茶：Hack Retrofit 之 增强参数](http://www.println.net/post/Android-Hack-Retrofit).

* [Android 下午茶：Hack Retrofit (2) 之 Mock Server](http://www.println.net/post/Android-Hack-Retrofit-Mock-Server)

* [深入浅出 Retrofit，这么牛逼的框架你们还不来看看？](http://www.println.net/post/deep-in-retrofit)


#### [09 尾递归优化](http://v.qq.com/x/page/f0345wmuw2m.html)

　　尾递归，顾名思义，就是递归中调用自身的部分在函数体的最后一句。我们知道，递归调用对于栈大小的考验是非常大的，也经常会因为这个导致 StackOverflow，所以尾递归优化也是大家比较关注的一个话题。Kotlin 支持语法层面的尾递归优化，这在其他语言里面是不多见的。


#### [10 单例](https://v.qq.com/x/page/w03659ate6w.html)

　　单例大家一定都不陌生，只要你动手写一个程序，就免不了要设计出一些全局存在且唯一的对象，他们就适合采用单例模式编写。在 Java 里面，单例模式的写法常见的有好几种，虽然简单却也是涉及了一些有意思的话题，那么在 Kotlin 当中我们要怎么设计单例程序呢？


#### [11 Sealed Class](https://v.qq.com/x/page/f0350ioskzj.html)
　　枚举类型可以很好的限制一个类型的实例个数，比如 State 枚举有两种类型 IDLE 和 BUSY 两种状态，用枚举来描述再合适不过。不过，如果你想要设计子类个数有限的数据结构，比如指令，指令的类型通常是确定的，不过对于某些有参数的指令每一次都使用同一个实例反而不合适，这时候就需要 Sealed Class。
　　
　　
#### [12 Json数据引发的血案](https://v.qq.com/x/page/s035296s9nw.html) 
　　Json 数据可真是大红大紫一番，它实在是太容易理解了，随着 Js 的火爆它就更加『肆无忌惮』起来。我们在 Java/Kotlin 当中解析它的时候经常会用到 Gson 这个库，用它来解析数据究竟会遇到哪些问题？本期主要围绕 Json 解析的几个小例子，给大家展示一下 Java/Kotlin 的伪泛型设计的问题，以及不完整的数据的解析对语言本身特性的冲击。


#### [13 kapt 以及它的小伙伴们](https://v.qq.com/x/page/q035439xksx.html) 

　　首先感谢 @CodingPapi，这一期的内容主要来自于他的建议。

　　Kotiln 对于注解的支持情况在今年（2016）取得了较大的成果，现在除了对 @Inherited 这个注解的支持还不够之外，试用了一下没有发现太大的问题。关于 kapt，官方的文章罗列下来，其中

* [kapt: Annotation Processing for Kotlin
](https://blog.jetbrains.com/kotlin/2015/05/kapt-annotation-processing-for-kotlin/) 已经过时了，大家可以阅读下了解其中提到的三个方案

* [Better Annotation Processing: Supporting Stubs in kapt
](https://blog.jetbrains.com/kotlin/2015/06/better-annotation-processing-supporting-stubs-in-kapt/) 提到的实现其实基本上就是现在的正式版

* [Kotlin 1.0.4 is here
](https://blog.jetbrains.com/kotlin/2016/09/kotlin-1-0-4-is-here/)提到了 kapt 的正式发布，需要注意的是，kapt 的使用方法有些变化，需要 ```apply plugin: 'kotlin-kapt'```

　　本期主要通过一个简单的 [Dagger2](https://google.github.io/dagger/) 实例给大家展示了注解在 Kotlin 当中的使用，看上去其实与在 Java 中使用区别不大，生成的源码也暂时是 Java 代码，不过这都不重要了，反正是要编译成 class 文件的。

　　后面我们又简单分析了一下 Dagger2 以及 [ButterKnife](https://github.com/JakeWharton/butterknife) 的源码（有兴趣的话也可以看下我直接对后者进行分析和 Hack 的一篇文章：[深入浅出 ButterKnife，听说你还在 findViewById？](http://www.println.net/post/Deep-in-ButterKnife-3)），其实自己实现一个注解处理器是非常容易的，类似的框架还有[androidannotations](https://github.com/androidannotations/androidannotations)，它的源码大家可以自行阅读。

****
通过这个例子，我们其实发现 kapt 还是有一些不完善的地方，主要是：

1. 不支持 @Inherited 
2. 生成的源码需要手动添加到 SourceSets 中 
3. 编译时有时候需要手动操作一下 gradle 的 build 才能生成源码（这一点大家注意下就行了，我在视频中并没有提到）

不过总体来讲，kapt 的现状还是不错的，相信不久的将来这些问题都将不是问题。
****

#### [14 Kotlin 与 Java 共存 (1)](https://v.qq.com/x/page/c03579nzo8x.html)

　　你想要追求代码简洁、美观、精致，你应该倾向于使用 Kotlin，而如果你想要追求代码的功能强大，甚至有些黑科技的感觉，那 Java 还是当仁不让的。

　　说了这么多，还是那句话，让他们共存，各取所长。

　　那么问题来了，怎么共存呢？虽然一说理论我们都知道，跑在 Jvm 上面的语言最终都是要编成 class 文件的，在这个层面大家都是 Java 虚拟机的字节码，可他们在编译之前毕竟还是有不少差异的，这可如何是好？

　　正所谓兵来将挡水来土掩，有多少差异，就要有多少对策，这一期我们先讲**在 Java 中调用 Kotlin**。


#### [15 Kotlin 与 Java 共存 (2)](https://v.qq.com/x/page/z0357ls85fe.html)

　　上一期我们简单讨论了几个 Java 调用 Kotlin 的场景，这一期我们主要讨论相反的情况：如何在 Kotlin 当中调用 Java 代码。

　　除了视频中提到的点之外还有一些细节，比如异常的捕获，集合类型的映射等等，大家自行参考官方文档即可。在了解了这些之后，你就可以放心大胆的在你的项目中慢慢渗透 Kotlin，让你的代码逐渐走向简洁与精致了。

# 获取最新动态

关注微信公众号：Kotlin， 获取最新视频更新动态

<img src="arts/Kotlin.jpg" width="250px"/>

大群已满~ 

加入新 QQ 群
Kotlin 中文社区群 大群：162452394 （已满）
Kotlin 中文社区群 ① 群：603441485 （已满）
Kotlin 中文社区群 ② 群：751395597

