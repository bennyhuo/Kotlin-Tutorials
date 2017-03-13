# Kotlin Script 及其运行机制简析

## 1. 认识 kts

打开你的 IntelliJ，随便找个位置，注意我说的，随便找个位置，创建一个文件，命名为 Hello.kts，然后你就会发现 IntelliJ 能够识别这种类型，文件的 icon 与 kt 后缀的 kotlin 文件没啥区别。

那你知道你创建了一个什么东西吗？它究竟与平时我们写的 Kotlin 代码有啥区别呢？其实，从名字我们就可以了解到，这是一个 Kotlin 的脚本文件，我们可以在其中直接写函数调用，逻辑判断，数值计算，干什么都行。

**Hello.kts**

```kotlin
import java.io.File

println("Hello from kts")

val file = File(".")
file.listFiles().forEach(::println)

println("The End.")
```

这段代码能输出什么呢？

```
Hello from kts
./.gradle
./.idea
./build
./build.gradle
./gradle
./gradlew
./gradlew.bat
./Hello.kts
./settings.gradle
./src
The End.
```

我恰好把这个脚本文件放到了一个工程的目录下面，于是它输出了这个工程根目录的所有文件。

## 2. 命令行调用 kts

如果只是在 IntelliJ 当中能够运行脚本，那多没意思。脚本就是要放到命令行跑的，就跟 python 一样，当成 shell 的神助攻来帮我们处理一下任务才好。

IntelliJ 的运行方法当然也是可以的，我们不妨把它的命令复制过来给大家看一下：

```bash
$JAVA_HOME/java -Dfile.encoding=UTF-8 -classpath "$INTELLIJ_HOME/Kotlin/kotlinc/lib/kotlin-compiler.jar:$INTELLIJ_HOME/Kotlin/kotlinc/lib/kotlin-reflect.jar:$INTELLIJ_HOME/Kotlin/kotlinc/lib/kotlin-runtime.jar:$INTELLIJ_HOME/Kotlin/kotlinc/lib/kotlin-script-runtime.jar" org.jetbrains.kotlin.cli.jvm.K2JVMCompiler -script /Users/benny/workspace/temp/Forty/Hello.kts
```

不知道大家看明白没，Kotlin 的编译器或者说脚本运行时环境都是 jar 包，用 Java 直接调用就 OK 了。不过这么复杂的命令我可不想每次都写。

嗨，这你还犹豫什么，赶紧安装 kotlin 的安装包，里面有 kotlinc 和 kotlin 这样的命令，用法几乎与 javac 和 java 一模一样。安装方法[点这里](http://kotlinlang.org/docs/tutorials/command-line.html)。

啊，你说安装好了？那么这时候你运行 kotlinc，是不是会出现一个响应式终端呢？ 跟 Python Scala 之类的一样呢？

``` bash
$ kotlinc
Welcome to Kotlin version 1.0.6-release-127 (JRE 1.8.0_60-b27)
Type :help for help, :quit for quit
>>>
```

你可以在里面随便敲个运算式啥的，从今天开始，kotlin 也可以成为你的御用“计算器”啦！

额，扯远了，现在我们该说说怎么运行刚才那个脚本了：

```bash
$ kotlinc -script Hello.kts
Hello from kts
./.gradle
./.idea
./build
./build.gradle
./gradle
./gradlew
./gradlew.bat
./Hello.class
./Hello.kts
./settings.gradle
./src
The End.
```

我最初觉得应该是类似 python 那样直接运行，可结果却有点儿让人尴尬。。好吧，随便啦。

```bash
$ kotlin Hello.kts
error: running Kotlin scripts is not yet supported
```

## 3. 我的 main 函数去哪儿啦？

我们都知道，Java 虚拟机上面的程序入口是 main 函数，嗯，就连 Android dalvik 那个算不上真正意义上的 Java 虚拟机的虚拟机，入口函数也是 main 呢！可是前面的脚本分明就没有 main 函数，还跑得挺欢啊，这简直不能让人相信爱情了（什么？跟爱情有毛关系？！）。。

好吧，这个事儿我们还是要仔细探查一下，不然毁了三观可不好。Java 系的孩子，还是要有点儿信仰的，嗯，信仰 main 的存在~

且说，我们运行 kotlinc 这个程序，你知道它是什么吗？不知道？没关系，找着它，结果发现丫其实就一 shell 脚本。。我去，搞得这么恐怖，原来就一 shell。。

**Mac 版 kotlinc** 部分 

```bash
...
if [ -n "$KOTLIN_RUNNER" ];
then
    java_args=("${java_args[@]}" "-Dkotlin.home=${KOTLIN_HOME}")
    kotlin_app=("${KOTLIN_HOME}/lib/kotlin-runner.jar" "org.jetbrains.kotlin.runner.Main")
else
    [ -n "$KOTLIN_COMPILER" ] || KOTLIN_COMPILER=org.jetbrains.kotlin.cli.jvm.K2JVMCompiler
    java_args=("${java_args[@]}" "-noverify")
    kotlin_app=("${KOTLIN_HOME}/lib/kotlin-preloader.jar" "org.jetbrains.kotlin.preloading.Preloader" "-cp" "${KOTLIN_HOME}/lib/kotlin-compiler.jar" $KOTLIN_COMPILER)
fi
```

我们看到了什么？org.jetbrains.kotlin.cli.jvm.K2JVMCompiler ？小哥，你看起来好生面熟啊，哪儿见过呢？

```bash
$JAVA_HOME/java -Dfile.encoding=UTF-8 -classpath "$INTELLIJ_HOME/Kotlin/kotlinc/lib/kotlin-compiler.jar:$INTELLIJ_HOME/Kotlin/kotlinc/lib/kotlin-reflect.jar:$INTELLIJ_HOME/Kotlin/kotlinc/lib/kotlin-runtime.jar:$INTELLIJ_HOME/Kotlin/kotlinc/lib/kotlin-script-runtime.jar" org.jetbrains.kotlin.cli.jvm.K2JVMCompiler -script /Users/benny/workspace/temp/Forty/Hello.kts
```

原来，IntelliJ 运行 kts 用到的命令，在 kotlinc 当中也是一样一样的，嗯哈，这就有意思了，我们运行一段脚本的程序入口原来在 K2JVMCompiler 当中，那我们不妨找着它的源码一看究竟~

*K2JVMCompiler 在 Kotlin 源码的 compiler/cli 模块下。*

它的入口方法倒也直接了当，

```kotlin
@JvmStatic fun main(args: Array<String>) {
    CLICompiler.doMain(K2JVMCompiler(), args)
}
```

原来这只是一个壳而已，我们还是一步步往下追查吧。在追查之前呢，我们需要一点儿想象力，猜测一下 kts 是如何运行的。

首先可以确定的是 kts 并没有 main 函数，所以一种可能是 kotlin 编译器在运行时给它生成一个 main 函数，然后调用它。这里有个问题，如果这个调用时普通 Java 虚拟机程序的那样调用的话，这就意味着 kts 执行的过程会有两个进程存在，一个是我们刚才执行的命令，另一个是以动态生成 main 为入口函数的 kts 文件。

还有一种可能，kts 文件直接编译成一个普通的类，直接在 kotlinc 的运行时中类加载并且运行。

另种方式比较下来，显然第二种最为简单，不过我们在 kts 当中写的代码究竟是作为哪部分代码运行的呢？

>**“元芳，你怎么看？”**
>
>“大人所言极是呀！只是小可有一事不明。。。”
>
>**“你哪儿来那么多事儿。。”**

好，猜测完毕，开始查案~

刚一开始看了两行就给我逗乐了，这个代码跳来跳去的，最后竟然又回到了 K2JVMCompiler.doExecute 方法，接着又到了 KotlinToJVMBytecodeCompiler.compileAndExecuteScript，这里基本上告诉我们 Kotlinc 会直接编译 kts 并且加载运行它。

```kotlin
fun compileAndExecuteScript(
        environment: KotlinCoreEnvironment,
        paths: KotlinPaths,
        scriptArgs: List<String>): ExitCode
{
	...
    val scriptClass = compileScript(environment, paths) 
    tryConstructClassFromStringArgs(scriptClass, scriptArgs)
	...
}
```
我们看下这个方法的内容，省略掉异常处理的代码之后，第一句是编译这个 kts ，得到 scrpitClass，这实际上就是一个 Java Class，后面的 tryConstructClassFromStringArgs 则是要实例化这个类，scriptArgs 则是我们在运行这个脚本时传入的其他参数，这里作为脚本生成的类 scriptClass 的构造方法的参数传入。

```kotlin
fun tryConstructClassFromStringArgs(clazz: Class<*>, args: List<String>): Any? {

    try {
        return clazz.getConstructor(Array<String>::class.java).newInstance(args.toTypedArray())
    } catch (e: NoSuchMethodException) {
        for (ctor in clazz.kotlin.constructors) {
            val mapping = tryCreateCallableMappingFromStringArgs(ctor, args)
            if (mapping != null) {
               return ctor.callBy(mapping)
            }
        }
    }
    return null
}
```

哦，这样我们就明白了，原来 kts 当中的代码其实是被编译成类的构造方法来运行的。这么说来我们还可以给脚本传入参数，在脚本当中引用命令行传入的参数也不难：

```kotlin
println("Hello from kts, args below: ")

args.forEach(::println)

println("The End.")
```
运行输出：

```bash
$ kotlinc -script Hello.kts X-Man Wolfrine
Hello from kts, args below: 
X-Man
Wolfrine
The End.
```

注意，如果你需要单步调试上面的过程，可以直接在 IntelliJ 当中右键运行 org.jetbrains.kotlin.cli.jvm.K2JVMCompiler，参数填入 -script [kts文件的路径]即可。如果遇到下面的错误：

```bash
Class 'xxx' is compiled by a pre-release version of Kotlin and cannot be loaded by this version of the compiler
```
确保你的编译环境和 IntelliJ 插件一致的前提下，加入 -Xskip-metadata-version-check 参数来忽略错误即可。

## 4. 小结

通过这篇文章我们不仅知道了 Kotlin 可以支持脚本方式运行，还知道了其运行的原理：编译成一个类，脚本代码作为其构造方法运行，命令行参数作为构造方法的参数传入。

其实前面这段分析本身没有什么难度，它最有价值的地方在于它为我们提供了一个方便快捷了解 Kotlin 内部运行机制的入口，哪里不会断哪里，妈妈再也不用担心我的 Kotlin~

<center>![](../../arts/kotlin扫码关注.png)</center>