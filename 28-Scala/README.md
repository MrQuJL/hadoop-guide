
## Scala编程语言

![image](https://github.com/MrQuJL/hadoop-guide/blob/master/28-Scala/imgs/scala-logo.jpg)

### （一）Scala语言基础

#### 1. Scala语言简介

Scala是一种多范式的编程语言，其设计的初衷是要集成面向对象编程和函数式编程的各种特性。Scala运行于Java平台（Java虚拟机），并兼容现有的Java程序。它也能运行于CLDC配置的Java ME中。目前还有另一.NET平台的实现，不过该版本更新有些滞后。Scala的编译模型（独立编译，动态类加载）与Java和C#一样，所以Scala代码可以调用Java类库（对于.NET实现则可调用.NET类库）。Scala包括编译器和类库，以及BSD许可证发布。

学习Scala编程语言，为后续学习Spark奠定基础。

#### 2. 下载和安装Scala

* 安装 JDK（Scala底层依赖于JDK）

* 下载 Scala：http://www.scala-lang.org/download/

* 安装 Scala：设置环境变量：SCALA_HOME 和 PATH 变量。

#### 3. Scala的运行环境

* REPL（Read Evaluate Print Loop）：命令行

* IDE：图形开发工具
	* Scala IDE（Based on Eclipse）：http://scala-ide.org/ 
	* IntelliJ IDEA 加插件：http://www.jetbrains.com/idea/download/

#### 4. Scala的常用数据类型

注意：在 Scala 中，任何数据都是对象。例如：

![image](https://github.com/MrQuJL/hadoop-guide/blob/master/28-Scala/imgs/data-type.png)

1. 数值类型：Byte，Short，Int，Long，Float，Double

	* Byte：8 位有符号数字，从 -128 到 127
	* Short：16 位有符号数据，从 -32768 到 32767
	* Int：32 位有符号数据
	* Long：64 位有符号数据

        ```scala
        val a:Byte = 10
        a + 10
        // 得到：res9:Int=20
        // 这里的 res9 是新生成的变量的名字
        ```

	**注意：在 Scala 中，定义变量可以不指定类型，因为 Scala 会进行类型的自动推到。**

2. 字符类型和字符串类型：Char 和 String

	对于字符串，在 Scala 中可以进行插值操作

	![image](https://github.com/MrQuJL/hadoop-guide/blob/master/28-Scala/imgs/insert-value.png)

	**注意：前面有个 s，相当于执行："My Name is" + s1**

3. Unit 类型：相当于 Java 中的 void 类型

	![image](https://github.com/MrQuJL/hadoop-guide/blob/master/28-Scala/imgs/unit.png)

4. Nothing 类型：一般表示在执行过程中，产生了 Exception。例如，我们定义一个函数如下：

	![image](https://github.com/MrQuJL/hadoop-guide/blob/master/28-Scala/imgs/nothing.png)

#### 5. Scala变量的声明和使用

* 使用 val 和 var 声明变量：
    ```scala
    val answer = 8 * 3 + 2
    ```

* val：定义的值实际是一个常量，要声明其值可变需用 var

	**注意：可以不用显式指定变量的类型，Scala会进行自动的类型推到**

#### 6. Scala的函数和方法的使用

* 可以使用 Scala 的预定义函数
	例如：求两个值的最大值
	![image](https://github.com/MrQuJL/hadoop-guide/blob/master/28-Scala/imgs/math.png)

* 也可以使用 def 关键字自定义函数，语法：
	![image](https://github.com/MrQuJL/hadoop-guide/blob/master/28-Scala/imgs/def.png)

	示例：
	![image](https://github.com/MrQuJL/hadoop-guide/blob/master/28-Scala/imgs/def-demo.png)
	
#### 7. Scala的条件表达式

#### 8. Scala的循环

#### 9. Scala函数的参数

#### 10. Scala的Lazy值（懒值）

#### 11. Scala中异常的处理

#### 12. Scala中的数组

#### 13. Map

#### 14. Tuple（元组）



### （二）Scala语言的面向对象




### （三）Scala语言的函数式编程



### （四）Scala中的集合



### （五）Scala语言的高级特性








