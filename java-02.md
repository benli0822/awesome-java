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
