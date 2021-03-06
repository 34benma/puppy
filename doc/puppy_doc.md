<pre>
______
| ___ \
| |_/ /   _ _ __  _ __  _   _
|  __/ | | | '_ \| '_ \| | | |
| |  | |_| | |_) | |_) | |_| |
\_|   \__,_| .__/| .__/ \__, |
           | |   | |     __/ |
           |_|   |_|    |___/
      
</pre>
           
#欢迎使用Puppy

[toc]

## 0 起步之前

<blockquote>

A great web server based on netty and spring.

</blockquote>

### 0.1 Puppy是什么

Puppy是一个基于Netty 4.0.33 和Spring 3.2.16 的Web服务器。不同于Tomcat这样的Servlet容器，Puppy不是一个Servlet容器，而是一个完整的实
现动静分离，前后端分离的Http服务器。

使用Puppy有如下优点：

* 支持快速开发。直接引入Puppy包，加载配置文件即可编写业务代码
* 支持动静分离，前后端分离。Puppy数据返回格式是JSON或XML，前端通过Ajax请求获取数据去渲染。我将在后文说明这样做的意义。
* 基于Netty4 线程模型,性能优越。Netty4是一个优秀的NIO框架，Puppy底层Http服务都是基于Netty编写，因此充分利用了Netty优秀的线程模型
* 提供Web开发过程中众多工具类，让开发聚焦业务逻辑实现上，
* ……

### 0.2 为什么要做Puppy

做Puppy最原始的动力我在以前的一篇[博文](http://wantedonline.cn/2016/12/09/20161209/)中说明了。需要再次说明的是Puppy这个产品的规划和
原始需求并不是我凭空拍脑袋想出来的。而是在上一家公司工作和现在公司工作两种经历鲜明的对比驱使我想做这样一个东西。因此，很多需求的原始来源基本
来自于上家服务器框架，但是最大的区别在于，我放弃使用Netty3，而是升级到了Netty4，同时，根据我的理解，完善并且修改了一些功能。当然，很多代码依
然参考了上一家的服务器框架。在代码签名中有所说明。

可以这样说，Puppy是Ruiz(上一家自研服务器框架)的升级和改造版本。Ruiz是闭源的，而Puppy是开源的。

当然，我最近学习Spring Boot，发现目前很多新技术完全实现了类似Puppy的功能，但是Puppy的优势在于学习门槛更低，使用门槛更低。适合快速开发高性
能的Web服务。

### 0.3 目前开发计划

目前Puppy已经基本能用了，但是还有很多功能没有完善，比如Session，Http包压缩，分块等。整个开发计划见这个链接:[开发计划](https://github.com/34benma/Puppy/blob/master/doc/puppy_plan)

目前V0.6.1版本已经发布。

(Update 目前已经发布V0.6.3 支持内存Session)

## 1 起步
### 1.1 运行环境要求

JDK 1.7+

推荐Linux环境下运行，CPU核数多多益善

### 1.2 如何安装

本工程为一个maven工程

1. 直接导入maven依赖

2. 如果你的工程不是一个maven工程，则需要将本工程的依赖也一起打包进去，目前还没有解决配置文件问题，因此不推荐使用

将maven依赖一起打包到jar包的命令：mvn assembly:assembly (需要安装maven依赖插件)

导入jar包后，还需要将本工程的配置文件引入classpath，配置文件配置见相应的配置文件说明

最终项目结构如图所示(示例工程[miai](https://github.com/34benma/miai))：

![](http://ojyf3pwsm.bkt.clouddn.com/1.png)

相应的配置文件，单独加入到classpath：

![](http://ojyf3pwsm.bkt.clouddn.com/2.png)

做完这些，我们就可以在项目src根下添加一个启动类，写一个main函数即可，最简单的写法如下：

![](http://ojyf3pwsm.bkt.clouddn.com/3.png)

做完这些，我们的项目应该就搭建好了。启动一下试试，如果看到这样的打印:

![](http://ojyf3pwsm.bkt.clouddn.com/4.png)

那么恭喜你，可以开始着手写业务代码了。

## 2 开始使用

### 2.1 项目结构是什么样？

按照MVC的架构划分项目结构，在引入本工程jar包后，可以按照MVC的要求建包

一般来说，cmd包为C，dao包为M，V和MC完全分离。由前端工程师负责实现。

我们不需要自己去写控制器，只需要按照要求将需要返回的数据通过Cmd接口返回即可。一般数据格式是JSON。

关于工程源码包结构，我个人一般喜欢这样来分类：

![](http://ojyf3pwsm.bkt.clouddn.com/5.png)

cmd包：专门写cmd类，可以认为是接入层，这里面一般都是接收参数，校验参数，然后调用Bo类进入逻辑处理

bo包：专门写逻辑处理类，这里可能会调用dao层进行一些数据的查询或更新

dao包：专门写数据操作类，一般缓存层和持久化层的逻辑代码会写在这个里面。

common包：一些公用逻辑或者公共继承类会放这里

### 2.2 如何编写Cmd接口

Cmd接口层是上层请求的入口和逻辑处理结束返回数据模型的出口。在项目工程里，一般专门建一个cmd包存放Cmd类。
所有Cmd类必须实现BaseCmd这个标记接口。

如果我们想要实现一个公共方法给所有Cmd类用，可以写一个公共的Cmd，比如CommonCmd，这个Cmd实现BaseCmd，然后全部Cmd类继承CommonCmd即可。

```
public class CommonCmd implements BaseCmd {
    //common method...
}

```

一个Cmd类，必须注入到Spring容器。Puppy会从Spring容器中找到该Cmd类，解析该Cmd类，放到一个映射关系表中。因此，一个基本的Cmd类必须是如下样子：

```
@Service
@CmdDesc("描述该Cmd类的作用,文档解析器会自动解析该注解形成文档")
public DemoCmd implements BaseCmd {

}

```

这里出现了一个新注解@CmdDesc, 这是一个文档注解，用于描述该Cmd类的作用。可以省略。但一般建议写上，这样，这个Cmd类做什么用就可以从文档中看
出来。

Cmd类的方法承担一个功能。比如有一个注册功能，我们会这样写这个方法,放在一个跟用户注册登录相关的Cmd类里，比如UserLoginCmd

方法签名如下：

```
public Object registerNewUser(HttpRequest request, HttpResponse response) throws Exception {
    // your logic code...
}

```
注意，方法签名里必须是含有且只能含有request和response，并且必须是Puppy的HttpRequest和HttpResponse。返回值是一个Object，一般是一个JSON
对象。

可以通过加一下Puppy的注解来形成文档。

@Cmd("方法功能说明") 这个注解用于修饰方法，可以说明该方法的作用
@CmdAuthor("作者签名") 修饰方法，标识该方法的作者。
@CmdParams() 修饰方法，这里面放@CmdParam，用于说明需要通过request携带的参数。
@CmdSession() 修饰方法，用于说明这个方法是否要求登录态。
@CmdReturn() 修饰方法，用于说明这个方法的返回数据说明。

最终，一个样例的Cmd类如下：

![](http://ojyf3pwsm.bkt.clouddn.com/6.png)

如果启动服务器，输入 localhost:8080/doc
则或生成这样的文档：

![](http://ojyf3pwsm.bkt.clouddn.com/7.png)

关于Cmd的返回值，我们一般是返回一个JSON对象，这里可以用cn.wantedonline.puppy.httpserver.httptools.JsonUtil这个类来JSON话对象。
该类包装了一些返回格式给上层使用。

### 2.3 如何编写Dao层

## 3 深入使用

### 3.1 String工具类的使用

### 3.2 Date相关工具类的使用

### 3.3 配置相关的使用

### 3.4 并发任务类的使用

### 3.5 并发数据结构的使用

## 4 系统工具使用

### 4.1 系统Cmd类的使用

### 4.2 系统监控工具的使用

### 4.3 系统统计工具的使用

### 4.4 系统安全机制

## 5 部分重要原理说明

### 5.1 请求响应模式

### 5.2 请求响应分发机制

### 5.3 Session机制

###



