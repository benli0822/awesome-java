# java 基础知识

## 基本数据类型

byte, short, int, long, float, double, boolean, char

byte: -128(-2^7) ~ 127(2^7-1)
short: -32768(-2^15) ~ 32767(2^15-1)
int: -2^31 ~ 2^31-1
long: -2^63 ~ 2^63-1
float: 单精度，32位浮点数
double: 双精度，64位浮点数
boolean: 默认为false
char：16位unicode字符


### 浮点型 

[链接](https://baike.baidu.com/item/%E6%B5%AE%E7%82%B9%E5%9E%8B)

### 为什么不能用浮点型表示金额？

[链接](https://juejin.im/post/5c08db5ff265da611e4d7417)


## 自动拆箱和装箱

装箱就是自动将基本数据类型转换为包装器类型；拆箱就是 自动将包装器类型转换为基本数据类型。

```java
    // Autoboxing
    Integer a = 100;
    Integer a = Integer.valueOf(100);
    // Unboxing
    int b = a;
    int b = a.intValue();
```
### Integer 的缓存机制

```java
    public static Integer valueOf(int i) {
        if (i >= IntegerCache.low && i <= IntegerCache.high)
            return IntegerCache.cache[i + (-IntegerCache.low)];
        return new Integer(i);
    }
```

```java
    public int intValue() {
        return value;
    }
```

Integer -128到127之间返回缓冲池常量，超出范围返回新的Integer对象

```java
Integer a = 123;
Integer b = 123;
a == b // true

Integer c = 129;
Integer d = 129;
c == d // false
```

[链接](https://blog.csdn.net/u013309870/article/details/70229983)

## String

### 字符串的不可变性

对象一旦创建，在整个进程的生命周期中是不可变的，无法对其进行加长、缩短、改变等操作，既然它不会变，所以也就不存在线程同步的问题，哪怕是皇天老儿创建的线程都无法对其进行改变。 

[链接](https://blog.csdn.net/u010133536/article/details/42492633)
[链接](https://blog.csdn.net/u013796619/article/details/30540589)

### substring方法在不同版本的jdk中的不同实现

> java 6 openjdk 6

java 6 中substring和string本身对象同指向同一个字符串数组，substring通过offset和count控制最终输出。

```java

    String(int offset, int count, char value[]) {
        this.value = value;
        this.offset = offset;
        this.count = count;
    }

    public String substring(int beginIndex, int endIndex) {
        if (beginIndex < 0) {
            throw new StringIndexOutOfBoundsException(beginIndex);
        }
        if (endIndex > count) { // count = characters sum
            throw new StringIndexOutOfBoundsException(endIndex);
        }
        if (beginIndex > endIndex) {
            throw new StringIndexOutOfBoundsException(endIndex - beginIndex);
        }
        return ((beginIndex == 0) && (endIndex == count)) ? this :
            new String(offset + beginIndex, endIndex - beginIndex, value);
    }
```

这样实现可能会导致的问题是：

如果被引用的字符串很长，但是substring取得字符很短，会导致原字符串很长时间都会被引用而无法GC，最终导致内存泄漏。

> Workaround

通过添加末尾字符创建新的字符串对象。

> 内存泄露：在计算机科学中，内存泄漏指由于疏忽或错误造成程序未能释放已经不再使用的内存。 内存泄漏并非指内存在物理上的消失，而是应用程序分配某段内存后，由于设计错误，导致在释放该段内存之前就失去了对该段内存的控制，从而造成了内存的浪费。

```java
    x = x.substring(x, y) + "";
```

> java 7 & 8

使用new String创建了一个新字符串，避免对老字符串的引用。从而解决了内存泄露问题。

```java
    public String(char value[], int offset, int count) {
        //check boundary
        this.value = Arrays.copyOfRange(value, offset, offset + count);
    }
    
    public String substring(int beginIndex, int endIndex) {
        if (beginIndex < 0) {
            throw new StringIndexOutOfBoundsException(beginIndex);
        }
        if (endIndex > value.length) {
            throw new StringIndexOutOfBoundsException(endIndex);
        }
        int subLen = endIndex - beginIndex;
        if (subLen < 0) {
            throw new StringIndexOutOfBoundsException(subLen);
        }
        return ((beginIndex == 0) && (endIndex == value.length)) ? this
                : new String(value, beginIndex, subLen);
    }
```

> java 9 & 10

```java
    static void checkBoundsBeginEnd(int begin, int end, int length) {
        if (begin < 0 || begin > end || end > length) {
            throw new StringIndexOutOfBoundsException(
                "begin " + begin + ", end " + end + ", length " + length);
        }
    }
    public String substring(int beginIndex, int endIndex) {
        int length = length();
        checkBoundsBeginEnd(beginIndex, endIndex, length);
        int subLen = endIndex - beginIndex;
        if (beginIndex == 0 && endIndex == length) {
            return this;
        }
        return isLatin1() ? StringLatin1.newString(value, beginIndex, subLen)
                          : StringUTF16.newString(value, beginIndex, subLen);
    }
```

### replace, replaceAll, replaceFirst

[链接](https://my.oschina.net/u/816576/blog/369643)

```java
String s = "my.test.txt";
System.out.println(s.replace(".", "#"));
System.out.println(s.replaceAll(".", "#"));
System.out.println(s.replaceFirst(".", "#"));
```

Result:
```
my#test#txt
###########
#y.test.txt
```

### String 对“+”的重载、字符串拼接的几种方式和区别

[链接](https://blog.csdn.net/Dextrad_ihacker/article/details/53055709)
[链接](https://blog.csdn.net/xingbaozhen1210/article/details/80061523)

```java
public class one {
    public static void main(String[] args) {
        String a = "abc";
        String b = "123" + a + "def" + 777;
        System.out.println(b);
    }
}
```

```java
String str = "";
for(int i = 0; i < num; i++)
{
    str += "a";
}
```

```java
StringBuilder a = new StringBuilder();
for(int i = 0; i < 10; i++)
{
    a.append(i); //只有String能"+"
}
```

字符串拼接：+, StringBuilder.append(), String.format();

可以看到StringBuilder.append的执行时间和内存占用都是最优的 . '+' 运算符比直接调用StringBuilder.append要慢上不少 , 特别是要连接的字符串数量较多时 , 内存占用也特别大 . String.format由于每次都有生成一个Formatter对象 , 从时间和内存占用上都最差 . 
    但在实际的业务场景中 , 我们为了程序的可读性 , 简洁等因素 . 往往直接使用'+'拼接 . 而在需要数据转换的时候 , 如日期转换 , 数字转换等 , 可以直接用String.format来拼接 .

### String.valueOf 和 String.toString 的区别

以上问题衍生出：

- (String)[对象]
- [对象].toString
- String.valueOf([对象])

第一种强制转换可能会在不是String类型的情况下抛出ClassCastException。
第二种对所有的对象都适用，因为是Object对象的基础方法，但是会在对象为null的情况下抛NullPointerException

```java
public static String valueOf(Object obj) {
   return (obj == null) ? "null" : obj.toString();
}
```

### switch 对 String 的支持

这是一个java 7中的新特性。

swtich 支持 byte short int char String

[链接](https://www.hollischuang.com/archives/61)

整形switch是对数值进行直接对比
char型switch是对ascii码进行对比
子符串的switch是通过equals()和hashCode()方法来实现的

***其实swich只支持一种数据类型，那就是整型，其他数据类型都是转换成整型之后在使用switch的。***