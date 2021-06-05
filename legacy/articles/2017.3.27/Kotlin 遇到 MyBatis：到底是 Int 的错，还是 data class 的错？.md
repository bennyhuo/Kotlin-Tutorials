# Kotlin 遇到 MyBatis：到底是 Int 的错，还是 data class 的错？

[toc]

## 问题出现

前不久刚刚应小伙伴的要求拉了个 QQ 群：162452394 (微信群可以加我微信 enbandari，邀大家进群)，上周一的时候在公众号推送了之后一下子就热闹起来了。

<img src="../../arts/e_group.png" width="250px"/>


话说有个哥们在群里面问了这么一个问题，他用 MyBatis 来接入数据库，有个实体类用 Kotlin 大概是这么写的：

```kotlin
data class User (var id: Int, var username: String, var age: Int, var passwd: String)
```

它对应的数据库表是这样的：

```sql
CREATE TABLE userinfo
(
    id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    username VARCHAR(45),
    age INT(11),
    passwd VARCHAR(45)
);
```

字段顺序也都能对得上。

然后呢，他就配置了这么一条查询语句：

```xml
<mapper namespace="net.println.kotlin.mybatis.UserMapper">
    <select id="selectUser" resultType="net.println.kotlin.mybatis.User">
        select * from userinfo where id = #{id}
    </select>
</mapper>
```
对应的 UserMapper 代码如下：

```kotlin
public interface UserMapper {
    User selectUser(int id);
}
```
这一切看上去似乎一点儿毛病都没有哇，可一旦他调用 selectUser 方法之后，程序开始抱怨了：

```
No constructor found in net.println.kotlin.mybatis.User matching [java.lang.Integer, java.lang.String, java.lang.Integer, java.lang.String]
```

啥问题呢？找不到构造方法。当时看到这个问题的时候正好手里有活，没有仔细看，周末特意照着写了个 demo，果然。。嗯。。居然找不到构造方法，这就有意思了。

## 问题探究 ① —— Kotlin 的类型映射

按理说，我们的 data class 是有构造方法的，说找不到构造方法倒也有些不公平，应该确切的说是找不到合适的构造方法。前面那句错误信息告诉我们 MyBatis 想要找的构造方法是下面的签名：

```java
init(java.lang.Integer, java.lang.String, java.lang.Integer, java.lang.String)
```
我们的 data class 的构造方法呢？

```java
init(kotlin.Int, kotlin.String, kotlin.Int, kotlin.String)
```
嗯，乍一看确实不一样哈，难怪找不到合适的构造方法。这样说对吗？我在之前有篇文章[为什么不直接使用 Array<Int> 而是 IntArray ？](https://mp.weixin.qq.com/s?__biz=MzIzMTYzOTYzNA==&mid=2247483762&idx=1&sn=c553cdd4b0bccd59e530a9f231037d73&chksm=e8a05e4fdfd7d759a01eef26adae2ddf35f05f253e056490cc62972eda790f9601f23b2a8a23#rd)提到 过 Kotlin 的类型映射的问题，kotlin.String 编译之后毫无疑问的要映射成 java.lang.String，而 kotlin.Int 则有可能映射成 int 或是 java.lang.Integer，这么说来我们的 User 的构造方法签名可能是下面这样：

```java
init(int, java.lang.String, int, java.lang.String)
```

也可能是这样：

```java
init(java.lang.Integer, java.lang.String, java.lang.Integer, java.lang.String)
```

现在通过刚才报的错误来看，映射后的签名毫无疑问的应该是前面那种了，毕竟这里 Int 并没有装箱的需求，为了追求效率，映射成 int 是再合适不过的了。也正是这个原因，MyBatis 才无法找到它想要的构造方法，无法构造出 User 对象，最终导致程序运行失败。

## 问题探究 ② —— JavaBean 的无参构造

JavaBean 是一个很有意思的概念，刚刚接触这个概念的时候都有点儿不敢相信自己的耳朵，一个在 JavaEE 当中举足轻重的概念居然就只是一个有无参构造方法、属性通过 Getter 和 Setter 访问、可序列化和反序列化的 POJO，就这么简单？说实在的，当时真觉得 JavaBean 也没什么了不起的，就像最开始学牛二定律的时候一样，一个只有 4 个字符的定律，料它也不能把洒家怎样——可是实际上呢，它确实把我给怎样了。。

刚刚我们分析错误的时候，很直接的分析了构造方法为什么不匹配的原因，却没有想想为什么要找这个构造方法，试想，如果你用 Java 写这段代码，你肯定会写出类似下面的代码：

```java
public class User{
	private int id;
	private String username;
	private int age;
	private String passwd;
	
	... 省略 getter 和 setter 
}
```

如果不纠结序列化的事儿，这个 User 就是个 JavaBean 是吧，你交给 MyBatis 使用的话也不会出现任何问题—— MyBatis 压根儿不需要找什么构造方法，因为人家根本不需要费那劲，有无参的默认构造方法的话，构造对象实例岂不是轻而易举？

对咯，MyBatis 其实想要的是一个 JavaBean，一个有默认无参构造方法的类，结果呢，你给人家塞了一个 data class 过去。。

## 解决方案 ① —— 我就用 Integer 了怎么着吧

这个问题有一个最为直接的解决办法，那就是直接使用 Integer 而不是 kotlin.Int。

```kotlin
data class User (var id: Integer, var username: String, var age: Integer, var passwd: String)
```

不过，你一旦这么写了，你就没办法在 Kotlin 当中正常实例化这个类了（在 Java 中可以实例化），所以这种方案堪比七伤拳啊：

```kotlin
val user = User(1,"root", 30,"") //error : The integer literal does not conform to the expected type Integer
```

## 解决方案 ② —— kotlin.Int 什么时候映射为 Integer

如果 kotlin.Int 能够映射成 java.lang.Integer，那么这问题就彻底解了。试想一下，什么情况下 int 不好使，非得用 Integer？

* 整型作为泛型参数的时候
* 可以为 null

这两种情况显而易见的需要 Integer 出马了，比如你想将一堆整数放入 ArrayList 当中，你只能这么搞：

```java
ArrayList<Integer> integers = new ArrayList<Integer>();
...
```

还有一种就是整型值可能为 null 的时候，毕竟作为基本类型的 int 连默认值都是 0，怎么会为 null 呢？

回到我们的问题，如果能让 data class 的 Int 映射为 Integer，那么构造方法应该是妥妥的了：

```kotlin
data class User (var id: Int?, var username: String, var age: Int?, var passwd: String)
```

我们把构造方法当中的 id 和 age 的类型做了修改，从不可为空的 Int 改为可为空的 Int？，这样编译之后就只好映射为 Integer了。

问题解决~

这个方案的优点就是几乎没有额外的依赖或者其他什么开销，只是后续编码时，你会总是被迫对 id、age 这几个属性进行是否为空的判断，这样看起来一点儿都不美。

## 解决方案 ③ —— 默认参数

其实就像我们前面提到的，如果 User 这个类有个无参构造的话，后面查找其他构造方法的事儿就压根儿不会有。也就是说如果我们给 User 类加一个无参构造，这个问题也是可以解决的：

```kotlin
class User {
    var id: Int = 0
    lateinit var username: String
    var age: Int = 0
    lateinit var passwd: String
}
```
如果这样写的话，我们就无法享受 data class 带来的书写便利了。。不过如果我们能够骗过 MyBatis 说我们这个类有无参构造，那么问题不就解决了？

```kotlin
data class User (var id: Int = 0, var username: String = "", var age: Int = 0, var passwd: String = "")
```

我们为每一个参数加了默认值， 这样编译出来之后，字节码当中就真的会看到有无参构造方法了：

```kotlin
  public <init>()V
   L0
    ALOAD 0 
    ICONST_0
    ACONST_NULL
    ICONST_0
    ACONST_NULL
    BIPUSH 15
    ACONST_NULL
    INVOKESPECIAL net/println/kotlin/mybatis/User.<init> (ILjava/lang/String;ILjava/lang/String;ILkotlin/jvm/internal/DefaultConstructorMarker;)V
    RETURN
   L1
    MAXSTACK = 7
    MAXLOCALS = 1
```

实际上我们也可以通过反射来获得到这个无参的构造方法，也正是因为如此，我们也可以直接用 newInstance 方法来构造 User 实例：

```kotlin
User::class.java.newInstance()
```

既然有了无参构造方法，MyBatis 就不需要绞尽脑汁还要找其他的构造方法，于是问题解决~

这个方案的优点是，比较简单，也没有上一个方案那样的副作用；缺点就是，万一某一个属性没有默认值，你该给它设置什么呢？

## 解决方案 ④ —— 官方也认为有时候我们需要一个无参构造

早在 1.0.6 发版的时候，官方就增加了对无参构造的一种另类支持，即 noarg 插件。Kotlin 原本不需要这么做，但考虑到它与 Java 解不开理还乱的关系，Java 支持的一切代码的写法 Kotlin 也似乎有责任和义务来完全支持了。

这个方法其实是 Kotlin 编译插件在编译器通过字节码织入的方式向 class 文件中写入了一个无参构造方法，这个构造方法由于出现的时间比较晚，我们无法在代码中引用到它，不过却可以通过反射访问到它，这样就即保证了 Kotlin 的初心不变，如果你愿意用 data class 或者类似的实体类，那么你就要按照 Kotlin 的要求妥善处理好它的成员的初始化，也方便了一些框架的“出格”行为，显然一个聪明的框架需要对代码本身有足够的理解，对编码人员的限制对于框架本身来说就显得没有那么的重要了。

如果你遇到了这样的问题，我当然建议你采用官方的这个解决方案，原因很简单，除了要写一个注解之外，几乎没有任何副作用，另外，官方支持的方案自然也比较有保障啦。

## 拓展延伸 —— 不择手段创建实例

说起来我就要批评一下 MyBatis 了，一点儿都不如 Gson 流氓。我们前面虽然没有细说，不过大家基本上可以知道 MyBatis 是如何创建返回结果的实例的：

```java
  private Object createResultObject(ResultSetWrapper rsw, ResultMap resultMap, List<Class<?>> constructorArgTypes, List<Object> constructorArgs, String columnPrefix)
      throws SQLException {
	...
    } else if (resultType.isInterface() || metaType.hasDefaultConstructor()) {
      //有无参构造方法的话走的是这个分支
      return objectFactory.create(resultType);
    } else if (shouldApplyAutomaticMappings(resultMap, false)) {
      //在这里查找与表结构匹配的构造方法，我们之前遇到的错误就在这个方法当中抛出
      return createByConstructorSignature(rsw, resultType, constructorArgTypes, constructorArgs, columnPrefix);
    }
    ...
  }
```

我们看到如果没有找到匹配的构造方法，也没有无参的构造方法，MyBatis 就叹了一口气，放弃了。这样的事情如果交给 Gson，你就会发现完全不一样。我曾在[12 Json数据引发的血案](https://mp.weixin.qq.com/s?__biz=MzIzMTYzOTYzNA==&mid=2247483683&idx=1&sn=41ca82c89ddccaf61a9c734e5976a62b&chksm=e8a05e1edfd7d7086b84db34640043054b2288cc5e45bdc300cfab2ce16ffb84c66438a16dca#rd)这一期当中介绍过 Gson 如何创建实例，它甚至可以让 Kotlin 的不可空类型“赋值”为 null，原因很简单，它在实例化对象的时候也跟 MyBatis 一样，先去找无参构造，找不到就用 Unsafe.allocateInstance 来创建对象，主要这个创建方法非常的底层，你可以简单的理解为只为实例化出来的 Java 对象开辟了对象存续需要的空间，而对应地它的成员没有一个会正常初始化。

```kotlin
class Test{
    init {
        println("init")
    }

    companion object{
        init {
            println("cinit")
        }
    }
}
```

注意到这段代码，cinit 将在 Test 类加载时打印，init 将在 Test 实例化时打印。

```kotlin
val field = Unsafe::class.java.getDeclaredField("theUnsafe")
field.isAccessible = true
val unsafe = field.get(null) as Unsafe
unsafe.allocateInstance(Test::class.java)
```

我们运行这样的程序，结果只有 cinit，难怪人家叫 Unsafe，都告诉你 Unsafe 了你还想要什么。。

不过这在 C++ 当中，简直不叫事儿，不信给你看一段代码：

```cpp
class Hello {
public:
    int getNum();

    int checkNum(int a, int b = 0);
};
...
int Hello::getNum() {
    return 12310;
}

...
using namespace std;

int main() {
    cout << ((Hello*)0)->getNum() << endl;
    return 0;
}
```

我们把一个 0 强转为 Hello 类型的指针，接着还调用人家的函数 getNum，结果你猜怎么着？运行结果还是对的！

如果你经常接触 Jni，你也经常会把 native 的指针传给 Java，Java 拿到的其实就是一个 long 类型的数，等 Java 需要调用 native 代码的时候，你就会发现这个整数传给 native 层会首先被 reinterpret_cast。

这有什么稀罕的，反正你创建的类也好，对象也好，最终都是数，严格的语法限制也不过是编译器给我们盖起的围墙，你通过围墙来保护你自己，同时也让围墙遮挡了你的眼睛。

![](../../arts/kotlin扫码关注.png)
