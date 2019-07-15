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
   
   * 定义属性：`private var money: Int = 1000`希望 money 只有 get 方法，没有 set 方法
   
     * 办法：将其定义为常量 `private val money: Int = 1000`
   
   * private[this]的用法：该属性只属于该对象私有，就不会生成对应的 set 和 get 方法。
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
   
   * 辅助构造器：可以有多个辅助构造器，通过关键字 this 来实现
   
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
  
   Scala 没有静态的修饰符，但 Object 对象下的成员都是静态的，若有同名的 class，将其作为它的伴生类。在 Object 中一般可以在伴生类中做一些初始化等操作。
   
   Object 对象的应用
   
   - 单例对象：
     
     ```scala
     // 利用object对象实现单例模式
     object CreditCard {
         // 变量保存信用卡号
         private[this] var creditCardNumber: Long = 0
     
         // 产生新的卡号
         def generateNewCCNumber(): Long = {
             creditCardNumber += 1
             creditCardNumber
         }
     
         // 测试程序
         def main(args: Array[String]) {
             // 产生新的卡号
             println(CreditCard.generateNewCCNumber())
             println(CreditCard.generateNewCCNumber())
             println(CreditCard.generateNewCCNumber())
             println(CreditCard.generateNewCCNumber())
         }
     
     }
     ```
   
   * 使用应用程序对象：
   
       ```scala
         // 使用应用程序对象： 可以省略main方法
         object HelloWorld extends App {
             // 通过如下方式取得命令行的参数
             if (args.length > 0) {
                 println(args(0))
             } else {
                 println("no arguments")
             }
         }
       ```

7. Scala中的apply方法
  
   遇到如下形式的表达式时，apply 方法就会被调用：
   
   `Object(arg1, arg2, ... argn)`
   
   通常，这样一个 apply 方法返回的是伴生类的对象；其作用是为了省略 new 关键字
   
   ```scala
   var myarray = Array(1, 2, 3)
   ```
   
   Object 的 apply 方法举例：
   
   ```scala
   // object的apply方法
   class Student5(val stuName: String) {
   
   }
   object Student5 {
       // 定义自己的apply方法
       def apply(stuName: String) = {
           println("********Apply in Object**********")
           new Student5(stuName)        
       }
   
       def main(args: Array[String]) {
           // 创建Student5的一个对象
           var s51 = new Student5("Tom")
           println(s51.stuName)
   
           // 创建Student5的一个对象
           var s52 = Student5("Mary")
           println(s52.stuName)
       }
   }
   ```

8. Scala中的继承
  
   Scala 和 Java 一样，使用 extends 关键字扩展类。
   
   - 案例一：Employee 类继承 Person 类
     
     ```scala
     // 演示Scala的继承
     // 父类
     class Person(val name: String, val age: Int) {
       // 定义方法
       def sayHello(): String = "Hello " + name + " and the age is " + age
     }
     
     // 子类
     class Employee(override val name: String, override val age: Int, val salary: Int) extends Person(name, age) {
     
     }
     
     object Demo1 {
       def main(args: Array[String]): Unit = {
         // 创建一个Person的对象
         val p1 = new Person("Tom", 20)
         println(p1.sayHello())
     
         // 创建一个Employee的对象
         var p2: Person = new Employee("Mike", 25, 1000)
         println(p2.sayHello())
       }
     }
     ```
   
   - 案例二：在子类中重写父类的方法：
     
     ```scala
     // 子类
     class Employee(override val name: String, override val age: Int, val salary: Int) extends Person(name, age) {
       override def sayHello(): String = "子类中的sayHello方法"
     }
     ```
   * 案例三：使用匿名子类
     
     ```scala
     // 使用匿名子类来创建新的Person对象
      var p3: Person = new Person("Jerry", 26) {
      override def sayHello(): String = "匿名子类中的sayHello方法"
      }
      println(p3.sayHello())
     ```
   
   * 案例四：使用抽象类。抽象类中包含抽象方法，抽象类只能用来继承。
     
     ```scala
     // Scala中的抽象类
      // 父类： 抽象类
      abstract class Vehicle {
      // 定义抽象方法
      def checkType(): String
      }
      // 子类
      class Car extends Vehicle {
      override def checkType(): String = "I am a car"
      }
      class Bysical extends Vehicle {
      override def checkType(): String = "I am a bike"
      }
      object Demo2 {
      def main(args: Array[String]): Unit = {
      // 定义两个交通工具
      var v1: Vehicle = new Car
      println(v1.checkType())
      var v2: Vehicle = new Bysical
      println(v2.checkType())
      }
      }
     ```
   
   * 案例五：使用抽象字段。抽象字段就是个没有初始值的字段
     
     ```scala
     // 抽象的父类
      abstract class Person {
      // 第一个抽象的字段，并且只有get方法
      val id: Int
      // 另一个抽象的字段，并且有get和set方法
      var name: String
      }
      // 子类：应该提供抽象字段的初始值，否则该子类也应该是抽象的
      abstract class Employee extends Person {
      // val id: Int = 1
      var name: String = "no name"
      }
      // 还有一个办法：我们可以定义个主构造器，接收一个id参数，注意名字要与父类中的名字一样。
      class Employee2(val id: Int) extends Person {
      var name: String = "no name"
      }
     ```

9. Scala中的trit（特征）
  
   trait 就是抽象类。trait 跟抽象类最大的区别：trait 支持多继承
   
   ```scala
   // 第一个trait
   trait Human {
     val id: Int
     val name: String
   
     // 方法
     def sayHello(): String = "Hello" + name
   }
   
   // 第二个trait
   trait Actions {
     // 抽象的方法
     def getActionNames(): String
   }
   
   // 子类
   class Student(val id: Int, val name: String) extends Human with Actions {
     override def getActionNames(): String = "Action is running"
   }
   
   object Demo2 {
     def main(args: Array[String]): Unit = {
       // 创建一个student的对象
       var s1 = new Student(1, "Tom")
       println(s1.sayHello())
       println(s1.getActionNames())
     }
   }
   ```

10. Scala中的文件访问
  
    * 读取行：
      
      ```scala
      import scala.io
      import scala.io.Source
      
      object Demo2 {
        def main(args: Array[String]): Unit = {
          // 读取行
          val source = Source.fromFile("d:/a.txt")
          // 1.将整个文件作为一个字符串
      //    println(source.mkString)
          // 2. 一行一行的读取
          val lines = source.getLines()
          for (l <- lines) println(l)
        }
      }
      ```
    
    * 读取字符：
      
      ```scala
      val source = Source.fromFile("d:/a.txt")
      for (c <- source) println(c)
      ```
    
            其实这个 source 就指向了文件中的每个字符。
    
    * 从 URL 或其他源读取：注意指定字符集 UTF-8：
      
      ```scala
      // 从 URL 或其他源读取：http://www.baidu.com
       val source = scala.io.Source.fromURL("http://www.baidu.com", "UTF-8")
       println(source.mkString)
      ```
    
    * 读取二进制文件：Scala 中并不支持直接读取二进制，但可以通过调用 Java 的 InputStream 来进行读入。
      
      ```scala
      object Demo2 {
       def main(args: Array[String]): Unit = {
         // 读取二进制文件：Scala 并不支持直接读取二进制文件
         var file = new File("d:/a.txt")
         // 构造一个InputStream
         val in = new FileInputStream(file)
         // 构造一个buffer
         val buffer = new Array[Byte](file.length().toInt)
         // 读取
         in.read(buffer)
         // 关闭
         in.close()
       }
      }
      ```
    
    * 写入文本文件：
      
      ```scala
      object Demo2 {
         def main(args: Array[String]): Unit = {
           // 写入文本文件
           val out = new PrintWriter("d:/m.txt")
           for (i <- 1 to 10) out.println(i)
           out.close()
         }
      }
      ```

### （三）Scala语言的函数式编程

1. Scala 中的函数
  
   在 Scala 中，函数是“头等公民”，就和数字一样。可以在变量中存放函数，即：将变量作为函数的值（值函数）。
   
   ```scala
   def myFun1(name: String): String = "Hello" + name
   println(myFun1("Tome"))
   
   def myFun2(): String = "Hello World"
   
   // 值函数：将函数作为变量的值
   val v1 = myFun1("Tom")
   val v2 = myFun2()
   // 再将v2赋值给myFun1
   println(myFun1(v2))
   ```

2. 匿名函数
  
   ```scala
   // 匿名函数
   (x: Int) => x * 3
   // 例子：(1,2,3) --> (3,6,9)
   Array(1,2,3).map((x: Int) => x * 3).foreach(println)
   // 由于map方法接收一个函数参数，我们就可以把上面的匿名函数作为参数传递给map方法
   ```

3. 带函数参数的函数，即：高阶函数
  
   ```scala
   import scala.math._
   
   // 定义高阶函数：带有函数参数的函数
   def someAction(f: Double => Double) = f (10)
   println(someAction(sqrt))
   ```
   
   函数的参数名是 f，f 的类型是匿名函数类型，匿名函数的参数是 Double 类型，匿名函数的返回值是 Double 类型。

4. 闭包
  
   就是函数的嵌套，即：在一个函数定义中，包含另外一个函数的定义；并且在内函数中可以访问外函数中的变量。
   
   ```scala
   def mulBy(factory: Double) = (x: Double) => x * factor
   
   val triple = mulBy(3)
   val half = mulBy(0.5)
   // 调用
   println(triple(10) + "\t" + half(8))
   ```

5. 柯里化
  
   柯里化函数是把具有多个参数的函数转换为一条函数链，每个节点上是单一参数。
   
   ```scala
   // 例子：以下两个 add 函数定义是等价的
   
   def add(x: Int, y: Int) = x + y
   
   def add(x: Int)(y: Int) = x + y // Scala里柯里化的语法
   ```
   
   一个简单的例子：
   
   ```scala
   // 一个普通的函数
   def mulByOneTIme(x: Int, y: Int) = x * y
   
   // 柯里化函数
   def mulByOneTIme1(x: Int) = (y: Int) => x * y
   
   // 简写的方式
   def mulByOneTime2(x: Int)(y: Int) = x * y
   ```

6. 高阶函数示例
  
   ![高阶函数](https://github.com/MrQuJL/hadoop-guide/blob/master/28-Scala/imgs/highfun.png)
   
   示例1：map
   
   ```scala
   // map
   // 在列表中的每个元素上计算一个函数，并且返回一个包含相同数目元素的列表
   val numbers = List(1,2,3,4,5,6,7,8,9,10)
   numbers.map((i: Int) => i * 2).foreach(println)
   ```
   
   示例2：foreach
   
   ```scala
   val numbers = List(1,2,3,4,5,6,7,8,9,10)
   
   // foreach
   // foreach 和 map相似，只不过它没有返回值 foreach只是为了对参数进行作用
   numbers.foreach(println)
   ```
   
   示例3：filter
   
   ```scala
   val numbers = List(1,2,3,4,5,6,7,8,9,10)
   // filter
   // 移除任何使得传入的函数返回false的元素
   numbers.filter((i: Int) => i % 2 == 0).foreach(println)
   ```
   
   示例4：zip（拉链操作）
   
   ```scala
   // zip
   // zip把两个列表的元素合成一个由元素对组成的列表里
   val nList = List(1,2,3) zip List(4,5,6)
   nList.foreach(println)
   ```
   
   示例5：partition（只能分两组，返回值是一个Tuple2）
   
   ```scala
   val numbers = List(1,2,3,4,5,6,7,8,9,10)
   // partition
   // partition根据函数参数的返回值对列表进行拆分
   val tup = numbers.partition((i: Int) => i % 2 == 0)
   tup._1.foreach(println)
   println("----------")
   tup._2.foreach(println)
   ```
   
   示例6：find
   
   ```scala
   val numbers = List(1,2,3,4,5,6,7,8,9,10)
   // find
   
   // find返回集合里第一个匹配断言函数的元素
   
   println(numbers.find(_ % 3 == 0))
   ```
   
   示例7：flatten
   
   ```scala
   // flatten
   // flatten可以把嵌套的结构展开
   
   List(List(1,2,3),List(4,5,6)).flatten.foreach(println)
   ```
   
   示例8：flatMap
   
   ```scala
   // flatMap
   // flatMap是一个常用的combinator，它结合了map和flatten的功能
   var myList = List(List(1,2,3), List(4,5,6))
   myList.flatMap(x => x.map(_*2)).foreach(println)
   ```
   
   (1). 先将(1,2,3)和(4,5,6)这两个集合合并成一个集合
   
   (2). 再对每个元素乘以2

### （四）Scala中的集合

1. 可变集合和不可变集合
  
   * 可变集合
   
   * 不可变集合
     
     * 集合从不改变，因此可以安全地共享其引用。
     
     * 甚至是在一个多线程的应用程序中也没问题。
       
       ```scala
       // 不可变集合
       val math = scala.collection.immutable.Map("Alice"->80,"Bob"->78)
       
       // 可变的集合
       val english = scala.collection.mutable.Map("Alice"->80)
       ```
       
       集合的操作：
       
       ```scala
       // 1. 获取集合中的值
       println(english("Alice"))
       // 2. 调用集合的contains来判断key是否存在
       if (english.contains("Alice")) {
         println("key存在")
       } else {
         println("key不存在")
       }
       // 3. 获取集合中的值不存在时返回一个默认值
       println(english.getOrElse("Alice1", -1))
       
       // 4. 修改集合中的值
       english("Alice") = 59
       println(english.getOrElse("Alice", -1))
       
       // 5. 向集合中添加元素
       english += "xiaoming" -> 40
       
       // 6. 移除集合中的元素
       english -= "Alice"
       ```

2. 列表
  
   * 不可变列表（List）
     
     ```scala
     // 不可变列表：List
     // 字符串列表
     val nameslist = List("Bob", "Mary", "Mike")
     // 整数列表
     val intList = List(1,2,3,4,5)
     // 空列表
     val nullList:List[Nothing] = List()
     // 二维列表
     val dim: List[List[Int]] = List(List(1,2,3),List(4,5,6))
     ```
     
     不可变列表的相关操作：
     
     ```scala
     println("第一个人的名字：" + nameslist.head)
     // tail: 不是返回的最后一个元素，而是返回除去第一个元素后，剩下的元素列表
     println(nameslist.tail)
     println("列表是否为空：" + nameslist.isEmpty)
     ```
   
   * 可变列表（LinkedList）
   
       ```scala
         // 可变列表：LinkedList和不可变List类似，只不过我们可以修改列表中的值
         val myList = mutable.LinkedList(1,2,3,4,5)
         // 操作：将上面可变列表中的每个值乘以2
         // 列名的elem

         // 定义了一个指针指向列表的开始
         var cur = myList
         // Nil: 代表Scala中的null
         while (cur != Nil) {
            // 对当前值*2
            cur.elem = cur.elem * 2
            // 将指针指向下一个元素
            cur = cur.next
         }

         // 查看结果
         println(myList)
       ```

3. 序列
  
   常用的序列有：Vector 和 Range
   
   * Vector 是 ArrayBuffer 的不可变版本，是一个带下标的序列
     
     ```scala
     // Vector: 为了提高list列表随机存取的效率而引入的新的集合类型
     // 支持快速的查找和更新
     
     val v = Vector(1,2,3,4,5,6)
     
     // 返回的是第一个满足条件的元素
     v.find(_ > 3)
     v.updated(2, 100)
     ```
   
   * Range 表示一个整数序列
   
       ```scala
         // Range: 有序的通过空格分割的 Int 序列
         // 一下几个列子 Range 是一样
         println("第一种写法：" + Range(0, 5))
         println("第二种写法：" + (0 until 5))
         println("第三种写法：" + (0 to 4))

         // 两个range可以相加
         ('0' to '9') ++ ('A' to 'Z')

         // 可以将Range转换为List
         1 to 5 toList
       ```

4. 集（set）和集的操作
  
   * 集 Set 是不重复元素的集合
   
   * 和列表不同，集并不保留元素插入的顺序。默认以 Hash 集实现
     
     示例1：创建集
     
     ```scala
     // 集Set：是不重复元素的集合，默认是HashSet
     
     // 创建一个 Set
     var s1 = Set(2,0,1)
     // 往s1中添加一个重复的元素
     s1 =  s1 + 100
     
     // 往s1中添加一个不重复的元素
     s1 = s1 + 100
     
     // 创建一个LinkedHashSet
     var weeksday = mutable.LinkedHashSet("星期一", "星期二", "星期三", "星期四")
     // 创建一个排序的集
     var s2 = mutable.SortedSet(1,2,3,10,4)
     ```
     
     示例2：集的操作
     
     ```scala
     // 集的操作
     // 1. 添加
     
     weeksday + "星期五"
     
     // 2. 判断元素是否存在
     weeksday.contains("星期二")
     
     // 3.判断一个集是否是另一个集的子集
     Set("星期二", "星期四", "星期日") subsetOf(weeksday)
     ```

5. 模式匹配
  
   Scala 有一个强大的模式匹配机制，可以应用在很多场合：
   
   * switch语句
   
   * 类型检查
   
   Scala 还提供了样本类（case class），对模式匹配进行了优化
   
   模式匹配实例：
   
   * 更好的 switch
     
     ```scala
     // 更好的switch
     var sign = 0
     var ch1 = '-'
     ch1 match {
         case '+' => sign = 1
         case '-' => sign = -1
         case _ => sign = 0
     }
     println(sign)
     ```
   
   * Scala 的守卫
   
       ```scala
         // Scala的守卫：匹配某种类型的所有值
         var ch2 = '6'
         var digit: Int = -1
         ch2 match {
             case '+' => println("这是一个+")
             case '-' => println("这是一个-")
             case _ if Character.isDigit(ch2) => digit = Character.digit(ch2, 10)
             case _ => println("其他类型")
         }
         println("Digit: " + digit)
       ```
   
   * 模式匹配中的变量
   
       ```scala
         // 模式匹配中的变量
         var str3 = "Hello World"
         str3(7) match {
             case '+' => println("这是一个+")
             case '-' => println("这是一个-")
             case ch => println("这个字符是：" + ch)
         }
       ```
   
   * 类型模式
   
       ```scala
         // 类型模式
         var v4: Any = 100
         v4 match {
             case x: Int => println("这是一个整数：" + x)
             case s: String => println("这是一个字符串：" + s)
             case _ => println("其他类型")
         }
       ```
   
   * 匹配数组和列表
   
       ```scala
         // 匹配数组和列表
         var myArray = Array(1,2,3)
         myArray match {
             case Array(0) => println("0")
             case Array(x,y) => println("数组包含两个元素")
             case Array(x,y,z) => println("数组包含三个元素")
             case Array(x, _*) => println("这是一个数组")
         }
         // 最后的这个表示，数组包含任意个元素，即：default的匹配
       ```
   
       ```scala
         var myList = List(1,2,3)
         myList match {
             case List(0) println("0")
             case List(x,y) => println("这个列表包含两个元素")
             case List(x,y,z) => println("这是一个列表，包含三个元素")
             case List(x, _*) => println("这个列表包含多个元素")
         }
       ```

6. 样本类（CaseClass）
  
   简单的来说，Scala 的 case class 就是在普通的类定义前加 case 这个关键字，然后你可以对这些类来模式匹配。
   
   case class 带来的最大好处是它们支持模式识别。
   
   首先，回顾一下前面的模式匹配：
   
   ```scala
   // 普通的模式匹配
   var name: String = "Tom"
   
   name match {
       case "Tom" => println("Hello Tom")
       case "Mary" => println("Hello Mary")
       case _ => println("Others")
   }
   ```
   
   其次，如果我们想判断一个对象是否是某个类的对象，跟 Java 一样可以使用 isInstanceOf
   
   ```scala
   // 判断一个对象是否是某个类的对象？isInstanceOf
   class Fruit
   
   class Apple(name: String) extends Fruit
   class Banana(name: String) extends Fruit
   
   // 创建对应的对象
   var aApple: Fruit = new Apple("苹果")
   var bBanana: Fruit = new Banana("香蕉")
   
   println("aApple是Fruit吗？" + aApple.isInstanceOf[Fruit])
   println("aApple是Fruit吗？" + aApple.isInstanceOf[Apple])
   println("aApple是Banana吗？" + aApple.isInstanceOf[Banana])
   ```
   
   在 Scala 中有一种更简单的方式来判断，就是 case class
   
   ```scala
   // 使用case class（样本类）进行模式匹配
   class Vehicle
   case class Car(name: String) extends Vehicle
   case class Bicycle(name: String) extends Vehicle
   
   // 定义一个 Car 对象
   var aCar: Vechicle = new Car("Tom的汽车")
   aCar match {
       case Car(name) => println("我是一辆汽车：" + name)
       case Bicycle(name) => println("我是一辆自行车")
       case _ => println("其他交通工具")
   }
   ```

### （五）Scala语言的高级特性

1. 什么是泛型类
  
   和 Java 或者 C++ 一样，类和特质可以带类型参数。在 Scala 中，使用方括号来定义类型参数。
   
   ```scala
   class GenericClass[T] {
       // 定义一个变量
       private var content: T = _
   
       // 定义变量的get和set方法
       def set(value: T) = {content = value}
       def get(): T = {content}
   }
   ```
   
   测试程序：
   
   ```scala
   // 测试
   object GenericClass {
       def main(args: Array[String]) {
           // 定义一个Int整数类型的泛型对象
           var intGeneric = new GenericClass[Int]
           intGeneric.set(123)
           println("得到的值是：" + intGeneric.get())
   
           // 定义一个String类型的泛型类对象
           var stringGeneric = new GenericClass[String]
           stringGeneric.set("Hello Scala")
           println("得到的值是：" + stringGeneric.get())
       }
   }
   ```

2. 什么是泛型函数
  
   函数和方法也可以带类型参数。和泛型类一样，我们需要把类型参数放在方法名之后。注意：这里的 ClassTag 是必须的，表示运行时的一些信息，比如类型。
   
   ```scala
   import scala.reflect.ClassTag
   
   // 创建一个函数，可以创建一个Int类型的数组
   def mkIntArray(elems: Int*) = Array[Int](elems:_*)
   
   // 创建一个函数，可以创建一个String类型的数组
   def mkStringArray(elems: String*) = Array[String](elems:_*)
   
   // 问题：能否创建一个函数mkArray，即能创建Int类型的数组，也能创建String类型的数组？
   // 泛型函数
   def mkArray[T:ClassTag](elems:T*) = Array[T](elems:_*)
   mkArray(1,2,3,5,8)
   mkArray("Tom", "Marry")
   ```

3. Upper Bounds 与 Lower Bounds
  
   类型的上界和下界，是用来定义类型变量的范围。它们的含义如下：
   
   * S <: T
     
     这是类型上界的定义。也就是 S 必须是 T 的子类（或本身，自己也可以认为是自己的子类。）
   
   * U >: T
   
     这是类型的下界的定义。也就是 U 必须是类型 T 的父类（或本身）
   
   * 一个简单的例子
     
     ![image](https://github.com/MrQuJL/hadoop-guide/blob/master/28-Scala/imgs/upbound.png)
     
     * 一个复杂一点的例子（上界）：
     
         ```scala
         class Vehicle {
             def drive() = {prinltn("Driving")}
         }

         class Car extends Vehicle {
             override def drive() = {println("Car Driving")}
         }

         class Bicycle extends Vehicle {
             override def drive() = {println("Bicycle Driving")}
         }

         object ScalaUpperBounds {
             // 定义方法
             def takeVehicle[ T <: Vehicle](v: T) = {v.drive()}

             def main(args: Array[String]) {
                 var v: Vehicle = new Vehicle
                 takeVehicle(v)

                 var c: Car = new Car
                 takeVehicle(c)
             }
         }
         ```

4. 视图界定（View bounds）
  
   它比 <: 适用的范围更加广泛，除了所有的子类型，还允许隐式转换过去的类型。用 <% 表示。尽量使用视图界定，来取代泛型的上界，因为适用的范围更加广泛。
   
   示例：
   
   * 上面写过的一个例子。这里由于 T 的上界是 String，当我们传递 100 和 200 的时候，就会出现类型不匹配。
     
     ![image](https://github.com/MrQuJL/hadoop-guide/blob/master/28-Scala/imgs/viewbund.png)    
   
   * 但是 100 和 200 是可以转换成字符串的，所以我们可以使用视图界定让 addTwoString 方法可以接受更为广泛的数据类型，即：字符串极其子类、可以转换成字符串的类型。
   
     注意：使用的是 <%
   
     ![image](https://github.com/MrQuJL/hadoop-guide/blob/master/28-Scala/imgs/viewbb.png)
   
   * 但实际运行的时候，会出现错误：
   
     ![image](https://github.com/MrQuJL/hadoop-guide/blob/master/28-Scala/imgs/viewerr.png)
   
     这是因为：Scala并没有定义如何将 Int 转换成 String 的规则，所以要使用视图界定，我们就必须创建转换规则。
   
   * 创建转换规则：
     
     ![image](https://github.com/MrQuJL/hadoop-guide/blob/master/28-Scala/imgs/changerule.png)
   
   * 运行成功：
     
     ![image](https://github.com/MrQuJL/hadoop-guide/blob/master/28-Scala/imgs/viewsuccess.png)

5. 协变和逆变
  
   * 协变：
     
     Scala 的类或特征的泛型定义中，如果在类型参数前面加入 + 符号，就可以使类或特征变为协变了。
     
     ![image](https://github.com/MrQuJL/hadoop-guide/blob/master/28-Scala/imgs/helpc.png)
   
   * 逆变：
     
     在类或特征的定义中，在类型参数之前加上一个 - 符号，就可以定义逆变泛型类和特征了。
     
     ![image](https://github.com/MrQuJL/hadoop-guide/blob/master/28-Scala/imgs/reverc.png)
   
   * 总结：
     
     Scala 的协变：泛型变量的值可以是本身类型或者其子类的类型
     
     Scala 的逆变：泛型变量的值可以是本身类型或者其父类的类型

6. 隐式转换函数
  
   所谓隐式转换函数指的是以 implicit 关键字申明的带有单个参数的函数。
   
   * 前面介绍视图界定的时候的一个例子：
     
     ![image](https://github.com/MrQuJL/hadoop-guide/blob/master/28-Scala/imgs/imp.png)
   
   * 再举一个例子：我们把 Fruit 对象转换成了 Monkey 对象
     
     ![image](https://github.com/MrQuJL/hadoop-guide/blob/master/28-Scala/imgs/monkey.png)

7. 隐式参数
  
   使用 implicit 申明的函数参数叫做隐式参数。我们可以使用隐式参数实现隐式转换
   
   ![image](https://github.com/MrQuJL/hadoop-guide/blob/master/28-Scala/imgs/yincan.png)

8. 隐式类
  
   所谓隐式类：就是对类增加 implicit 限定的类，其作用主要是对类的功能加强。
   
   ![image](https://github.com/MrQuJL/hadoop-guide/blob/master/28-Scala/imgs/add.png)

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

2. scala 循环创建数字序列的几种方式：

   ```scala
   // 0-10
   for (i <- 0 to 10) {
       println(i)
   }
   
   // 0-9
   for (i <- 0 until 10) {
       println(i)
   }
   ```

3. groupByKey 和 reduceByKey 的区别？

   它们都有shuffle的过程，不同的是 reduceByKey 在 shuffle 前进行了合并，减少了 IO 传输，效率高。
