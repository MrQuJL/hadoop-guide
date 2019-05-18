## HDFS的底层原理

### HDFS的底层通信原理采用的是：RPC和动态代理对象Proxy

### （一）RPC

#### 什么是RPC？

Remote Procedure Call，远程过程调用。也就是说，调用过程代码并不是在调用者本地运行，而是要实现调用者与被调用者二地之间的连接与通信。
RPC的基本通信模型是基于Client/Server进程间相互通信模型的一种同步通信形式；它对Client提供了远程服务的过程抽象，其底层消息传递操作对Client是透明的。
在RPC中，Client即是请求服务的调用者(Caller)，而Server则是执行Client的请求而被调用的程序 (Callee)。

#### RPC示例

* 服务器端
    ```java
    package rpc.server;

    import org.apache.hadoop.ipc.VersionedProtocol;

    public interface MyInterface extends VersionedProtocol {

        //定义一个版本号
        public static long versionID=1;

        //定义客户端可以调用的方法
        public String sayHello(String name);
    }
    ```

    ```java
    package rpc.server;

    import java.io.IOException;

    import org.apache.hadoop.ipc.ProtocolSignature;

    public class MyInterfaceImpl implements MyInterface {

        @Override
        public ProtocolSignature getProtocolSignature(String arg0, long arg1, int arg2) throws IOException {
            // 指定签名（版本号）
            return new ProtocolSignature(MyInterface.versionID, null);
        }

        @Override
        public long getProtocolVersion(String arg0, long arg1) throws IOException {
            // 返回的该实现类的版本号
            return MyInterface.versionID;
        }

        @Override
        public String sayHello(String name) {
            System.out.println("********* 调用到了Server端*********");
            return "Hello " + name;
        }

    }
    ```

    ```java
    package rpc.server;

    import java.io.IOException;

    import org.apache.hadoop.HadoopIllegalArgumentException;
    import org.apache.hadoop.conf.Configuration;
    import org.apache.hadoop.ipc.RPC;
    import org.apache.hadoop.ipc.RPC.Server;

    public class RPCServer {

        public static void main(String[] args) throws Exception {
            //定义一个RPC Builder
            RPC.Builder builder = new RPC.Builder(new Configuration());

            //指定RPC Server的参数
            builder.setBindAddress("localhost");
            builder.setPort(7788);

            //将自己的程序部署到Server上
            builder.setProtocol(MyInterface.class);
            builder.setInstance(new MyInterfaceImpl());

            //创建Server
            Server server = builder.build();

            //启动
            server.start();

        }

    }
    ```

* 客户端
    ```java
    package rpc.client;

    import java.io.IOException;
    import java.net.InetSocketAddress;

    import org.apache.hadoop.conf.Configuration;
    import org.apache.hadoop.ipc.RPC;

    import rpc.server.MyInterface;

    public class RPCClient {

        public static void main(String[] args) throws Exception {
            //得到的是服务器端的一个代理对象
            MyInterface proxy = RPC.getProxy(MyInterface.class,  //调用服务器端的接口
                                             MyInterface.versionID,      // 版本号
                                             new InetSocketAddress("localhost", 7788), //指定RPC Server的地址
                                             new Configuration());

            String result = proxy.sayHello("Tom");
            System.out.println("结果是："+ result);
        }

    }
    ```

### （二）Java动态代理对象

* 为其他对象提供一种代理以控制这个对象的访问。

* 核心是使用JDK的Proxy类

    ```java
    package proxy;
    
    public interface MyBusiness {
    
        public void method1();
    
        public void method2();
    }
    ```

    ```java
    package proxy;
    
    public class MyBusinessImpl implements MyBusiness {
    
        @Override
        public void method1() {
            System.out.println("method1");
        }
    
        @Override
        public void method2() {
            System.out.println("method2");
        }
    }
    ```

    ```java
    package proxy;
    
    import java.lang.reflect.InvocationHandler;
    import java.lang.reflect.Method;
    import java.lang.reflect.Proxy;
    
    public class ProxyTestMain {
    
        public static void main(String[] args) {
            //创建真正的对象
            MyBusiness obj = new MyBusinessImpl();
    
            //重写method1的实现 ---> 不修改源码
            //生成真正对象的代理对象
            /*
            Proxy.newProxyInstance(loader, 类加载器
                                   interfaces, 真正对象实现的接口
                                   h ) InvocationHandler 表示客户端如何调用代理对象
            */
    
            MyBusiness proxyObj = (MyBusiness) Proxy.newProxyInstance(ProxyTestMain.class.getClassLoader(), 
                                                         obj.getClass().getInterfaces(), 
                                                         new InvocationHandler() {
    
                                            @Override
                                            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                                                // 客户端的一次调用
                                                /*
                                                 * method: 客户端调用方法名
                                                 * args  : 方法的参数
                                                 */
                                                if(method.getName().equals("method1")){
                                                    //重写
                                                    System.out.println("******重写了method1*********");
                                                    return null;
                                                }else{
                                                    //不感兴趣的方法 直接调用真正的对象完成
                                                    return method.invoke(obj, args);
                                                }
                                            }
                        });
    
            //通过代理对象调用 method1  method2
            proxyObj.method1();
            proxyObj.method2();
        }
    
    }
    ```

