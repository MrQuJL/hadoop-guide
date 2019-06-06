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

* 可以使用 Scala 的预定义函数，例如：求两个值的最大值:

![image](https://github.com/MrQuJL/hadoop-guide/blob/master/28-Scala/imgs/math.png)

* 也可以使用 def 关键字自定义函数，语法：
  
    ![image](https://github.com/MrQuJL/hadoop-guide/blob/master/28-Scala/imgs/def.png)
  
    示例：
  
    ![image](https://github.com/MrQuJL/hadoop-guide/blob/master/28-Scala/imgs/def-demo.png)

#### 7. Scala的条件表达式

Scala 的 if/else 语法结构和 Java 或 C++ 一样。

不过，在 Scala 中，if/else 是表达式，有值，这个值就是跟在 if 或 else 之后的表达式的值。

#### 8. Scala的循环

Scala 拥有与 Java 和 C++ 相同的 while 和 do 循环

Scala 中，可以使用 for 和 foreach 进行迭代

* 使用 for 循环案例：
  
  ```scala
    // 定义一个集合
    var list = List("Mary", "Tom", "Mike")
  
    println("********** for 第一种写法 ***********")
    for (s <- list) println(s)
  
    println("********** for 第二种写法 ***********")
    for {
        s <- list
        if (s.length > 3)
    } println(s)
  
    println("********** for 第三种写法 ***********")
    for ( s <- list if s.length <= 3) println(s)
  ```

#### 9. Scala函数的参数

* Scala中，有两种函数参数的求值策略
  
  * Call By Value：对函数实参求值，且仅求一次
  * Call By Name：函数实参每次在函数体内被用到的时候都会求值
    
    ![image](https://github.com/MrQuJL/hadoop-guide/blob/master/28-Scala/imgs/fun-param.png)
    
    我们来分析一下，上面两个调用执行的过程：
    
    ![image](https://github.com/MrQuJL/hadoop-guide/blob/master/28-Scala/imgs/fun-param-process.png)
    
    一份复杂一点的例子：
    
    ![image](https://github.com/MrQuJL/hadoop-guide/blob/master/28-Scala/imgs/com.png)

* Scala中的函数参数：
  
  * 默认参数
  
  * 代名参数
  
  * 可变参数
    
      ![image](https://github.com/MrQuJL/hadoop-guide/blob/master/28-Scala/imgs/param-type.png)

#### 10. Scala的Lazy值（懒值）

当 val 被声明为 lazy 时，它的初始化将被推迟，直到我们首次对它取值。

![image](https://github.com/MrQuJL/hadoop-guide/blob/master/28-Scala/imgs/lazy.png)

![image](https://github.com/MrQuJL/hadoop-guide/blob/master/28-Scala/imgs/lazy-com.png)

#### 11. Scala中异常的处理

Scala 异常的工作机制和 Java 或者 C++ 一样。直接使用 throw 关键字抛出异常。

![image](https://github.com/MrQuJL/hadoop-guide/blob/master/28-Scala/imgs/exception.png)

使用 try...catch...finally 来捕获和处理异常：

![image](https://github.com/MrQuJL/hadoop-guide/blob/master/28-Scala/imgs/trycatch.png)

#### 12. Scala中的数组

* 定长数组：使用关键字 Array
  
  ```scala
  // 定长数组
  val a = new Array[Int](10)
  val b = new Array[String](5)
  val c = Array("Tom", "Mary", "Mike")
  ```

* 变长数组
  
  ```scala
  // 变长数组
  val d = ArrayBuffer[Int]()
  // 往变长数组中加入元素
  d += 1
  d += 2
  d += 3
  // 往变长数组中加入多个元素
  d += (10, 12, 13)
  
  // 去掉最后两个值
  d.trimEnd(2)
  
  // 将ArrayBuffer转换为Array
  d.toArray
  ```
- 遍历数组：
  
  ```scala
  // 遍历数组
  var a = Array("Tom", "Mary", "Mike")
  
  // 使用for循环进行遍历
  for (s <- a) println(s)
  
  // 对数组进行转换，新生成一个数组 yield
  val b = for {
      s <- a
      s1 = s.toUpperCase
  } yield (s1)
  
  // 可以使用foreach进行循环输出
  a.foreach(println)
  ```
* Scala 数组的常用操作：
  
  ```scala
  import scala.collection.mutable.ArrayBuffer
  
  val myArray = Array(1, 10, 2, 3, 5, 4)
  
  // 最大值
  myArray.max
  
  // 最小值
  myArray.min
  
  // 求和
  myArray.sum
  
  // 定义一个变长数组
  var myArray1 = ArrayBuffer(1, 10, 2, 3, 5, 4)
  
  // 排序
  myArray1.sortWith(_ > _)
  
  // 升序
  myArray1.sortWith(_ < _)
  ```

* Scala 的多维数组：
  
  * 和 Java 一样，多维数组是通过数组的数组来实现的。
  
  * 也可以创建不规则的数组，每一行的长度各不相同。
    
    ![image](https://github.com/MrQuJL/hadoop-guide/blob/master/28-Scala/imgs/matrix.png)

#### 13. Map

map 集合，由一个(key, value) 组成，用 -> 操作符来创建，例如：

```scala
val scores = Map("Alice" -> 10, "Bob" -> 3, "Cindy" -> 8)n
```

map 的类型分为：不可变 Map 和可变 Map

```scala
// 不可变Map
val math = scala.collection.immutable.Map("Alice" -> 95, "Tom" -> 59)

// 可变Map
val english = scala.collection.mutable.Map("Alice" -> 80)
val chinese = scala.collection.mutable.Map(("Alice", 80), ("Tom", 30))
```

映射的操作

* 获取映射中的值
  
  ```scala
  // 1. 获取Map中的值
  // 如果不存在，会抛出异常
  chinese("Alice")
  chinese.get("Alice")
  
  if (chinese.contains("Alice")) {
      chinese("Alice")
  } else {
      -1
  }
  
  // 简写
  chinese.getOrElse("Alice", -1)
  ```

* 更新映射中的值（必须是可变Map）
  
  ```scala
  // 2.更新Map中的值
  chinese("Bob") = 100
  ```
  
  // 往Map中添加新的元素
  
  chinese += "Tom" -> 85
  
  // 移除Map中的元素
  
  chinese -= "Bob"

```

* 迭代映射

  ```scala
  // 3.迭代Map：使用for，或者foreach
  for (s <- chinese) println(s)
  chinese.foreach(println)
```

#### 14. Tuple（元组）

元组是不同类型的值的聚集

例如：`val t = (1, 3.14, "Friend") // 类型为Tuple3[Int, Double, java.lang.String]`

这里：Tuple 是类型，3 是表示元组中有三个元素。

元组的访问和遍历：

```scala
// 定义tuple，包含3个元素
val t1 = (1, 2, "Tom")
val t2 = new Tuple4("Marry", 3.14, 100, "Hello")

// 访问tuple中的组员_1
t2._1
t2._2
t2._3
t2._4
// t2._5 ---> error

// 遍历Tuple：
t2.productIterator.foreach(println)
```

注意：要遍历 Tuple 中的元素，需要首先生成对应的迭代器。不能直接使用 for 或者 foreach。

### （二）Scala语言的面向对象

1. 面向对象的基本概念
   
   把数据及数据的操作方法放在一起，作为一个相互依存的整体 -- 对象
   
   面向对象的三大特征：
   
   * 封装
   
   * 继承
   
   * 多态

2. 类的定义
   
   简单类和无参方法：
   
   ```scala
   class Counter {
       private var value = 0;
       def increment() { value += 1 }
       def current() = value;
   }
   ```
   
   案例：注意 class 前面没有 public 关键字修饰。
   
   ```scala
   // Scala中类的定义
   class Student1 {
       // 定义属性
       private var stuName: String = "Tom"
       private var stuAge: Int = 20
   
       // 成员方法
       def getStuName(): String = stuName
       def setStuName(newName: String) = this.stuName = newName
   
       def getStuAge(): Int = stuAge
       def setStuAge(newAge: Int) = this.stuAge = newAge
   }
   ```
   
   如果要开发 main 方法，需要将 main 方法定义在该类的伴生对象中，即：object 对象中。
   
   ```scala
   // 创建 Student1 的伴生对象
   object Student1 {
       def main(args: Array[String]): Unit = {
           // 测试Student1
           var s1 = new Student1
   
           // 第一次输出
           println(s1.getStuName() + "\t" + s1.getStuAge())
   
           // 调用set方法
           s1.setStuName("Mary")
           s1.setAge(25)
   
           // 第二次输出
           println(s1.getStuName() + "\t" + s1.getStuAge())
   
           // 第三次输出
           println(s1.stuName + "\t" + s1.stuAge)
           // 注意：stuName 和 stuAge 是 private 的类型的，为什么还可以直接访问呢？这就需要来讨论属性的 get 和 set 方法了
       }
   }
   ```

3. 属性的getter和setter方法
   
   - 当定义属性是 private 时候，scala 会自动为其生成对应的 get 和 set 方法
     
     `private var stuName: String = "Tom"`
     - get 方法：stuName ==> `s2.stuName()` 由于 stuName 是方法的名字，所以可以加上一个括号，当然也可以不加
     
     - set 方法：stuName ==> `stuName_=是方法的名字`
   
   . 定义属性：`private var money: Int = 1000`希望 money 只有 get 方法，没有 set 方法
   
     . 办法：将其定义为常量 `private val money: Int = 1000`
   
   . private[this]的用法：该属性只属于该对象私有，就不会生成对应的 set 和 get 方法。
   
   ```scala
     class Student2 {
         // 定义属性
         private var stuName: String = "Tom"
         // private[this] var stuAge: Int = 20
         private var stuAge: Int = 20
         private val money: Int = 1000
     }
   
     // 测试
     object Student2 {
         def main(args: Array[String]): Unit = {
             var s2 = new Student2
   
             println(s2.stuName + "\t" + s2.stuAge)
             println(s2.stuName + "\t" + s2.stuAge + "\t" + s2.money)
   
             // 修改money的值 --> error
             s2.money = 2000
         }
     }
   ```

4. 内部类（嵌套类）
   
   我们可以在一个类的内部定义一个类，如下：我们在 Student 类中，再定义了一个 Course 类用于保存学生的选修课。
   
   ```scala
   import scala.collection.mutable.ArrayBuffer
   
   // 嵌套类：内部类
   class Student3 {
       // 定义一个内部类，记录学生选修课的课程信息
       class Course(val courseName: String, val credit: Int) {
           // 定义其他方法
       }
       // 属性
       private var stuName: String = "Tom"
       private var stuAge: Int = 20
       
       // 定义一个ArrayBuffer记录该学生选修的所有课程
       private var courseList = new ArrayBuffer[Course]()
       
       // 定义方法往学生信息中添加新的课程
       def addNewCourse(cname: String, credit: Int) {
           // 创建新的课程
           var c = new Course(cname, credit)
           // 将课程加入list
           courseList += c
       }
   }
   ```
   
   开发一个测试程序进行测试：
   
   ```scala
   // 测试
   object Student3 {
       // 创建学生对象
       var s3 = new Student3
       
       // 给该学生添加新的课程
       s3.addNewCourse("Chinese", 2)
       s3.addNewCourse("English", 3)
       s3.addNewCourse("Math", 4)
       
       // 输出
       println(s3.stuName + "\t" + s3.stuAge)
       println("*************选修的课程*************")
       for (s <- s3.courseList) println(s.courseName + "\t" + s.credit)
   }
   ```

5. 类的构造器
   
   类的构造器分为：主构造器、辅助构造器
   
   * 主构造器：和类的声明结合在一起，只能有一个主构造器
     
     `Student4(val stuName: String, val stuAge: Int)`
     
     1. 定义类的主构造器：两个参数
     
     2. 声明了两个属性：stuName 和 stuAge 和对应的 get 和 set 方法
        
        ```scala
        class Student4(val stuName: String, val stuAge: Int) {}
        
        object Student4 {
            def main(args: Array[String]) {
                // 创建Student4的一个对象，调用了主构造器
                var s4 = new Student4("Tom", 20)
                println(s4.stuName + "\t" + s4.stuAge)
            }
        }
        ```
   
   . 辅助构造器：可以有多个辅助构造器，通过关键字 this 来实现
     
     ```scala
     class Student4(val stuName: String, val stuAge: Int) {
         // 定义辅助构造器
         def this(age: Int) {
             // 调用主构造器
             this("no name", age)
         }
     }
     
     object Student4 {
         def main(args: Array[String]) {
             // 创建一个新的Student4的对象，并调用辅助构造器
             var s42 = new Student4(25)
             println(s42.stuName + "\t" + s42.stuAge)
         }
     }
     ```

6. Scala中的Object对象

7. Scala中的apply方法

8. Scala中的继承

9. Scala中的trit（特征）

10. 包的使用

11. Scala中的文件访问

### （三）Scala语言的函数式编程

### （四）Scala中的集合

### （五）Scala语言的高级特性

### （六）Scala语法错误集锦

1. 不用.调方法引起的错误：
   
   ```scala
    val dataFieldValue = dataFieldStr toInt
    if (dataFieldValue >= startParamFieldValue && dataFieldValue <= endParamFieldValue) {
      return true
    } else {
      return false
    }
   ```
   
    编译报错：
   
   ```scala
    Error:(311, 7) illegal start of simple expression
          if (dataFieldValue >= startParamFieldValue && dataFieldValue <= endParamFieldValue) {
   ```
   
    正确写法：
   
   ```scala
    val dataFieldValue = dataFieldStr.toInt
    if (dataFieldValue >= startParamFieldValue && dataFieldValue <= endParamFieldValue) {
      return true
    } else {
      return false
    }
   ```
