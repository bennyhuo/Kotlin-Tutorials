# [用 Kotlin 写 Android] 02 说说 Anko

上周的文章其实我们提到了 Anko 的，不过我们只是给大家展示了一下 find 方法。除了这个之外，还有哪些好玩的东西呢？

## 1、简化页面操作

我们写 Android 最先做的是什么？当然是设置个 OnClickLisener，这样自然我的按钮听我的，我的地盘我做主了。

```kotlin
hello.onClick {
    startActivity<AnotherActivity>("from" to "MainActivity")
}
```

哎哟，不错哦。其中 hello 是一个 TextView，我们通过 onClick 为其设置了一个 OnClickListener，这样看上去真是简洁不少。

```kotlin
fun android.view.View.onClick(l: (v: android.view.View?) -> Unit) {
    setOnClickListener(l)
}
```
也没什么难理解的，onClick 是一个扩展方法，传入的 Lambda 表达式通过 SAM 转换成了 OnClickListener，一切都是这么的自然。如果你对传入的 view 感兴趣，你当然可以直接用 it 召唤它：

```kotlin
hello.onClick {
	Log.d(TAG, it.toString())
    startActivity<AnotherActivity>("from" to "MainActivity")
}
```
简单吧。

**等等！那个 startActivity 是怎么回事？没有 Intent 么？**

哈哈，这个嘛，且看源码：

```kotlin
inline fun <reified T: Activity> Context.startActivity(vararg params: Pair<String, Any>) {
    AnkoInternals.internalStartActivity(this, T::class.java, params)
}

...

fun internalStartActivity(
        ctx: Context,
        activity: Class<out Activity>,
        params: Array<out Pair<String, Any>>
) {
    ctx.startActivity(createIntent(ctx, activity, params))
}
```
其实也没什么，就是对我们之前模板式的跳转写法做了简化而已，至于用到的 reified 和 Pair 也不算什么新鲜的东西，Pair 当中的 K-V 实际上就是我们通常放入 Intent 的 extra，所以我们自然可以在 AnotherActivity 当中取到这个值：

```kotlin
class AnotherActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        relativeLayout {
            textView {
                text = if(intent == null) 
                			"from nowhere" 
	                	else 
   		             		intent.extras["from"]?.toString()
            }
        }
    }
}
```

取到我们传入的值，在 AnotherActivity 当中显示出来。有木有觉得要比我们用 Java 老大哥写出来的代码简洁易懂呢？

## 2、聊聊 DSL 布局

**再等等！那个 ```relativeLayout{...}```是几个意思？？**

嗯，这个要多说几句了，Anko 这个框架虽然打着简化开发的旗号，不过野心终归还是不小的。它自己搞出一套用 Kotlin 写布局的 DSL，换句话说，有了 Anko 我们布局甚至可以不需要用 XML 了，也不需要像用 Java 硬编码 View 那么繁琐，只需要通过几句 DSL 就可以搞定。

前面的例子就是这样，我们在 onCreate 当中定义了一个 RelativeLayout，其中又添加了一个 TextView 作为它的子 View，紧接着又给这个 TextView 设置了内容。

Anko DSL 的方式布局看上去还是比较清爽直观的，而且因为这是 Kotlin 代码，自然所有的view 都是强类型约束的，不需要我们 findViewById 再强转，除此之外由于是代码，可以直接运行，也就省去了运行时解析 XML 的开销，这一点可以说也是相比于 Android 官方的 XML 布局而言 Anko 主打的性能优势。

说归说，它的这些优势用眼一看便知，可是它存在哪些问题呢？

* 首先，Anko DSL 布局不能预览。可以说这一点足以让我们放弃它了，不能预览的话很多时候我们只能通过运行结果来判断布局是否准确，这对开发效率的影响是巨大的。当然，这么说可能 Anko 不服，毕竟人家也是发布了一个叫 Anko Preview Plugin 的 IDE 插件的，有了这个插件理论上我们就可以预览 Anko DSL 的布局结果了对吧？可是结果呢，每次做了修改都需要 make 一下才可以看到结果，显然预览速度来看不如 XML 快。而就算这个问题我们可以忍，慢就慢点儿，别慢太多就行了吧，结果呢，人家这个插件存在各种各样的问题，比如对最新版的 Android Studio 2.2 和 IntelliJ 2016.3 不支持（当然其实本质上是对新版本的 Preview 功能不兼容），大家可以参考这个 issue：https://github.com/Kotlin/anko/issues/202。也就是说，这个插件现在是不能用的，所以跟没有也没啥区别。
* 其次，对于 id 的定义会比较蛋疼。我们知道我们在布局的时候可以通过 ```@+id/xxx``` 的方式生成一个 id，并交给 Android 资源管理器统一管理，用 Anko DSL 的话我们就得专门定义一个变量去让 view 引用。不用 id 行不行呢？你去问问 RelativeLayout 答应不答应吧。
	
	```kotlin
    val FROM_TEXT = 0
    val CLICK_ME = 1
    relativeLayout {
        textView {
            text = ...
            id = FROM_TEXT
        }
        
        button("clickMe"){
            id = CLICK_ME
        }.lparams { 
            below(FROM_TEXT)
        }
    }
	```
* 再次，我们通常会需要引用一些 view，通过 XML 布局 + kotlin-android-extensions 的方式，我们可以直接引用到这些有 id 的 view，非常方便，不过，如果我们用 Anko DSL 布局的话，我们就享受不到这项福利了。

	```kotlin
	val FROM_TEXT = 0
    val CLICK_ME = 1
    var fromText: TextView? = null
    relativeLayout {
        fromText = textView {
            text = ...
            id = FROM_TEXT
        }
        
        button("clickMe"){
            id = CLICK_ME
        }.lparams { 
            below(FROM_TEXT)
        }
    }
    
    ...
    
    if(shouldHideText) fromText?.visibility = View.GONE
    else fromText?.visibility = View.VISIBLE
	```
* 还有就是，如果我们的布局有多个版本，而且需要动态替换外部资源以达到换肤的效果，那么 XML 显然比 Kotlin 代码要来得容易：前者可以编译成一个只有资源的 apk 供应用加载，后者的话就得搞一下动态类加载了。

总之，Anko DSL 布局这个特性我个人觉得还没有达到可以取代 XML 布局的地步，如果大家习惯用 Java 硬编码 View 结构的话，Anko DSL 是个不错的选择；相反，如果大家一直用 XML 的话，那请接着用。当然，如果大家有好的使用方式，无论如何要来我这儿跟我嘚瑟一下哈~

## 3、简化异步操作

假如你要在点击按钮之后把一个文件（本地或者服务端，也可能比较大，总之读取耗时）当中的文字显示出来，你用 Java 会怎么写呢？

```java
button.setOnClickListener(new OnClickListener(){
	@Override public void onClick(View view){
		getExecutor().execute(new Runnable(){
			@Override public void run(){
				...
				MainActivity.this.runOnUiThread(new Runnable(){
					...
				});
			}
		})
	}
});
```
哎呀我去，真是蜜汁缩进啊，我都写晕了。可是有了 Anko 配合，这段代码简直不能更清爽：

```kotlin
button.onClick {
    doAsync {
        val text = File("You raise me up.lrc").readText()
        uiThread {
            hello.text = text
        }
    }
}
```

doAsync 当中的代码运行在 Anko 配置的线程池当中，执行完之后还可以转入 uiThread 块来操作 UI，简单明了，还不容易出错。你当然也可以处理异常和自定义线程池：

```kotlin
doAsync(
    exceptionHandler = {
        Log.e(TAG, "error happened when read file.", it)
    },
    task = {
        val text = File("You raise me up.lrc").readText()
        uiThread {
            hello.text = text
        }
    },
    executorService = Executors.newSingleThreadExecutor()
)
```

其实大家肯定想到了这两个方法的实现逻辑：

```kotlin
fun <T> T.doAsync(
        exceptionHandler: ((Throwable) -> Unit)? = null,
        executorService: ExecutorService,
        task: AnkoAsyncContext<T>.() -> Unit
): Future<Unit> {
    val context = AnkoAsyncContext(WeakReference(this))
    return executorService.submit<Unit> {
        try {
            context.task()
        } catch (thr: Throwable) {
            exceptionHandler?.invoke(thr)
        }
    }
}

...

fun <T> AnkoAsyncContext<T>.uiThread(f: (T) -> Unit): Boolean {
    val ref = weakRef.get() ?: return false
    if (ContextHelper.mainThread == Thread.currentThread()) {
        f(ref)
    } else {
        ContextHelper.handler.post { f(ref) }
    }
    return true
}
```

## 4、简化日志打印

不知道大家有没有觉得 ```Log.d(TAG, ...)``` 这样的代码写起来麻烦，绝大多数情况下，我们打日志都需要多写个 Log. 除非静态导入方法，以及 TAG 的值通常都是对应的类名，有时候我只是为了临时打印一行日志，还得去定义一个静态常量 TAG，简直了，还有就是如果我只是想要打印一下某一个对象，还得显式得调用 toString 方法，一点儿都不智能。

```java
public class MainActivity extends Activity{
	public static final String TAG = "MainActivity";
	
	...
	View view = ...
	Log.d(TAG, view.toString());
	...

}
```

有了 Anko 就要简单的多了，只要实现 AnkoLogger 这个接口，我们就可以愉快的打印日志了：

```kotlin
class SomeActivity : Activity(), AnkoLogger {
    private fun someMethod() {
        info("London is the capital of Great Britain")
        debug(5) // .toString() method will be executed
        warn(null) // "null" will be printed
    }
}
```

日志的 TAG 默认就是类名称，如果你需要自定义，那也没关系，直接覆写这个变量就可以了：

```kotlin
override val loggerTag: String = "SomeActivityTag"
```

## 5、小结

Anko 这个框架其实没有什么复杂的地方，它更多的是在想办法简化我们的“八股文”代码，让我们的生活更轻松一些而已。DSL 布局是一个很不错的尝试，不过现在看来还是不太完美的，XML 本身也没有太大的问题，想必后续大家完全转向 DSL 的动力也不会很大。

除了前面提到的特性，Anko 还可以简化对话框、toast、sqlite 等操作，相比之下，toast 的用法还是比较常用的，也比较简单，我就不细说了；至于 sqlite ，通常我们也不建议去直接操作它，用一些 ORM 框架可能会让你的代码更友好。