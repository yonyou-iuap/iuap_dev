import static com.yonyou.uap.ieop.busilog.context.ContextKeyConstant.BUSINESS_SYS_ID;
class BusinessLogConfig {
    def context;    
     def ExampleService_delete() {
        [category:"删除类",log:"执行删除方法:${context._param0}的日志记录"]
    }
    
    def ExampleService_save() {
        [category:"保存类",log:"执行保存方法:${context._param0}的日志记录"]
    }

}
