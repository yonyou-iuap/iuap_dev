业务日志组件-核心层

------------
 
### 简介
现实场景，我们对于 **业务**的记录（也叫业务日志）的操作，很多时候是这样编码的：

    //创建一家公司
    public Organization createCompany(CompanyDto dto){
        // 执行业务方法
        companyDAO.save(dto);

        // 记录日志
        LogDAO.save(new BusinessLog(userDAO.getSubjectName() + "，创建子公司：" + dto.getCompanyName()));

    }

最后结果就是

    1. 新公司的创建
    2. 业务日志：张三，创建子公司：广州子公司

咋一看这样写没有什么问题，但是其中有一个最大的问题：业务逻辑和日志逻辑的混在一起了。如果业务逻辑和日志逻辑足够复杂的时候，你可以想像得到你的代码
就如同意大利面一样。以后维护的时候，就会变成人间地狱！

业务日志组件可以解决此问题：业务逻辑和日志逻辑分离！


### 业务日志组件的目标

1. 日志的记录对业务方法尽量无侵入

2. 尽最大可能不影响业务方法的性能

3. 系统及日志模板配置简单

4. 日志持久化(也称为导出日志)方式灵活

5. 修改日志模板而不需要重启应用

事实上，要达到真正的无侵入是不可能的，业务日志组件对业务方法的侵入只不过是要在业务方法上加上一个注解。



### 如何使用业务日志组件

**大纲**

    1. 在类路径下加入'yonyou-busilog.properties'文件
    2. 为业务方法加上别名，具体做法：在业务方法上加入'@BusilogConfig'注解，并设置别名
    3. 在类路径下加入日志模板配置文件


**详细操作**
1. 在类路径下加入'yonyou-busilog.properties'文件

        #指定拦截的业务方法，使用Spring的切入点写法
        pointcut=execution(* business.*Application.*(..))
        #日志开关
        kaola.businesslog.enable=true
        #指定日志导出器BusinessLogExporter接口的实现。默认有：com.yonyou.uap.ieop.busilog.writer.BusiLogConsoleWriter
        businessLogExporter=com.yonyou.uap.ieop.busilog.writer.BusiLogConsoleWriter

        #线程池配置。因为业务日志的导出借助线程池实现异步
        #核心线程数
        log.threadPool.corePoolSize=10
        #最大线程数
        log.threadPool.maxPoolSize=50
        #队列最大长度
        log.threadPool.queueCapacity=1000
        #线程池维护线程所允许的空闲时间
        log.threadPool.keepAliveSeconds=300
        #线程池对拒绝任务(无线程可用)的处理策略
        log.threadPool.rejectedExecutionHandler=java.util.concurrent.ThreadPoolExecutor$CallerRunsPolicy

2. 为业务方法加上别名。这个别名必须符合Java方法名的命名规则。给业务方法加别名的目的是为了方便业务方法与日志模板之间的映射。

        @BusilogConfig("业务方法别名")
        业务方法

3. 在类路径下加入日志模板配置文件

    日志模板实际上是groovy文件。在这个groovy文件中，你可以写Java代码，也可以写groovy代码。这样，就可以达到最大的
    灵活。同时，配置起来又不复杂。

    目前我们支持两种配置方式：单文件配置方式和多文件配置方式。

**日志模板配置**

* 单文件配置
    1. 在类路径下加入'BusinessLogConfig.groovy'
    2. 文件模板为：

            class BusinesslogConfig {
                //必须
                def context

                //InvoiceApplicationImpl_addInvoice为业务方法别名
                def InvoiceApplicationImpl_addInvoice() {
                    "日志内容"
                }

                def ProjectApplicationImpl_findSomeProjects() {
                    [category:"项目操作", logs:"查找项目"]
                }

            }

    3. 配置模板说明
        配置模板实际上是一个Groovy类。你可以在类中定义任何方法。如果方法为某个业务方法的别名（使用'@BusilogConfig'注解）
        那么，我们就认为它是一个业务日志方法。它的返回值（return或者放在方法最后一行的变量）将会被Set到'com.yonyou.uap.ieop.busilog.BusinessLog'的实例中。

        日志方法返回值有两种情况：1. 只返回一个String类型的日志文本；2. 返回一个Map，这个Map包括Key为'category'的日志分类及日志文本。

        在类中，还会使用Groovy定义变量的方法：'def context'定义一个变量。这个变量实际上是一个Map。
        Map中存储的是业务方法的'返回值'、'参数'。如果需要，你可以存储任何你需要的数据。你可以从这个context中取
        出你需要的内容，填充到你的日志中。至于如何取context中的内容，请看附录


* 多文件配置
当业务系统非常复杂的时候，一个日志配置文件是不足够的。我们提供多文件的配置方式

    1. 在类路径中加入'businessLogConfig'文件夹。
    1. 在该文件夹中加入日志配置文件，文件名任意，只要符合Groovy类文件的命名规范即可。

注： 多文件配置方式与单文件配置方式不兼容。在此业务日志组件中，单文件配置方式优先。
    'businessLogConfig'文件夹中的所有以'.groovy'结尾的文件都将被作为日志配置文件。


### 实现自己的日志持久化方式

1. 新建一个实现'com.yonyou.uap.ieop.busilog.writer.itf.IBusiLogWriter'接口的类，比如:com.mycom.busineslog.MyBusinesslogExporter
2. 在'yonyou-busilog.properties'中设置'businessLogExporter=com.mycom.busineslog.MyBusinesslogExporter'



附录
----------
#### 在日志模板中取'context'的内容

     key                     value
    _methodReturn           业务方法返回值
    _param                  业务方法的参数, _param0代表第一个参数 _param1代表第二个参数，依此类推
    _executeError           业务方法执行失败的异常信息
    _businessMethod         业务方法
    _user                   业务方法操作人
    _time                   业务方法操作时间
    _ip                     ip地址
