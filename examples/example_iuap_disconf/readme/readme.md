#配置中心组件概述#

## 业务需求 ##

业务应用或者其它产品中，在开发和测试、上线阶段部署应用时都会接触大量的配置文件，如属性配置文件.properties、.xml等，配置文件繁多且不同环境的配置信息不同，如果集群部署需要重复修改，需要以配置中心的机制统一管控和修改配置文件，达到信息一致、实时同步的效果。

同时，部分业务上也需要实时监控某些特殊配置信息的变化，在接收到信息变化通知时，实时修改状态以达到运行态信息实时调整的功能，也需要提供统一的配置信息管理界面和回调机制，使得业务开发此类应用时简便和统一。


## 解决方案 ##

配置中心是以开源产品disconf为基础，利用zk的watcher机制，结合平台产品iuap-auth组件和iuap-design开发出的一款针对配置文件综合管理、近实时同步更新和监控的服务与组件。

利用配置中心，平台其他产品或者业务应用可以将复杂的配置文件和配置项集中式、可视化的管理，降低集群分布式部署时配置文件繁多、重复修改等复杂问题。同时，配置中心还可以根据不同情况，对指定的配置文件或者配置项修改时实时回调业务系统，使得业务系统可以监听配置变化并且加入扩展逻辑。

配置中心支持多应用、多版本、多环境的同时使用，较好的支持了项目上对于开发、测试、线上环境的不同业务模块和微服务集群多配置文件管理的需要。

另外，配置中心提供了统一的可视化配置管理界面，运维管理员或者业务开发人员可以通过可视化的方式管理业务中需要的各种配置文件，并监控多个客户端与配置中心信息的同步状态；配置中心提供简洁的客户端集成的SDK和RESTFUL格式的访问支持，原有业务只需要修改Spring配置文件或者增加注解的方式即可，零代码侵入的进行集成，极大方便了开发扩展。


# 整体设计 #

## 依赖环境 ##

配置中心的web端，依赖zookeeper的3.4.6版本和数据库mysql。服务端和客户端组件依赖iuap平台产品iuap-auth、iuap-cache、iuap-utils等组件。

配置中心的客户端对外提供的依赖方式如下：

	<dependency>
	  <groupId>com.yonyou.iuap</groupId>
	  <artifactId>iuap-disconfclient</artifactId>
	  <version>${iuap.modules.version}</version>
	</dependency>

${iuap.modules.version} 为平台在maven私服上发布的组件的version。

## 其它说明 ##

iuap-disconf依赖zookeeper，利用zk存储配置文件和配置项信息，利用zk的watcher机制在客户端启动的时候扫描配置信息并注册监听，中心的信息变化时实时通知。


# 使用说明 #

## WEB端使用说明 ##

文档以开发态举例，运行态可以借助运维平台搭建环境。


**1：根据开发工具包提供的sql脚本创建数据库并保证提供服务**

**2：安装部署zookeeper环境、Redis环境**

**3：将获取到的服务端war包部署在tomcat中，修改application.properties中对应的连接串信息**

**4：访问部署服务器IP/iuap-disconfweb,以管理员身份登录（admin/admin）**

**5：创建应用、配置文件，选择指定的版本号、运行环境等**

**6：在业务应用中配和iuap-disconfclient进行开发**

## 客户端组件配置 ##

**1:在客户端应用中引用配置中心客户端SDK的Maven依赖**

	<dependency>
		<groupId>com.yonyou.iuap</groupId>
		<artifactId>iuap-disconfclient</artifactId>
		<version>${iuap.modules.version}</version>
	</dependency>	


**2:Spring的主配置文件中，添加如下配置**

    <!-- 使用disconf必须添加以下配置 -->
    <bean id="disconfMgrBean" class="com.yonyou.iuap.disconf.client.DisconfMgrBean" destroy-method="destroy">
        <property name="scanPackage" value="com.yonyou.iuap"/>
    </bean>
    
    <bean id="disconfMgrBeanSecond" class="com.yonyou.iuap.disconf.client.DisconfMgrBeanSecond" 
    	  init-method="init" destroy-method="destroy" lazy-init="false">
    </bean>

    <!-- 使用托管方式的disconf配置(无代码侵入, 配置更改会自动reload)-->
    <bean id="configproperties_disconf" class="com.yonyou.iuap.disconf.client.addons.properties.ReloadablePropertiesFactoryBean">
        <property name="locations">
            <list>
            	<!--目前只支持classpath下托管配置文件，文件格式包括.properties和.xml-->
                <value>classpath:application.properties</value>
                <value>classpath：schema-mapping.xml</value>
            </list>
        </property>
    </bean>
    <bean id="disconfPropertyConfigurer" class="com.yonyou.iuap.disconf.client.addons.properties.ReloadingPropertyPlaceholderConfigurer">
        <property name="ignoreResourceNotFound" value="true"/>
        <property name="ignoreUnresolvablePlaceholders" value="true"/>
        <property name="propertiesArray">
            <list>
                <ref bean="configproperties_disconf"/>
            </list>
        </property>
        <property name="systemPropertiesMode" value="2"></property>
    </bean>

disconfMgrBean和disconfMgrBeanSecond是客户端启动时需要扫描的配置，scanPackage为需要扫描的包名，使用@DisconfFile等注解声明的和配置中心保持同步的配置文件和配置项对应的Bean需要包含在此包内。


其中，configproperties_disconf中的application.properties等配置文件时托管在配置中心的示例配置，业务上可以根据自身需求进行配置，客户端启动前需要在配置中心的web先配置好。

**3:修改classpath下的disconf.properties**

修改关键配置如disconf.conf_server_host、disconf.version等配置信息，各个配置项的说明如下：

- disconf.enable.remote.conf 是否使用远程配置文件，true(默认值)会从远程获取配置 false则直接获取本地配置

- disconf.conf_server_host 配置服务器的 HOST

- disconf.version 版本, 请采用 X_X_X_X 格式 

- disconf.app 对应的APP 请采用产品线_服务名 格式

- disconf.env 环境, 如rd、online等

- disconf.version 版本, 请采用 X\_X\_X\_X 格式,如1_0_0_0

- disconf.user_define_download_dir 用户指定的下载文件夹, 远程文件下载后存放地址

**4:代码中增加对配置文件或者配置项的注解**
	
	//copy2TargetDirPath可以下载到指定目录
	@DisconfFile(filename="test.properties",copy2TargetDirPath="/etc/conf/download")
	public class DisConfBean

**5:编写回调服务**

	@Service
	@DisconfUpdateService(confFileKeys = {"test.properties"})
	public class DisConfigUpdateCallback implements IDisconfUpdate {
		
		private static final Logger LOG = LoggerFactory.getLogger(DisConfigUpdateCallback.class);
	
	    public void reload() throws Exception {
	    	LOG.info("DisConfigUpdateCallback called! application.properties has changed!");
	    	
	    }
	}


**6:更多API操作和配置方式，请参考配置中心对应的示例工程(DevTool/examples/example\_iuap\_disconf)**

按照步骤开发完客户端后，系统启动时会从配置中心下载托管的配置文件和注解中生命的配置文件，在运行态，在配置中心的web端修改信息时，中心会实时通知各个客户端，刷新下载的文件；如果注册了回调服务，会调用IDisconfUpdate实现类的reload方法。

## 工程样例 ##

开发工具包DevTool中携带了对分布式缓存组件的示例工程，位置位于DevTool/examples/example_iuap_cache下，在IUAP_STUDIO中导入已有的Maven工程，可以将示例工程导入到工作区。示例工程中有较为完整的对iuap-disconf组件的使用示例代码。


## 常用注解说明 ##

### @DisconfFile -- 配置文件注解 ###

作用：在客户端应用中生命需要在配置中心统一管理的配置文件

- filename 监管的配置文件名称

- env 配置文件对应的环境编码

- version 配置文件对应的版本号

- copy2TargetDirPath 需要拷贝文件到指定的目录
	

### @DisconfFileItem -- 配置文件属性注解 ###

作用：分布式的配置文件中的ITEM对应的注解


- name 配置文件里的KEY的名字

- associateField 所关联的域

### @DisconfItem -- 配置项注解 ###

作用：独立分布式的配置项注解


- key 配置项名称

- associateField 所关联的域

- env 配置文件对应的环境编码

- version 配置文件对应的版本号


### @DisconfUpdateService -- 回调服务注解 ###

作用：标识配置更新时需要进行更新的服务,需要指定它影响的配置数据，可以是配置文件或者是配置项

- classes 配置文件对应的Bean的Clazz

- confFileKeys 配置文件名称

- itemKeys 配置项名称
