# Kotlin 兼容 Java 遇到的最大的“坑” 

前言：上周我发了一篇文章[Kotlin 遇到 MyBatis：到底是 Int 的错，还是 data class 的错？](https://mp.weixin.qq.com/s?__biz=MzIzMTYzOTYzNA==&mid=2247483908&idx=1&sn=0c072a630198d4a23a7d3aec700c138b&chksm=e8a05d39dfd7d42f1494c5f0fcc0562112be6d8912e44fc51f17dee472f9ebdfaad7d930e3b1#rd)讲如何解决群里面一兄弟遇到的 data class 与 MyBatis 相克的问题，其中提到了几种外门邪道的方法，也提到了官方的解决思路，有些朋友看了之后还是不太明白，甚至紧接着就有小伙伴在使用 Realm 的时候遇到了类似的问题，看来，我还是得再写一篇来进一步告诉大家，这究竟是个什么问题，以及该如何面对它。

本文源码在 [Github：Kotlin-Tutorials](https://github.com/enbandari/Kotlin-Tutorials) 这个项目当中，微信公众号无法添加外链，请大家点击“阅读原文”获取。

## 一个 Realm 的小例子

Realm 在 2016 年与 RxJava、Retrofit 这样的框架一起，在 Android 开发领域内着实小小的火了一把，如果大家对它不了解，没关系，传送门 biu ~ [Realm](https://realm.io/)

我们先按照官网的说明配置好 gradle 依赖，话说呀，这互联网发展这么快，新时代的框架一出来，逼格果断就体现在完善的构建和开发生态，你发布的东西还只是一个 jar 包，人家呢，早上了 maven 不说，还要搞几个 gradle 任务来方便你开发：

```groovy
buildscript {
    ext.kotlin_version = '1.1.1'
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:2.3.0'
        //这里将 realm 的 gradle 插件加入 gradle 构建的运行时
        classpath "io.realm:realm-gradle-plugin:3.0.0"
    }
}
...
```

```groovy
apply plugin: 'com.android.application'
apply plugin: 'realm-android' //应用插件，Realm 会在这里添加自己的一些构建任务
...
```
其实 gradle 插件开发也是一个很有意思的话题，如果大家有需要，我后面也可以写几篇文章介绍下（悄悄告诉你们，其实我早就想写了，这不是 kotlin 版的 gradle 还没有正式发布么！）。

有了这个我们就可以开始写个 Realm 的 demo 了。

小明：等等！我还有一事不明，你怎么不添加 realm 的依赖就要开始写 demo 了啊！

艾玛，要么说小明人家就是明白人呢，我前面写了一大堆，只不过是添加了 gradle 构建的依赖而已，而我们的程序想要使用 realm，必须依赖 realm 的运行时库才行。那这么说我是不是漏掉了什么？当然没有，怎么会呢，我这么聪（dou）明（bi）的人，我可是一步一步照着官网的步骤抄的！

其实呀，realm 的运行时依赖早在我们 apply plugin 的时候就已经添加进来的， realm-android 这个插件除了添加了一些它需要的 gradle 任务之外，也顺手帮我们把依赖添加了。嗯，就是酱紫，如果有那个同学学（xian）有（de）余（dan）力（teng），可以翻一翻 realm 插件的源码。

来来来，赶紧看 demo，不然有些人该内急了~

首先在 Application 当中初始化它：

```kotlin
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        Realm.setDefaultConfiguration(
                RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(1)
                .build())
    }
}
```

定义一个 User 类：

```kotlin
data class User(@PrimaryKey var id: Int, val name: String) : RealmObject()
```

接着我们开始存数据和查数据啦：

```kotlin
add.setOnClickListener {
    Realm.getDefaultInstance().use {
        it.beginTransaction()
        val d = it.createObject(User::class.java, it.where(User::class.java).count())
        d.name = "User ${d.id}"
        it.commitTransaction()
    }
    
}

query.setOnClickListener {
    Realm.getDefaultInstance().use {
        it.where(User::class.java).findAll().map {
            Log.d(TAG, it.toString())
        }
    }
}
```

想得挺美，结果呢？编译不通过。

```
Error:A default public constructor with no argument must be declared in User if a custom constructor is declared.
```

## 无参构造方法

这就让我想到上周的文章，那篇文章里面我们其实就发现症结根本不是什么 Int 和 Integer，而是无参构造方法。JavaBean 是 Java 的一个概念，我其实甚至有些觉得 Java 的设计者们通过 JavaBean 这样的概念来弥补语言本身的缺陷——不管怎样，JavaBean 是不能没有无参构造的，。

Kotlin 呢，语言层面就有类似于 JavaBean 的东西，那就是 data class，这俩孩子实在太像了，以至于大家经常把 data class 当做 JavaBean 来使。嗯，你信不信 Kotlin 的设计者也是这么想的呢？当然，用 data class 这样一个名正言顺的“亲儿子”数据类来替代 JavaBean 这么个语言层面没有任何支持和认可的“野孩子”，应该算是 JavaBean 莫大的荣幸了，可问题又出在 Java 语言本身构造方法滥用的潜在问题上了。在 Java 中，构造方法真心是一个很没有存在感的东西，大家总是根据自己的喜好来随意的定义很多个构造方法的版本，而最终忽视掉它们的内在联系，导致没有正常走完初始化逻辑的实例满天飞，这家伙如果是导弹，我估计也不需要解放军就可以直接把台湾给统一了。

说了这么多，我主要是想吐槽两个点：第一个就是 Java 本身语言设计层面几乎没有任何照顾到数据类的体现（可千万别说 clone 和 Serialize），第二个就是 Java 对其对象的实例化过程的把控太过于儿戏。

这两点呢，Kotlin 都做的很好，我现在写 Kotlin 经常被迫认真思考一个类该如何正确初始化，这显然对于我们的程序结构和逻辑梳理有莫大的好处。可是结果呢？Java 时代的那些框架们受不了了。Kotlin 背靠着 Java 这座大山，Java 就像它的父母一样，父母的观念再老再陈旧，Kotlin 也得做好自己该做的，一方面是向现在看来陈旧但在过去已经非常革命的观念致敬，另一方面嘛，如果 Java 不支持个几十万首付，Kotlin 能买得起房吗？

哇塞，我好能扯啊。

其实想要解决 default public constructor 这样的问题，Kotlin 官方已经想到了，那就是 noarg。嗯，我原以为我提一句 noarg 大家就会知道是什么了，看来是我想的简单了，毕竟这个东西在 1.0.6 才出来，当时我还在介绍这个版本的时候提到了它的使用方法，朋友们可能还没有接触过，没关系，下面我再贴一些写法，大家一看就明白：

首先你要做的就是定义一个注解：

```kotlin
annotation class PoKo
```
接着 gradle 配置一下脚本的依赖：

```groovy
buildscript {
...
    dependencies {
		...
        classpath "org.jetbrains.kotlin:kotlin-noarg:$kotlin_version"
		...
    }
}
```
加了运行时环境，那么我们就可以使用 noarg 插件了：

```groovy
apply plugin: "kotlin-noarg"

noArg {
    annotation("net.println.kotlin.realm.PoKo")
}
```
配置完之后，PoKo 这个注解就有了超能力，所有被它标注的类在编译时都会生成一个无参的构造方法，于是我们给 User 加一个 PoKo 的注解：

```kotlin
@PoKo data class User(...) : RealmObject()
```
搞定，果断去编译一下！！

## final 还是不 final，这是个问题

本来兴高采烈的以为不就是个无参构造的问题嘛，结果编译的时候又爆出了新的问题：

```kotlin
Error:(31, 61) error: cannot inherit from final User
```

好家伙，这究竟发生了什么。。原来 Realm 在编译的时候生成了一个类：

```java
public class UserRealmProxy extends net.println.kotlin.realm.User
    implements RealmObjectProxy, UserRealmProxyInterface 
```

这个类要继承我这个 User 类，结果就报错了。

下面是理（che）论（dan）时间。我们说在 C++ 当中给合适的变量、函数参数、函数返回值甚至函数加上 const 是个好习惯，大家没有意见吧？同样的，Java 当中给那些不变的量、不能被继承的类、不能被覆写的方法加上 final 也是个好习惯，大家也没有意见吧？那么问题来了，大家有几个人这么干了？是不是不到万不得已，才懒得写那个 final 呢，五个字母呢，你是想累死宝宝啊？我就知道 Effective Java 这本书看了也白看，因为大家经常明知道什么是好习惯却还是要对着干，这个不是因为大家不喜欢好习惯，而是因为坚持好习惯需要成本！不瞒各位说，我中午为了坚持午休的好习惯，牺牲了跟组里面的小伙伴一起开黑上分的机会，还得装着拥护“人民ri报”关于“小学生打排位太坑”的评论，我容易么我。。

嗯，扯远了。还是说 final 的事儿，Kotlin 就做的很好，它默认所有的类、变量、方法都是 final 的，想要继承？来，过来申请我给你审批。。。你看，这样从根儿解决问题，我们再也不用为了坚持好习惯而发愁了，因为我们根本不需要坚持，难道你想要坚持坏习惯嘛？

可是 Java 及其框架们呢？原来到北京买房有钱就行，现在呢，商住都不让买了啊（什么？你说广州都不让卖了？）。那叫一个不适应，这可不是得闹事儿么。

Kotlin 官方考虑到 Java 帮它出首付买房的事儿，想了想算了，还是出个什么插件，解决下这个问题吧，于是 allopen 闪亮登场！allopen 的原理跟 noarg 极其类似，它是在编译器对指定的类进行去“final”化，你别看你写代码的时候 User 还是个 final 的类，不过编译成字节码之后这天呀可就变了。

关于 allopen 的使用，跟 noarg 简直不要太像，先定义一个注解：

```kotlin
annotation class PoKo // How old r U!
```
可以跟 noarg 公用同一个注解，也可以自己另外单独定义一个，这个不要紧。

接着 gradle 配置搞起：

```groovy
buildscript {
	...
    dependencies {
		...
        classpath "org.jetbrains.kotlin:kotlin-allopen:$kotlin_version"
		...
    }
}
```
接着就是应用插件，配置注解一气呵成：

```groovy
apply plugin: "kotlin-allopen"

allOpen {
    annotation("net.println.kotlin.realm.PoKo")
}
```
编译运行~

ps：如果加了 allopen 和 noarg 之后编译仍然提示原来的错误，记得狠狠地 clean 一下才行哈。

## 

## 认真脸：究竟什么是 “坑”

前面说了 Kotlin 的两个 “坑”，都是关于 data class 的。有人认为这么说 Kotlin 不公平，毕竟人家 Kotlin 也是可以写出下面的代码的：

```kotlin
class User{
	var id: String? = null
	var name: String? = null
}
```

尽管你在为 Kotlin 打抱不平，不过如果你真要写这样的代码，我建议你还是用 Java 吧。你不属于 Kotlin。。。

Kotlin 这么美的语言，怎么能写这么丑陋的东西呢？这就好比有人说为什么空类型强转为非空类型一定要两个感叹号呢，用一个不就够了么，两个看起来好丑呀！

```kotlin
var user: User? = getUser()
user!!.name = "小明" //小明，他们说你丑！
```

有人回答说：明明这就是丑陋的东西，为什么要美化？掩盖事物的本质只能让事情变得更糟糕！

我们用 Kotlin 企图兼容 Java 的做法，本来就是权宜之计，兼容必然带来新旧两种观念的冲突以及丑陋的发生，这么说来，我倒是更愿意期待 Kotlin Native 的出现了。