[原文链接](https://juejin.im/entry/5c6a0a04f265da2de660f839)

# 基础篇

## 面向对象

### 什么是面向对象

Object Oriented, OO是软件开发方法，是一种对现实世界的理解和抽象的方法。

### 面向对象编程

Object Oriented Programming OOP是一种程序设计的思想，把对象作为程序的最基本单元，一个对象中包含了数据和操作数据的函数。OOP把计算机程序视作一组对象的组合，每个对象都可接收其他对象发送过来的信息，并进行对应的处理。程序的执行就是一系列消息在对象之间的传递。

## 面向过程

### 什么是面向过程

Procedure Oriented, PO是一种以过程为中心的编程思想。为了完成什么样的事情我们会有什么目的。

### 面向过程编程

Procedure Oriented Programming POP是一种把计算机程序视作一系列命令的集合，既一组函数的循序执行。面向过程编程中常用的简化复杂度的方法是把大块函数切割成小块函数子函数进而降低系统的复杂度。

## 面向对象的三大特征

### 继承 Inheritance

1. 子类拥有父类的特征，父类则不拥有子类的特有属性，父类更通用，子类更具体。
2. Java中子类使用extends关键字继承对应父类。
3. Java中子类使用super关键字调用父类构造方法。
4. 父类中的public, protected修饰的属性，方法可以继承，default, private修饰的不能继承。
5. 创建规则是创建子类的时候先通过父类的无参数构造方法创建一个父类对象。//TODO
6. 可以在子类中显示父类的有参构造方法。
7. 父类属性均为private修饰的时候，可以通过getter和setter进行调用。

### 封装 Abstraction

封装性就是把对象的成员属性和成员方法结合成一个独立的相同单位，并尽可能的隐蔽对象的内部细节。

1. 通过修饰符设置私有成员变量。
2. 私有成员变量的访问得通过对象的内部成员方法，既对应的getter和setter。

> 成员变量，成员方法。
> 局部变量，全局变量。
> 静态变量、方法，实例变量、方法。
// TODO

### 多态 Polymorphism

### 重写和重载

重写 Overrive

重载 Overloading：让一个类以统一的方式吃力不同数据类型的手段，Java中就是一个类中可以有多个同名方法，但是他们具有不同的参数类型和个数定义。

```java
public class TestSample {

    public void test(final int a) {
        // Do nothing
    }

    public void test(final int a, final int b) {
        // Do nothing
    }
}
```



程序定义的一个引用变量倒底会指向哪个类的实例对象，该引用变量发出的方法调用到底是哪个类中实现的方法，必须在由程序运行期间才能决定。

实现多态在Java中必须具备并满足三个必要条件

1. 继承：必须有存在继承关系的父类和子类
2. 重写：子类对父类方法进行了重写
3. 向上转型：在定义中将子类定义给了父类对象。

## 面向对象的五大原则

1. 单一职责原则 (Single Responsiblity Principle)：类的功能是单一的。
2. 开放封闭原则 (Open-Close Principle)：开放扩展性，封闭更改性，强调在不修改源码的基础上支持更多的接入。
3. 替换原则 (The Liskov Substitution Priciple)：子类可以替换父类出现并且能够在任何地方都满足这个条件。
4. 依赖原则 (The Dependency Inversion Principle)：上层依赖下层。
5. 接口分离原则 (The Interface Segregation Principle)：模块访问通过接口区分。

# 平台无关性

## Java是如何实现平台无关性

Java Runtime Enviroment JRE, 提供了Java跨平台运行上层开发程序的环境
Java Virutal Machine JVM
Java Developemtn Kit JDK

## JVM 支持的其他语言

Kotlin, Groovy, JRuby, Jython, Scala

都是通过编译器编译成Class文件在JVM上运行。

# 值传递

## 值传递和引用传递

值传递，传递的参数是按照值得拷贝传递。传递完成之后互不相干。不会更改局部变量中的值。

```java
public class TempTest {
    private void test1(int a) {
        a = 5;
        System.out.println("a in test method = " + a);
    }

    public static void main(String[] args) {
        TempTest t = new TempTest();
        int a = 3;
        t.test1(a);
        System.out.println("a in main = " + a);
    }
}
```

引用传递，变量对应的内存空间地址传递，指针传递。

```java
public class TempTest {

    private Date testDate;

    public void setDate(final Date paramDate) {
        if (null == paramDate) {
            this.testDate = null;
        } else {
            this.testDate = (Date) paramDate.clone();
        }
    }
}
```

上面这样写是为了杜绝成员变量testDate的引用传递。

## 为什么说java中只有值传递。

[链接](https://blog.csdn.net/bjweimengshu/article/details/79799485)

所以，值传递和引用传递的区别并不是传递的内容。而是实参到底有没有被复制一份给形参。
所以说，Java中其实还是值传递的，只不过对于对象参数，值的内容是对象的引用。
无论是值传递还是引用传递，其实都是一种求值策略(Evaluation strategy)，而按共享传递其实只是按值传递的一个特例罢了。