#iuap-file文件组件示例#

该示例包括了iuap-file文件组件所适配的，本地文件系统、fdfs文件系统、oss文件系统 的文件操作示例，此外还提供了oss直传示例、fdfs权限处理示例。

##配置文件##

application.properties 该文件配置文件组件采用哪种文件系统，以及一些相关配置

##本地文件系统##

参见FileMgrTest类的testFSUpload方法


##fdfs文件系统##

参见FileMgrTest类的testFdfsUpload方法

##oss文件系统##

参见FileMgrTest类的testAliUpload方法

##oss直传##

参见webapp\js与webapp\page相关页面的实现方法

##fdfs权限处理##

FdfsAuthController类提供了fdfs文件鉴权的模拟实现