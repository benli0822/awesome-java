# 关键字

## transient

前提必须实现 Serializable 接口，指明对象可序列化。
transient关键字保证了对象属性在序列化过程中会被忽略。换句话说，这个字段的生命周期仅存于调用者的内存中而不会写到磁盘里持久化。

1. 一旦变量被transient修饰，变量将不再是对象持久化的一部分，该变量内容在序列化后无法获得访问。
2. transient关键字只能修饰变量，而不能修饰方法和类。注意，本地变量是不能被transient关键字修饰的。变量如果是用户自定义类变量，则该类需要实现Serializable接口。
3. 被transient关键字修饰的变量不再能被序列化，一个静态变量不管是否被transient修饰，均不能被序列化。

对象的序列化方式