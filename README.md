[![GitHub license](https://img.shields.io/badge/license-CC%20BY--NC--ND%204.0-blue.svg)](https://creativecommons.org/licenses/by-nc-nd/4.0/)

# Kotlin-Tutorials

**2021.6 更新**

这个仓库最初（2016年底）是用来分享一些 Kotlin 教程的，感兴趣的话可以查看[这里](legacy/README.md)。后来随着 Google 的大力支持，Kotlin 已经逐步在 Android 开发领域占据了一席之地。我就以个人经历来说说自己的感受吧：

过去几年我所在的[腾讯公司](https://www.tencent.com)当中，Kotlin 的落地项目实际上已经相当可观，例如：
* 外部开源的 Android 插件化框架 [Shadow](https://github.com/Tencent/Shadow)，核心逻辑基本上使用 Kotlin 编写。
* [蓝鲸 CI 平台](https://github.com/Tencent/bk-ci)，这是一个后端项目，在腾讯内部已经成为公司主推的 CI 平台，之前我在上面做持续集成时需要做插件开发，惊喜地发现打印出来的调用堆栈居然有 Kotlin 身影。
* 内部还有团队使用 KMM 做跨平台的 UI 控件渲染，这可以说是非常有挑战的工作了，不仅要在移动端支持 Android、iOS，还要同时支持桌面版。基于这个框架所属产品的体量，我们大概率已经是这套技术方案的用户了。

今年初我因个人原因离开了鹅厂，去了[北京猿力教育科技有限公司（猿辅导）](https://www.yuanfudao.com/)，不出所料，在这家号称**小而美**且**有技术追求**的公司当中，Kotlin 在项目上的应用已经相当广泛了，新代码很少能见到 Java 的身影（有需要内推的小伙伴可以找我）。
 
所以，作为“布道者”，过去我们总是想着怎么让更多的开发者知道 Kotlin，现在我们则需要提供更多的 Kotlin 的学习材料，来帮助大家更好的使用 Kotlin。

我目前主要的想法就是做一些视频放到 B 站上（账号：[**bennyhuo 不是算命的**](https://space.bilibili.com/28615855)），当然视频的内容也不限于 Kotlin 本身。这个计划已经开始尝试实施了，内容的规划在这个仓库的 [issue](https://github.com/bennyhuo/Kotlin-Tutorials/issues) 当中跟进，例如前不久刚刚录制的几个 [Gradle 迁移 KTS 的视频](https://github.com/bennyhuo/Kotlin-Tutorials/issues/25)。

还有一个相对长期的规划是做一套电子书，后面会在这个 [issue](https://github.com/bennyhuo/Kotlin-Tutorials/issues/36) 当中跟进，电子书的主体内容与我之前的课程 [Kotlin 从入门到精通 视频教程](http://coding.imooc.com/class/398.html) 会有一定程度上的重叠，但电子书的好处就是更新维护方便，相比之下视频教程制作成本太高且不易维护。

有想法的小伙伴欢迎与我交流，也欢迎加入**催更群：966752510**一起讨论。

## 视频清单

### [Kotlin 杂谈 (持续更新中)](https://github.com/bennyhuo/Kotlin-Tutorials/issues/35)

Kotlin 的一些很零碎的内容，放到这个栏目下面。

- [x] [[Kotlin 杂谈] SAM 转换遇到包内可见](https://www.bilibili.com/video/BV1wB4y1g79W/)  >>[示例代码](code\Kotlin-Sample\src\main\java\com\bennyhuo\kotlin\samissue)
- [x] [[Kotlin 杂谈] 使用协程实现轮询任务竟是这么简单](https://www.bilibili.com/video/BV11b4y1Z7sz/) >>[示例代码](code\Kotlin-Sample\src\main\java\com\bennyhuo\kotlin\scheduledtask)
- [x] [[Kotlin 协程 1.5] 什么？GlobalScope 竟被废弃了？](https://www.bilibili.com/video/BV1P64y1C7bF/) >>[示例代码](https://github.com/bennyhuo/Kotlin-Tutorials/blob/master/code/Kotlin-Sample/src/main/java/com/bennyhuo/kotlin/coroutinesupdate/DelicateGlobalScope.kt)  2021-06-09 18:00:00 发布


### [Gradle 迁移 KTS 系列视频 (2021.4 已完结)](https://github.com/bennyhuo/Kotlin-Tutorials/issues/25)

前不久开源了一个小项目 [Android-LuaJavax](https://github.com/bennyhuo/Android-LuaJavax)，在改造 Gradle 脚本的时候也同时看到群里有不少小伙伴在问 KTS 编写 Gradle 脚本的问题，于是想要做几个视频来介绍下 Gradle 脚本从 Groovy 到 Kotlin 需要掌握哪些内容。

- [x] [你的 Gradle脚本究竟是什么？](https://www.bilibili.com/video/BV18K4y1D7Yb/)
- [x] [你的 Gradle 脚本是怎么运行起来的？](https://www.bilibili.com/video/BV1ep4y1h7qU/)
- [x] [Gradle Project 的属性都是哪里来的？](https://www.bilibili.com/video/BV16h411D77Q/)
- [x] [Gradle Task 创建的特殊语法](https://www.bilibili.com/video/BV1ib4y1D74X/)
- [x] [快速迁移 Gradle 脚本至 KTS](https://www.bilibili.com/video/BV1Kf4y1p7zq/)
- [x] [番外：如何调试 Gradle 源码](https://www.bilibili.com/video/BV1m54y1L7vK)
- [x] [如何为 Gradle 的 KTS 脚本添加扩展？](https://www.bilibili.com/video/BV1BU4y1b7Wk/)

## 联系我

* 邮箱： [bennyhuo@kotliner.cn](mailto:bennyhuo@kotliner.cn) 
* B 站账号：[**bennyhuo 不是算命的**](https://space.bilibili.com/28615855)，我的个人视频会优先在 B 站发布
* 微信公众号 **Kotlin**，公众号主要用来发布 Kotlin 以及移动端开发相关的内容。

    <img src="legacy/arts/Kotlin.jpg" width="250px"/>
* 加入社区 QQ 群
    * Kotlin 中文社区群 大群：162452394 （已满）
    * Kotlin 中文社区群 ① 群：603441485 （已满）
    * Kotlin 中文社区群 ② 群：751395597

* 当然，**北京猿辅导**的小伙伴也可以找我面聊 ：）

---

以下是过去几年我的一些积累和产出，有兴趣可以关注。

## [《深入理解 Kotlin 协程》](https://www.bennyhuo.com/project/kotlin-coroutines.html) 2020.6 出版

Kotlin 协程可以说是截止目前为止 Kotlin 中最让人困惑的一部分内容了，官方文档也比较简略，对于初学者不够友好。今年我把过去在公众号和博客发布的协程相关的文章进行了整理和扩充，进一步完善了诸多细节编写了本书，有兴趣可以留意一下。

### 随书源码

本书**源码地址**：[《深入理解 Kotlin 协程》源码](https://github.com/enbandari/DiveIntoKotlinCoroutines-Sources)

### 购买途径

* 京东自营：[深入理解Kotlin协程](https://item.jd.com/12898592.html)
* 当当自营：[深入理解Kotlin协程](http://product.dangdang.com/28973005.html)

## [注解处理器开发教程](https://github.com/enbandari/Apt-Tutorials)

我在做 [基于 GitHub App 业务深度讲解 Kotlin1.2高级特性与框架设计](https://coding.imooc.com/class/232.html)(目前已经下线) 这门课的时候，顺便做了一个注解处理器的框架，叫 [Tieguanyin(铁观音)](https://github.com/enbandari/TieGuanYin)，这个框架主要是用来解决 Activity 跳转时传参的问题，我们知道 Activity 如果需要参数，那么我们只能非常繁琐的使用 `Intent` 来传递，有了这个框架我们就可以省去这个麻烦的步骤。

在这里，框架的内容其实不是重点，重点是，它是一个注解处理器的项目。为了让它的作用尽可能的放大，我对原框架做了简化，做了这套课程。

## [Kotlin 从入门到精通 视频教程](http://coding.imooc.com/class/398.html)

Kotlin 1.3 是一个相对成熟和稳定的版本，这次基于  Kotlin 1.3.50 重新制作入门到精通的课程，相比之下新课知识点梳理更详细，内容干货更多，讲法也更成熟，希望能对大家有帮助。

**视频地址：** [http://coding.imooc.com/class/398.html](http://coding.imooc.com/class/398.html)


## [破解 Retrofit](https://www.imooc.com/learn/1128?mc_marking=5487b137ad904bd13590a053ede6da2f&mc_channel=syb19) 

最近不少朋友反馈希望能看到一些深入分析框架的课程，正好前段时间对 Retrofit 又做了一次深入的分析，整理了一套免费视频分享给大家~ 

## [破解Android高级面试](https://s.imooc.com/SBS30PR)

我一直做 Kotlin 的推广和宣传，发现大多数学习 Kotlin 的同学都是被行业“胁迫”不得不学。大家的诉求更多是怎么样找到一份好的工作，考虑到学习 Kotlin 的同学大多都是 Android 开发者，我就花了半年的功夫仔细整理了这一套视频出来，题目看上去不多，但每一个题目背后能引出的知识点可一点儿都不少。不求面面俱到，只求精准打击，面试过程中只要你能给面试官留下深刻的印象，那么这事儿就成了。

