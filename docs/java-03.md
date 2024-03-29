# Java基础篇-03：关键字-01，内存模型，JVM内存模型

## 关键字-01

### transient

前提必须实现 Serializable 接口，指明对象可序列化。
transient关键字保证了对象属性在序列化过程中会被忽略。换句话说，这个字段的生命周期仅存于调用者的内存中而不会写到磁盘里持久化。

1. 一旦变量被transient修饰，变量将不再是对象持久化的一部分，该变量内容在序列化后无法获得访问。
2. transient关键字只能修饰变量，而不能修饰方法和类。注意，本地变量是不能被transient关键字修饰的。变量如果是用户自定义类变量，则该类需要实现Serializable接口。
3. 被transient关键字修饰的变量不再能被序列化，一个静态变量不管是否被transient修饰，均不能被序列化。

对象的序列化方式

Externalizable (手动)

Externalizable是Serializable的接口子类，其中主要包括两个方法。

```java
void writeExternal(ObjectOutput out) throws IOException;
void readExternal(ObjectInput in) throws IOException, ClassNotFoundException;
```

Externalizable是对Serializable的一种定制化，在序列化和反序列化中以上两个方法会被调用，在实际中，我们可以会希望对象的某一部分不需要被序列化，或者说一个对象被还原之后，其内部的某些子对象需要重新创建，从而不必将该子对象序列化。

Serializable (自动)

要点：

1. Serializable序列化时不会调用默认的构造器，而Externalizable序列化时会调用默认构造器的！！！ 
2. Serializable：一个对象想要被序列化，那么它的类就要实现 此接口，这个对象的所有属性（包括private属性、包括其引用的对象）都可以被序列化和反序列化来保存、传递。

在Externalizable中使用transient关键字没有任何效果，transient配合Serializable使用。

原文：[链接](https://blog.csdn.net/mengtuoling111/article/details/50156307) 

### instanceof

instanceof运算符判定**运行时**对象是否属于特定类的一个实例。

### final

通常意义：这是无法改变的。

1. 一个编译时恒定不变的常量 => val
2. 一个在运行时初始化，而你不希望它被改变。

#### final 成员变量

final修饰基本类型的时候，表示变量的基本内容不可改变。final修饰引用变量的时候，表示对象引用不可修改，但是对象内容可变，既该对象引用不能指向别的对象。

带有final的成员变量可作为编译器常量供编译器使用。

#### final 参数

java中也许将参数列表中的参数以声明的方式声指明为final。这意味着你无发改变参数所指向的对象。

这是一种代码编程风格，意味着可以读参数，但是不能修改参数，场景是向匿名内部类传递数据。

##### 匿名内部类

匿名内部类也就是没有名字的内部类。
抽象类或者是接口都可以用来作为匿名内部类的声明。匿名内部类只能用一次。

```java
    Thread t = new Thread() {
            public void run() {
                for (int i = 1; i <= 5; i++) {
                    System.out.print(i + " ");
                }
            }
        };
    t.start();
```

```java
  Runnable r = new Runnable() {
            public void run() {
                for (int i = 1; i <= 5; i++) {
                    System.out.print(i + " ");
                }
            }
        };
    Thread t = new Thread(r);
    t.start();
```
[链接](http://www.cnblogs.com/nerxious/archive/2013/01/25/2876489.html)

#### final方法

final可以用在声明方法上，方法前面加上final关键字，代表这个方法不可以被子类重写。
一般情况下如果一个方法已经定义的比较全面，可以通过final关键字修饰以确保子类不会复写父类方法。

final方法会比非final方法快一点，因为编译的时候已经静态绑定了。

#### 静态绑定和动态绑定

java中将一个方法的调用与方法所在的类关联起来的过程叫做绑定。

##### 静态绑定

(final, private, static)在程序执行前就已经进行了绑定，也就是说编译过程中就已经确定了方法是在哪个类的。

private: 不能被子类继承，也不能被子类调用。
final：可以被子类继承，但是不能被子类重写（覆盖）。
static：可以被子类继承，但是不能被子类重写（覆盖）；但是可以被子类隐藏？

##### 动态绑定

在运行时具体对象的类型进行绑定，根据运行时判断的对象具体类型，并分别调用适当的方法。也就是说编译器并不知道对象的类型，但是在方法调用的时候可以通过方法调用机制去查，并找到正确的方法主体。

### static

[链接](https://www.cnblogs.com/chenssy/p/3386721.html)

static代表的含义是在没有创建对象的情况下可以直接调用的方法或者变量。

修饰范围：

1. 类的成员方法
2. 类的成员变量
3. static代码块

#### static方法，静态方法

静态方法不依赖任何对象进行访问。静态方法中不存在this的概念。所以静态方法不能访问类的非静态成员变量或者非静态成员方法。

static方法不依赖于任何实例，所以static方法必须实现，也就是说他不能是抽象方法abstract。

#### static变量，静态变量

Java中没有全局变量的概念，可以简单地把static关键词修饰理解成”全局“或者”静态“的含义。
同时被static修饰的成员变量和成员方法是独立于该类的，它不依赖于某个特定的实例变量，也就是说它被该类的所有实例共享。所有实例的引用都指向同一个地方，任何一个实例对其的修改都会导致其他实例的变化。
静态变量是随着类加载时被完成初始化的，它在内存中仅有一个，且JVM也只会为它分配一次内存，同时类所有的实例都共享静态变量，可以直接通过类名来访问它。

所以我们一般在这两种情况下使用静态变量：

1. 对象之间共享数据。
2. 访问方便。

#### static方法块，静态代码块

静态代码块会随着类的加载一块执行。

##### 静态代码块，构造块，构造函数

对于一个类而言，按照如下顺序执行：

1. 执行静态代码块
2. 执行构造代码块
3. 执行构造函数

对于静态变量、静态初始化块、变量、初始化块、构造器，它们的初始化顺序依次是（静态变量、静态初始化块）>（变量、初始化块）>构造器。

当涉及到继承时，按照如下顺序执行：

1. 执行父类的静态代码块，并初始化父类静态成员变量
2. 执行子类的静态代码块，并初始化子类静态成员变量
3. 执行父类的构造代码块，执行父类的构造函数，并初始化父类普通成员变量
4. 执行子类的构造代码块， 执行子类的构造函数，并初始化子类普通成员变量

[链接](https://www.cnblogs.com/Qian123/p/5713440.html)

### volatile前置知识

#### 系统内存模型

简化模型为：

- CPU运算单元，CPU寄存器Registers
- CPU高速缓存(CPU Cache)
- 主内存

详细的内存模型可以参考

[深入理解计算机系统（1.3）---金字塔形的存储设备、操作系统的抽象概念](https://www.cnblogs.com/zuoxiaolong/p/computer3.html)

单CPU单线程

![java-03-single-cpu-model-interaction](/img/java-03-single-cpu-model-interaction.png)

多CPU多线程

![java-03-cpu-mem-model](/img/java-03-cpu-mem-model.png)


#### 并发编程中的三个基本概念

##### 原子性

一个操作或者多个操作，要么全部执行切执行过程中不会被任何其他因素打断，要么就全部不执行。

典型的例子可以举银行转账的场景：

A账户转账给B账户1000元，那么实际上转账操作要分为两部分，第一部分是从A账户中取出1000元，第二部分是B账户中存入1000元。

那么对于这样的场景，必须保证整体转账的操作是具有原子性的，也就是说要么第一部分和第二部分一起执行，要么就一起不执行。

###### Java中的原子性

```java
int x = 10;
int y = x;
x++;
x = x + 1
```

以上语句只有第一句是单纯的原子性操作，因为其他的语句中都有读取x变量原先值是多少的操作，只有第一句是单纯的将10赋值给x，并将变量写回工作内存中。

实际上，Java的内存模型中只保证基本读取和赋值操作是具有原子性的。

如果想要实现范围更大的原子操作，语法级别可以通过 __synchronized__ 关键字或者Lock的具体实现来实现。

__synchronized__ 和 Lock实际上是基于悲观锁的实现，保证代码块执行的时候有且只有一个线程可以访问执行，保证了原子性的定义。

##### 可见性

多个线程同事访问一个变量的时候，如果其中一个线程对变量进行了修改，则保证其他线程可以立即看到这个线程对变量进行的修改。

```c
# Thread 1
int i = 10;
i = 20;

# Thread 2
j = i
```

简单来说，如果Thread 1是由CPU 1执行，Thread 2是由CPU 2执行，基于计算内存模型考量，假如变量是共享也没有协同协议的话，CPU Register 1 加载 i = 10 到缓存的时候如果 CPU Register 2 加载 j = i，那么实际上j最后的值很有可能就是10而不是20。

所以可见性问题在这个例子中的情况就是，如果线程1对变量的值进行了变更，线程2是否能够立即看到线程1对变量进行的变更并及时进行响应。

##### Java中的可见性

Java中通过关键字 **volatile** 保证变量在线程中的可见性，被 **volatile** 修饰的变量会在修改之后立即被写入主存当中，当其他线程需要读取的时候，可以立即获得最新的值。
如果没有被 **volatile** 修饰的变量，写入主存的时间是不能确定的，故而无法保证可见性。

Java中同样可以通过 __synchronized__ 关键字或者Lock的具体实现来保证可见性，因为锁机制的实现会在执行完对应代码块后，释放锁并且将变量的更改立即写入主存当中。以保证可见性。

##### 有序性

程序执行的顺序就是代码编写的顺序。

影响并发程序执行有序性的主要问题是编译器是否会对代码进行指令重排序(Instruction Reorder)。

###### 指令重排序

处理器在不影响最终程序执行结果的情况下，对输入的程序代码进行的优化，以尽可能的提高程序执行的效率。
指令重排序中很重要的原则是对最终的程序执行结果不会有任何的影响，也就是说，同一执行代码块中，反过来如果程序执行的顺序会对结果产生过影响，那么处理器就不会对其进行优化。

这种情况会发生在什么场景下呢？

举个例子

```java
int i = 0;
int j = 0;
```

对上面的两句代码，谁先谁后在这一代码块为整体的情况下对执行结果不会有任何的影响。这里要做的最终事情很清晰，变量i，j都已正常初始化并且赋值成0。

再者

```java
int i = 0; //1
i = i + 1; //2
```

这样的代码块显而易见是不可以指令重排序的。句柄2如果在句柄1之前执行，那么就意味着变量未定义声明也未赋值，抛出异常不说，更别说能保证最终结果了。

###### Java中的有序性

####### Java中的happens-before原则

Java内存模型允许编译器和处理器对指令进行重排序，重排序的过程不会影响单线程程序的执行，但是却会影响多线程程序执行的正确性。

官网上指出了并命名为 **内存一致性问题** *Memory Consistency Error*

[原文链接](https://docs.oracle.com/javase/tutorial/essential/concurrency/memconsist.html)

Oracle官网上也在这个问题中着重对 **现行发生原则** *happens-before* 原则进行了举例分析。其中谈到以下代码场景

```java
int counter = 0;
counter++; // Thread A
System.out.println(counter); // Thread B
```

如果说以上代码块是在同一个线程中执行的，那么最终命令行打印出的结果是好物疑问的。
但是如果将以上代码块带入到不同的线程中执行，例如

- 第二行由线程A执行
- 第三行由线程B执行

单纯的不做任何处理的话，最终的答应结果将无法保证是1，因为如果没有特殊处理线程A的执行结果以保证线程B可见，也就是前文提到的**可见性**原则。

Java中通过happens-before原则保证这一系列动作的发生，

- 程序次序规则：同一线程内，按照代码循序，书写在前面的代码操作会先行发生于书写在后面的操作。
- 锁定规则：一个unLock操作先行发生于后面对同一个锁的Lock操作。
- volatile变量规则：对一个变量的写操作先行发生于对这个变量的读操作。
- 传递规则：如果操作A先行发生于操作B，而操作B又先行发生于操作C，则可以得出操作A先行发生于操作C。
- 线程启动规则：Thread对象的start()方法先行发生于此线程的每个一个动作。
- 线程中断规则：对线程interrupt()方法的调用先行发生于被中断线程的代码检测到中断事件的发生。
- 线程终结规则：线程中所有的操作都先行发生于线程的终止检测，我们可以通过Thread.join()方法结束、Thread.isAlive()的返回值手段检测到线程已经终止执行。
- 对象终结规则：一个对象的初始化完成先行发生于他的finalize()方法的开始。

### volatile关键字

#### 作用域

- 类的成员变量
- 类的静态成员变量

#### 语义

- 保证了不同线程之间对变量操作的可见性
- 禁止进行指令重排序

volatile包含了以下操作：

- 对使用volatile关键字修饰的的变量的修改会立即被写入主存；
- 不同线程对volatile关键字修饰的变量进行修改的时候，会导致其他线程中对变量的工作缓存失效。（也就是CPU中L1或者L2缓存中对应的缓存行失效；
- 由于对volatile关键字修饰的变量进行修改的时候会立即让其他工作缓存中的缓存行失效，对应第一步其他CPU线程会立即请求主存并重新加载volatile变量。

##### volatile的原子性

```java
public VolatileAtomicTest {

    public volatile int i = 0;

    public void increase() {
        i++;
    }

    public static void main(String[] args) {
        final VolatileAtomicTest test = new VolatileAtomicTest();
        for(int j = 0; j < 10; j++) {
            new Thread() {
                public void run() {
                    for(int k = 0; k < 1000; k++) {
                        test.increase();
                    }
                }
            }.start();
        }

        // https://blog.csdn.net/xiaolinzi007/article/details/44487851
        // https://blog.csdn.net/u013164931/article/details/80498880
        // 注意如果用Intelij idea 执行这段代码请用
        // while(Thread.activeCount() > 2) {
        // 简单来说，Intellij在debug的时候会启用守护线程
        while(Thread.activeCount() > 1) {
            Thread.yield();
        }
        System.out.println(test.i);
    }
}
```

先自问一个问题，以上程序做了什么？

主程序创建了10个独立的线程，用来操作同一个对象执行对象成员方法increase对对象成员变量进行自增操作。
接下来的问题是，最后的命令行输出是```10*1000=10000```么？

答案是否定的，原因在于这里做的自增操作并不是一个原子操作，前文中提到自增操作实际上是分为两步来做的：读取并自增。所以说这段程序中，单纯的用volatile关键字修饰变量并进行多线程自增操作，是存在线程安全问题的。

![java-03-volatile-cpu-model](/img/java-03-volatile-cpu-model.png)

[图片原文链接](http://www.cnblogs.com/aigongsi/archive/2012/04/01/2429166.html)

从上图中可以看出，举个栗子如果线程处于use阶段中变量中的内容已经被其他线程所修改，那么自然最终程序运行的结果是不正确的。
回到之前讨论的定义，volatile关键字修饰的变量jvm只会保证修改后的值会立即同步到主存当中，同时其他线程在读取这个变量值得时候可以取到最新的值。

上述代码可以有以下三种修改方法。


```java
public VolatileAtomicTest {

    public volatile int i = 0;

    // 加方法锁
    public synchronized void increase() {
        i++;
    }

    public static void main(String[] args) {
        final VolatileAtomicTest test = new VolatileAtomicTest();
        for(int j = 0; j < 10; j++) {
            new Thread() {
                public void run() {
                    for(int k = 0; k < 1000; k++) {
                        test.increase();
                    }
                }
            }.start();
        }

        // https://blog.csdn.net/xiaolinzi007/article/details/44487851
        // https://blog.csdn.net/u013164931/article/details/80498880
        // 注意如果用Intelij idea 执行这段代码请用
        // while(Thread.activeCount() > 2) {
        // 简单来说，Intellij在debug的时候会启用守护线程
        while(Thread.activeCount() > 1) {
            Thread.yield();
        }
        System.out.println(test.i);
    }
}
```

```java
public VolatileAtomicTest {

    public volatile int i = 0;
    // 采用Lock
    private Lock lock = new ReetrantLock();

    public void increase() {
        lock.lock();
        try {
            i++;
            } finally {
                lock.unlock();
            }
    }

    public static void main(String[] args) {
        final VolatileAtomicTest test = new VolatileAtomicTest();
        for(int j = 0; j < 10; j++) {
            new Thread() {
                public void run() {
                    for(int k = 0; k < 1000; k++) {
                        test.increase();
                    }
                }
            }.start();
        }

        // https://blog.csdn.net/xiaolinzi007/article/details/44487851
        // https://blog.csdn.net/u013164931/article/details/80498880
        // 注意如果用Intelij idea 执行这段代码请用
        // while(Thread.activeCount() > 2) {
        // 简单来说，Intellij在debug的时候会启用守护线程
        while(Thread.activeCount() > 1) {
            Thread.yield();
        }
        System.out.println(test.i);
    }
}
```


```java
public VolatileAtomicTest {

    // 支持原子CAS循环的AtomicInteger
    public AtomicInteger i = new AtomicInteger();

    public void increase() {
        i.getAndIncrement();
    }

    public static void main(String[] args) {
        final VolatileAtomicTest test = new VolatileAtomicTest();
        for(int j = 0; j < 10; j++) {
            new Thread() {
                public void run() {
                    for(int k = 0; k < 1000; k++) {
                        test.increase();
                    }
                }
            }.start();
        }

        // https://blog.csdn.net/xiaolinzi007/article/details/44487851
        // https://blog.csdn.net/u013164931/article/details/80498880
        // 注意如果用Intelij idea 执行这段代码请用
        // while(Thread.activeCount() > 2) {
        // 简单来说，Intellij在debug的时候会启用守护线程
        while(Thread.activeCount() > 1) {
            Thread.yield();
        }
        System.out.println(test.i);
    }
}
```