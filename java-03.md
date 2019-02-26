# 关键字

## transient

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

## instanceof

instanceof运算符判定**运行时**对象是否属于特定类的一个实例。

## final

通常意义：这是无法改变的。

1. 一个编译时恒定不变的常量 => val
2. 一个在运行时初始化，而你不希望它被改变。

### final 成员变量

final修饰基本类型的时候，表示变量的基本内容不可改变。final修饰引用变量的时候，表示对象引用不可修改，但是对象内容可变，既该对象引用不能指向别的对象。

带有final的成员变量可作为编译器常量供编译器使用。

### final 参数

java中也许将参数列表中的参数以声明的方式声指明为final。这意味着你无发改变参数所指向的对象。

这是一种代码编程风格，意味着可以读参数，但是不能修改参数，场景是向匿名内部类传递数据。


#### 匿名内部类

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


### final方法

final可以用在声明方法上，方法前面加上final关键字，代表这个方法不可以被子类重写。
一般情况下如果一个方法已经定义的比较全面，可以通过final关键字修饰以确保子类不会复写父类方法。

final方法会比非final方法快一点，因为编译的时候已经静态绑定了。

### 静态绑定和动态绑定

java中将一个方法的调用与方法所在的类关联起来的过程叫做绑定。

#### 静态绑定

(final, private, static)在程序执行前就已经进行了绑定，也就是说编译过程中就已经确定了方法是在哪个类的。

private: 不能被子类继承，也不能被子类调用。
final：可以被子类继承，但是不能被子类重写（覆盖）。
static：可以被子类继承，但是不能被子类重写（覆盖）；但是可以被子类隐藏？

#### 动态绑定

在运行时具体对象的类型进行绑定，根据运行时判断的对象具体类型，并分别调用适当的方法。也就是说编译器并不知道对象的类型，但是在方法调用的时候可以通过方法调用机制去查，并找到正确的方法主体。