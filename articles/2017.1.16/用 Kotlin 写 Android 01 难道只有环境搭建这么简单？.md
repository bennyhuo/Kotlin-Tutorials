# [用 Kotlin 写 Android] 01 难道只有环境搭建这么简单？

从这周开始，每周一的文章推送将连载 Kotlin Android 开发的文章，大家有关心的题目也可以直接反馈给我，这样也可以帮助我提高后续文章的针对性。

亲爱的小伙伴，阅读本文之前，请确保你对 Kotlin 有一定的了解，并且你的 Android Studio 或者 IntelliJ Idea 已经安装了 Kotlin 的插件。如果没有，果断回去先看我的 [Kotlin 视频](https://github.com/enbandari/Kotlin-Tutorials) 第一集 :)

## 1 千里之行，始于 Hello World

话说我们入坑 Kotlin 之后，要怎样才能把它运用到 Android 开发当中呢？我们作为有经验的开发人员，大家都知道 Android 现在基本上都用 gradle 构建，gradle 构建过程中只要加入 Kotlin 代码编译的相关配置，那么 Kotlin 的代码运用到 Android 的问题就解决了。

这个问题有何难呢？Kotlin 团队早就帮我们把这个问题解决了，只要大家在 gradle 配置中加入：

```groovy
apply plugin: 'kotlin-android'
```

就可以了，这与我们在普通 Java 虚拟机的程序的插件不太一样，其他的都差不多，比如我们需要在 buildScript 当中添加的 dependencies 与普通 Java虚拟机程序毫无二致：

```groovy
buildscript {
    ext.kotlin_version = '1.0.6'//版本号根据实际情况选择
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:2.2.0'
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
    }
}
```
当然，我们还要在应用的 dependencies 当中添加 Kotlin 标准库：

```groovy
compile "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"
```

有了这些，你的 Kotlin 代码就可以跑在 Android 上面了！当然，你的代码写在 src/main/java 或是 src/main/kotlin 下都是可以的。这不重要了，我觉得把 Java 和 Kotlin 代码混着写就可以了，没必要分开，嗯，你最好不要感觉到他们是两个不同的语言，就酱紫。

```Kotlin
package net.println.kotlinandroiddemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.hello) as TextView
        textView.text = "Hello World"
    }
}
```

我们定义一个 TextView 的成员，由于我们只能在 onCreate 当中初始化这个成员，所以我们只好用 lateinit 来修饰它。当然，如果你不怕麻烦，你也可以选择 TextView? ，然后给这个成员初始化为 null。

接着我们就用最基本的写法 findViewById、类型强转拿到这个 textView 的引用，然后 setText。

运行自然是没有问题的。

不过，不过！我如果就写这么点儿就想糊弄过去这一周的文章，番茄鸡蛋砸过来估计够我吃一年的西红柿炒鸡蛋了吧（我~就~知~道~，我这一年不用愁吃的了！）

## 2 Anko 已经超神

要说用 Kotlin 写 Android，Anko 谁人不知谁人不晓，简直到了超神的地步。好好，咱们不吹牛了，赶紧把它老人家请出来：

```groovy
compile 'org.jetbrains.anko:anko-sdk15:0.9' // sdk19, sdk21, sdk23 are also available
compile 'org.jetbrains.anko:anko-support-v4:0.9' // In case you need support-v4 bindings
compile 'org.jetbrains.anko:anko-appcompat-v7:0.9' // For appcompat-v7 bindings
```

稍微提一句 anko-sdk 的版本选择：

* org.jetbrains.anko:anko-sdk15 ： 15 <= minSdkVersion < 19
* org.jetbrains.anko:anko-sdk19 ： 19 <= minSdkVersion < 21
* org.jetbrains.anko:anko-sdk21 ： 21 <= minSdkVersion < 23
* org.jetbrains.anko:anko-sdk23 ： 23 <= minSdkVersion

当然除了这些之外，anko 还对 cardview、recyclerview等等做了支持，大家可以按需添加，详细可以参考 [Github - Anko](https://github.com/Kotlin/anko)

另外，也建议大家用变量的形式定义 anko 库的版本，比如：

```groovy
ext.anko_version = "0.9"
...

compile "org.jetbrains.anko:anko-sdk15:$anko_version" // sdk19, sdk21, sdk23 are also available
compile "org.jetbrains.anko:anko-support-v4:$anko_version" // In case you need support-v4 bindings
compile "org.jetbrains.anko:anko-appcompat-v7:$anko_version" // For appcompat-v7 bindings
```

好，有了 Anko 我们能干什么呢？

```kotlin
textView = find(R.id.hello)
```
还记得 findViewById 么？变成 find 了，而且强转也没有了，是不是很有趣？你一定有疑问，Anko 究竟干了啥，一下子省了这么多事儿，我们跳进去看看 find 的真面目：

```kotlin
inline fun <reified T : View> View.find(id: Int): T = findViewById(id) as T
inline fun <reified T : View> Activity.find(id: Int): T = findViewById(id) as T
inline fun <reified T : View> Fragment.find(id: Int): T = view?.findViewById(id) as T
```

首先它是个扩展方法，我们暂时只用到了 Activity 的扩展版本，实际上 View、Fragment 都有这个扩展方法；其次，它是个 inline 方法，并且还用到了 reified 泛型参数，我们本来应该这么写：

```kotlin
textView = find<TextView>(R.id.hello)
```
由于泛型参数的类型可以很容易的推导出来，所以我们再使用 find 的时候不需要显式的注明。

说到这里，其实还是有问题没有说清楚的，reified 究竟用来做什么？其实我们就算不写 inline 和 reified 泛型，这个方法照样是可以用的：

```kotlin
fun <T : View> Activity.myFind(id: Int): T = findViewById(id) as T
```

```kotlin
textView = myFind(R.id.hello)
```

不过呢，这地方用 inline 就省了一次函数调用，并且 reified 也可以消除 IDE 的类型检查提示，所以既然可以，为什么不呢？

当然，用 Anko 的好处不可能就这么点儿，我们今天先按住不说，谁好奇的话可以先自己去看看（我~就~知~道~，你们肯定忍不住！！）~

## 3 不要 findViewById

作为第一篇介绍 Kotlin 写 Android 的文章，绝对不能少的就是 kotlin-android-extensions 插件了。在 gradle 当中加配置：

```groovy
apply plugin: 'kotlin-android-extensions'
```

之后，我们只需要在 Activity 的代码当中直接使用在布局中定义的 id 为 hello 的这个 textView，于是：

```kotlin
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

//这个包会自动导入
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //直接使用 hello，hello 实际上是这个view 在布局当中的id
        hello.text = "Hello World"
    }
}
```

只要布局添加一个 View，在 Activity、View、Fragment 中其实都可以直接用 id 来引用这个 view，超级爽~

所以，你们不准备问下这是为什么吗？为什么可以这样做呢？

其实要回答这个问题也不难，首先 Android Studio 要能够从 IDE 的层面索引到 hello 这个 View，需要 Kotlin 的 IDE 插件的支持（别问我啥是 IDE 插件，你们用 Kotlin 的第一天肯定都装过）；其次，在编译的时候，编译器能够找到 hello 这个变量，那么还需要 Kotlin 的 gradle 插件支持（我们刚刚好像 apply 了个什么 plugin 来着？）。知道了这两点，我们就要有的放矢了~

>“啊！” 那边的 Kotlin 源码一声惨叫。。。

前方高能。。我们讨论的源码主要在 plugins 目录下的 android-extensions-compiler 和 android-extensions-idea 两个模块当中。

如果让大家自己实现一套机制来完成上面的功能，大家肯定会想，我首先得解析一下 XML 布局文件吧，并把里面的 View 存起来，这样方便后面的查找。我告诉大家，Kotlin 也是这么干的！

**AndroidXmlVisitor.kt**

```kotlin
override fun visitXmlTag(tag: XmlTag?) {
	...

    val idAttribute = tag?.getAttribute(AndroidConst.ID_ATTRIBUTE)
    if (idAttribute != null) {
        val idAttributeValue = idAttribute.value
        if (idAttributeValue != null) {
            val xmlType = tag?.getAttribute(AndroidConst.CLASS_ATTRIBUTE_NO_NAMESPACE)?.value ?: localName
            val name = androidIdToName(idAttributeValue)
            if (name != null) elementCallback(name, xmlType, idAttribute)
        }
    }
    tag?.acceptChildren(this)
}
```
这是遍历 XML 标签的代码，典型的访问者模式对吧。如果拿到这个标签，它有 android:id 这个属性，那么小样儿，你别走，老实交代你的 id 是什么！举个例子，如果这个标签是这样的：

```xml
<Button
    android:id="@+id/login"
	... />
```

那么，name 就是 login 了，既然 name 不为空，那么调用 elementCallback，其实就是把它记录了下来。

**IDEAndroidLayoutXmlFileManager.kt**

```kotlin
override fun doExtractResources(files: List<PsiFile>, module: ModuleDescriptor): List<AndroidResource> {
    val widgets = arrayListOf<AndroidResource>()
    
    //注意到这里的 Lambda 表达式就是前面的 elementCallback
    val visitor = AndroidXmlVisitor { id, widgetType, attribute ->
        widgets += parseAndroidResource(id, widgetType, attribute.valueElement)
    }

    files.forEach { it.accept(visitor) }
    
    //返回所有带 id 的 view
    return widgets
}
```

接着想既然我们找到了所有的布局带有 id 的 view，那么我们总得想办法让 Activity 它们找到这些 view 才行对吧，而我们发现其实在引用它们的时候总是要导入一个包，包名叫做：

```
kotlinx.android.synthetic.main.<布局文件名>.*
```

几个意思？Kotlin 编译器为我们创建了一个包？

**AndroidPackageFragmentProviderExtension.kt**

```kotlin
...
createPackageFragment(packageFqName, false)
createPackageFragment(packageFqName + ".view", true)
...
```

注意到，这里的 packageFqName 其实就是我们前面提到的


```
kotlinx.android.synthetic.main.<布局文件名>
```

不对呀，怎么创建了两个包呢？其实第二个多了个 .view ，我们在 Activity 当中 导入的包是第一个，但如果是我们用父 view 引用子 view 时，用的是第二个：

```kotlin
...
import kotlinx.android.synthetic.main.activity_main.view.*

class OverlayManager(context: Context){
    init {
        val view = LayoutInflater.from(context).inflate(R.layout.activity_main, null)
        view.hello.text = "HelloWorld"
        ...
    }
    
    ...
}
```

好，我们现在知道了，IntelliJ 居然已经通过解析 XML 帮我们偷偷搞出了这么两个虚拟的包，这样我们在代码当中能够引用到这个包就很容易解释了。

这时候可能还会有人比较疑惑点击了 Activity 的 hello 之后如何跳转到 XML 的，这个大家阅读一下 ```AndroidGotoDeclarationHandler``` 的源码就会很容易的看到答案。

费了这么多篇幅，其实我们只是做好了表面文章。上面的一切其实都是障眼法，别管怎么说，这两个包都是虚拟的，编译的时候该怎么办？

其实编译就简单多了，碰到这样的引用，比如前面的 hello，直接生成 findViewById 的字节码就可以了，我们把 ```hello.text = "HelloWorld"``` 的字节码贴出来给大家看：

```
L2
LINENUMBER 12 L2
ALOAD 0
GETSTATIC net/println/kotlinandroiddemo/R$id.hello : I
INVOKEVIRTUAL net/println/kotlinandroiddemo/MainActivity._$_findCachedViewById (I)Landroid/view/View;
CHECKCAST android/widget/TextView
LDC "Hello World"
CHECKCAST java/lang/CharSequence
INVOKEVIRTUAL android/widget/TextView.setText (Ljava/lang/CharSequence;)V
```

这个是怎么做到的？请大家阅读 ```AndroidExpressionCodegenExtension.kt```，

```kotlin
...
//GETSTATIC net/println/kotlinandroiddemo/R$id.hello : I
v.getstatic(packageName.replace(".", "/") + "/R\$id", resourceId.name, "I")
    
//INVOKEVIRTUAL net/println/kotlinandroiddemo/MainActivity._$_findCachedViewById (I)Landroid/view/View;
v.invokevirtual(declarationDescriptorType.internalName, 
	CACHED_FIND_VIEW_BY_ID_METHOD_NAME, 
	"(I)Landroid/view/View;", false)
...
```

好，到这里，想必大家才能对 Android 的 HelloWorld 代码有一个彻底的理解。

## 4 小结

虽然是 HelloWorld，但要想搞清楚其中的所有秘密，并没有那么简单，很多时候，阅读 Kotlin 源码几乎成了唯一的途径。

谢谢大家的关注和支持~如果有什么问题可以联系我~